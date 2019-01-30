package com.phynos.tools.pdf.convertor.controller;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.phynos.tools.pdf.convertor.util.PDFMicroOfficeConvert;
import com.phynos.tools.pdf.convertor.util.PDFOpenOfficeConvert;
import com.phynos.tools.pdf.convertor.util.ServletUtil;

@Controller
public class PdfConvertorController {

	@RequestMapping("/")
	public String getIndexView(){
		return "redirect:/uploadview";
	}

	@RequestMapping("/uploadview")
	public String getUploadView(){
		return "upload";
	}

	List<String> EXT = Arrays.asList("doc","docx","xls","xlsx","ppt","pptx","txt");

	@RequestMapping("/upload")
	public String uploadFile(
			@RequestParam("uploadFile") CommonsMultipartFile file,
			@RequestParam("convertor") int convertor,
			HttpServletRequest request,
			Model model
			){
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		if(!EXT.contains(ext)) {
			model.addAttribute("errorMsg", "文件类型错误：" + ext);
			return "result/error";
		}

		if(convertor == 1)
			return convertByMicrosoftOffice(file,ext,request,model);
		else
			return convertByOpenOffice(file,ext,request,model);
	}

	private String convertByMicrosoftOffice(
			CommonsMultipartFile file,
			String ext,
			HttpServletRequest request,
			Model model
			){
		String wordFile = saveFile(request,"_upload",file);

		PDFMicroOfficeConvert.convertFile(wordFile,null,ext);
		
		String a = FilenameUtils.getBaseName(file.getOriginalFilename());		
		if(a.contains(" ")){
			a = a.replace(' ', '_');
		}

		model.addAttribute("pdfPath", "/_upload/" + a + ".pdf");

		return "result/success";
	}

	private String convertByOpenOffice(
			CommonsMultipartFile file,
			String ext,
			HttpServletRequest request,
			Model model
			){
		String wordFile = saveFile(request,"_upload",file);

		PDFOpenOfficeConvert coc2HtmlUtil = PDFOpenOfficeConvert.getDoc2HtmlUtilInstance();
		try {
			File dir = ServletUtil.getWebappsSubFoldDir(request, "_upload");
			String dest = null;
			dest = coc2HtmlUtil.file2pdf(wordFile, dir.getPath(),"." + ext);
			if(dest == null) {
				model.addAttribute("errorMsg", "暂不支持该格式");
				return "result/error";
			} else {
				model.addAttribute("pdfPath", "/_upload/" + dest);
				return "result/success";	
			}			
		} catch (ConnectException e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "OpenOffice服务未启动，请确保OpenOffice已安装且服务已启动！");
			return "result/error";
		} catch (IOException e) {
			e.printStackTrace();
			model.addAttribute("errorMsg", "文件操作错误，堆栈信息：" + e.getMessage());
			return "result/error";
		}
	}

	private String saveFile(
			HttpServletRequest request,
			String fold,
			CommonsMultipartFile file
			){
		String finalFileName = file.getOriginalFilename();
		//检查文件名是否有空格
		if(finalFileName.contains(" ")){
			finalFileName = finalFileName.replace(' ', '_');
		}
		//
		File dir = ServletUtil.getWebappsSubFoldDir(request, fold);
		File finalFile = new File(dir,finalFileName);
		try {
			file.transferTo(finalFile);
			return finalFile.getPath();
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}	

}
