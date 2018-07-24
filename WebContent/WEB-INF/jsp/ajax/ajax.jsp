<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../top.jsp"></jsp:include>
<title>ajax学习</title>
</head>
<body>
	<form action = "${ctx }/wx/ajax" method = "post">
		用户名:<input name="username" onblur="javascript:checkname();"/><br/><span id = "nameerror"></span>
		密码:<input name="password"/><br/>
		<input type="submit" value="登陆"/>
	</form>
</body>

<script type="text/javascript">
	var nameerr = document.getElementById("nameerror");
	
	$(function(){
		//alert(222);
	});

	function checkname(){
		alert(11);
		$.ajax({
			url:"${ctx}/wx/ajaxback",
			type:"post",
			data:{"name":"我的","age":3},
			dataType:"json",
			success:function(data){
				alert(data.appid + "---" + data.seconds);
			}
		});
	}
	

</script>

</html>