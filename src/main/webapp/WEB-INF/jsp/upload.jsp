<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" >
	<style type="text/css">
		
		table {
			width: 100%;
			border: 1px solid #ddd;
			border-collapse: collapse;			
		}
		
		table td {
			border: 1px solid #ddd;
		}
		
		table th {
			border-left: 1px solid #ddd;
		}
		
	</style>
</head>
<body>
	<div>
		<h1 style="display: inline;">PDF转换测试</h1>
	</div>

	<br/>
	 
		<table>
			<thead>
				<tr>
					<th></th>
					<th>基于Microsoft Office + Powershell</th>
					<th>基于OpenOffice+jodconverter</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>优点</td>
					<td>
						<ul>
							<li>性能最好</li>
							<li>完美转换（基于官方原生软件）</li>
						</ul>
					</td>
					<td>
						<ul>
							<li>跨平台</li>
							<li>灵活度高</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>缺点</td>
					<td>
							<ul>
								<li>只支持windows系统</li>
								<li>必须安装Microsoft-Office2007+</li>
								<li>必须支持PowerShell脚本（Win7以上操作系统）</li>
								<li>灵活性相对较差（依赖于COM接口）</li>
							</ul>
					</td>
					<td>
						<ul>
							<li>转换慢</li>
							<li>复杂文档转换不完美</li>
							<li>jodconverter2.2.1版本不支持xlsx,pptx格式</li>			
						</ul>					
					</td>
				</tr>
				<tr>
					<td>支持格式</td>
					<td>支持doc,docx,xls,xlsx,ppt,pptx,txt</td>
					<td>支持doc,docx,xls,ppt</td>
				</tr>
			</tbody>
		</table>
		
		<form action="<%=path%>/upload" enctype="multipart/form-data" method="post">
			<h2>上传文件</h2>
			选择转换器：
			<select name="convertor">
				<option value="1">Microsoft Office</option>
				<option value="2">OpenOffice</option>
			</select>
			<input type="file" name="uploadFile" />
			<input type="submit" value="上传" />
		</form>	
		
	<hr>
	
	<h1>PDF浏览测试</h1>
	<ul>
		<li>
			<a href="<%=path%>/assets/pdf/中文名称ABC.pdf" target="_blank">浏览器直接浏览</a>
		</li>
		<li>
			<a href="<%=path%>/assets/lib/pdfJS/web/viewer.html?file=<%=path%>/assets/pdf/中文名称ABC.pdf" target="_blank">pdfJS插件浏览</a>
		</li>
	</ul>
		
<script type="text/javascript">
  

</script>	
</body>
</html>