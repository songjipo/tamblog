<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../top.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推广二维码</title>
<script>
	window.onload = function showQRCode() {
		$("#imageId").attr("src","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + document.getElementById("ticket").value);
	}
</script>
</head>
<body>
	<input type="hidden" value="${ticket }" id="ticket" />
	<div align="center">
		<image id="imageId" src="" width="90%" style="max-width: 400px;">
		<br/>
		<small style="color: gray">扫一扫上面的二维码，关注公众号</small>
	</div>
</body>
</html>