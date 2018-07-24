<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../top.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片上传</title>
</head>
<body>

	<!-- <input name="picfile" class="easyui-filebox" style="width:300px"> -->
	<form action="${ctx}/upload/upload" method="post" enctype="multipart/form-data">

		<input type="file" name="picfile" /> <input type="submit" value="upload" />

	</form>

</body>
</html>