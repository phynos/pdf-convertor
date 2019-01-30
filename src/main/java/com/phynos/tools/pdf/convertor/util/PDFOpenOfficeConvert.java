package com.phynos.tools.pdf.convertor.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * pdf转换工具-基于OpenOffice+jodconverter
 * <p>
 * 利用jodconverter(基于OpenOffice服务)将文件(*.doc、*.docx、*.xls、*.ppt)转化为html格式或者pdf格式，
 * 使用前请检查OpenOffice服务是否已经开启, OpenOffice进程名称：soffice.exe | soffice.bin
 * 
 * 	开启服务：soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp" -nofirststartwizard
 * </p>
 * @author lupc
 *
 */
public class PDFOpenOfficeConvert {

	private static PDFOpenOfficeConvert doc2HtmlUtil;

	/**
	 * 获取Doc2HtmlUtil实例
	 */
	public static synchronized PDFOpenOfficeConvert getDoc2HtmlUtilInstance() {
		if (doc2HtmlUtil == null) {
			doc2HtmlUtil = new PDFOpenOfficeConvert();
		}
		return doc2HtmlUtil;
	}
	/**
	 * 转换文件成pdf
	 * 
	 * @param fromFileInputStream:
	 * @throws IOException 
	 */
	public String file2pdf(
			InputStream fromFileInputStream,
			String toFilePath,String type) throws IOException  {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timesuffix = sdf.format(date);
		String docFileName = null;
		String htmFileName = null;
		if(".doc".equals(type)){
			docFileName = "doc_" + timesuffix + ".doc";
			htmFileName = "doc_" + timesuffix + ".pdf";
		} else if(".docx".equals(type)){
			docFileName = "docx_" + timesuffix + ".docx";
			htmFileName = "docx_" + timesuffix + ".pdf";
		} else if(".xls".equals(type)){
			docFileName = "xls_" + timesuffix + ".xls";
			htmFileName = "xls_" + timesuffix + ".pdf";
		} else if(".xlsx".equals(type)){
			return null;
		} else if(".ppt".equals(type)){
			docFileName = "ppt_" + timesuffix + ".ppt";
			htmFileName = "ppt_" + timesuffix + ".pdf";
		} else if(".pptx".equals(type)){
			return null;
		} else{
			return null;
		}

		File htmlOutputFile = new File(toFilePath + File.separatorChar + htmFileName);
		File docInputFile = new File(toFilePath + File.separatorChar + docFileName);
		if (htmlOutputFile.exists())
			htmlOutputFile.delete();
		htmlOutputFile.createNewFile();
		if (docInputFile.exists())
			docInputFile.delete();
		docInputFile.createNewFile();
		/**
		 * 由fromFileInputStream构建输入文件
		 */
		try {
			OutputStream os = new FileOutputStream(docInputFile);
			int bytesRead = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((bytesRead = fromFileInputStream.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}

			os.close();
			fromFileInputStream.close();
		} catch (IOException e) {
			throw e;
		}

		try {
			OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
			connection.connect();
			// convert
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(docInputFile, htmlOutputFile);
			connection.disconnect();
			// 转换完之后删除word文件
			docInputFile.delete();
		} catch (ConnectException e) {
			throw e;
		}
		return htmFileName;
	}

	public String file2pdf(File src, String toFilePath,String type) throws IOException {
		FileInputStream fromFileInputStream = new FileInputStream(src);
		return file2pdf(fromFileInputStream,toFilePath,type);        
	}

	public String file2pdf(String src, String toFilePath,String type) throws IOException {
		File file = new File(src);
		return file2pdf(file,toFilePath,type);        
	}


}

