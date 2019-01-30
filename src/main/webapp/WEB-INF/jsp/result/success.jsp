<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" >
	<style type="text/css">
	
		
	</style>
</head>
<body>

	<h1>操作成功</h1>
	
	<a href="${pdfPath}" target="_blank">浏览器打开</a>
	
	<br>
	
	<a href="<%=path%>/assets/lib/pdfJS/web/viewer.html?file=${pdfPath}" target="_blank">pdfJS插件浏览</a>
</body>
</html>