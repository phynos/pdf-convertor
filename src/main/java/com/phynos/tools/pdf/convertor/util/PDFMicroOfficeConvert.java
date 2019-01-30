package com.phynos.tools.pdf.convertor.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * pdf转换工具-基于微软Office软件和Powershell脚本工具
 * @author lupc
 *
 */
public class PDFMicroOfficeConvert {

	public static final String SCRIPT_WORD_TXT = "doc2pdf.ps1";
	public static final String SCRIPT_EXCEL = "excel2pdf.ps1";
	public static final String SCRIPT_POWERPOINT = "ppt2pdf.ps1";
	
	public static String convertFile(
			String filePath,
			String destPath,
			String ext){
		if(ext.contains("doc") || ext.contains("txt"))
			return convert(filePath,destPath,PDFMicroOfficeConvert.SCRIPT_WORD_TXT);
		else if(ext.contains("xls"))
			return convert(filePath,destPath,PDFMicroOfficeConvert.SCRIPT_EXCEL);
		else if(ext.contains("ppt"))
			return convert(filePath,destPath,PDFMicroOfficeConvert.SCRIPT_POWERPOINT);
		else {
			return null;
		}
	}
	
	/**
	 * 将文件转换为PDF文件
	 * <p>注意：
	 * 		<ul>
	 * 			<li>脚本文件请放在资源目录的cmd文件夹下</li>
	 * 			<li>只支持win7以上系统</li>
	 * 			<li>运行环境必须安装Office2007，且必须安装有原生导出插件</li>
	 * 			<li>必须启用Powershell脚本（从开始-附件-powershell命令行执行：Set-ExecutionPolicy Unrestricted）</li>
	 * 		</ul>
	 * </p>
	 * @param filePath 原文件 完整路径（绝对路径）
	 * @param destPath 暂不支持，目标文件路径
	 * @param convertScript 转换脚本，支持：SCRIPT_WORD_TXT,SCRIPT_EXCEL,SCRIPT_POWERPOINT
	 */
	private static String convert(
			String filePath,
			String destPath,
			String convertScript){
		String root = null;
		try {
			root = PDFMicroOfficeConvert.class.getClassLoader().getResource("/").toURI().getPath();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		File file = new File(root);
		root = file.getPath();
		System.out.println(root);
		System.out.println("getAbsolutePath" + file.getAbsolutePath());
				
		String cmd = String.format(
				"%s \"%s%s%s%s%s\" \"%s\"", 
				"powershell -File",
				root,
				File.separatorChar,
				"cmd",
				File.separatorChar,
				convertScript,
				filePath);               
		System.out.println(cmd);
		
		Process ps;
		try {
			ps = Runtime.getRuntime().exec(cmd);
			ps.getInputStream().close();
			ps.getOutputStream().close();
			int result = ps.waitFor();
			System.out.println("result:" + result);			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
}
