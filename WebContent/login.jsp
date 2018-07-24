<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>用户登陆</title>
<link href="${ctx}/css/base.css" rel="stylesheet" type="text/css">
<!-- [my97日期时间控件] -->
<script type="text/javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" charset="utf-8"></script>

<!-- [jQuery] -->
<script type="text/javascript" src="${ctx }/js/easyui/jquery.min.js" charset="utf-8"></script>

<!-- [EasyUI] -->
<link id="easyuiTheme" rel="stylesheet" href="${ctx }/js/easyui/themes/default/easyui.css" type="text/css">
<link id="easyuiTheme" rel="stylesheet" href="${ctx }/js/easyui/themes/icon.css" type="text/css">
<link id="easyuiTheme" rel="stylesheet" href="${ctx }/js/easyui/ext/portal.css" type="text/css">
<link rel="stylesheet" type="text/css"  href="${ctx }/css/default.css">	
<script type="text/javascript" src="${ctx }/js/easyui/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript" src="${ctx }/js/easyui/ext/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<!-- [zTree] -->
<link rel="stylesheet" href="${ctx }/js/ztree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx }/js/ztree/jquery.ztree.all.js"></script>

<!-- [扩展JS] -->
<script type="text/javascript" src="${ctx}/js/extJs.js" charset="utf-8"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="edge" />
<link rel="shortcut icon" href="${ctx}/images/login/favicon.png" />
<link rel="stylesheet" type="text/css" href="${ctx }/css/style.css" />
<link type="text/css" rel="stylesheet" href="${ctx }/css/style_grey.css" />

<style>
input[type=text] {
	width: 100%;
	height: 25px;
	font-size: 12pt;
	font-weight: bold;
	margin-left: 45px;
	padding: 3px;
	border-width: 0;
}

input[type=password] {
	width: 100%;
	height: 25px;
	font-size: 12pt;
	font-weight: bold;
	margin-left: 45px;
	padding: 3px;
	border-width: 0;
}
</style>

<script type="text/javascript">
	if(window.self != window.top){
		window.top.location = window.location;
	}
	
	$(function() {
		$('#loginform').form({
		    url:'${ctx}/admin/login',
		    onSubmit : function() {
				var isValid = $(this).form('validate');
				if(!isValid){
					$.messager.alert('警告','用户名和密码不能为空','warn');
				} 
				return isValid;
			},
		    success:function(result){
		    	result = $.parseJSON(result);
		    	if (result.success) {
		    		window.location.href='${ctx}/admin/main';
		    	}else{
		    		$.messager.alert('错误',result.msg,'error',function(){
		    			$('#loginform').form('reset');
		    			$('#username').focus();
		    		});
		    	}
		    }
		});
	});
	
	function submitForm(){
		$('#loginform').submit();
	}
	
	function clearForm(){
		$('#loginform').form('clear');
	}
	
	//回车登录
	function enterlogin(){
		if (event.keyCode == 13){
        	event.returnValue=false;
        	event.cancel = true;
        	$('#loginform').submit();
    	}
	}
</script>

</head>

<body onkeydown="enterlogin();">
	<div
		style="width: 900px; height: 50px; position: absolute; text-align: left; left: 50%; top: 50%; margin-left: -450px;; margin-top: -280px;">
		<span style="float: right; margin-top: 35px; color: #488ED5;">全面致力于便捷、安全、稳定等方面的客户体验</span>
	</div>
	<div class="main-inner" id="mainCnt"
		style="width: 900px; height: 440px; overflow: hidden; position: absolute; left: 50%; top: 50%; margin-left: -450px; margin-top: -220px; background-image: url('${pageContext.request.contextPath }/images/bg_login.jpg')">
		<div id="loginBlock" class="login"
			style="margin-top: 80px; height: 255px;">
			<div class="loginFunc">
				<div id="lbNormal" class="loginFuncMobile">幼儿园管理系统登录</div>
			</div>
			<div class="loginForm">
				<form id="loginform" name="loginform" method="post" class="niceform">
					<div id="idInputLine" class="loginFormIpt showPlaceholder" style="margin-top: 5px;">
						<input id="username" class="easyui-textbox" type="text" name="username" data-options="prompt:'用户名',iconCls:'icon-man',required:true"></input>
					</div>
					<div class="forgetPwdLine"></div>
					<div id="pwdInputLine" class="loginFormIpt showPlaceholder">
						<input id="password" class="easyui-textbox" type="password" name="password" data-options="prompt:'密码',iconCls:'icon-lock',required:true"></input>
					</div>
					
					<div class="loginFormIpt loginFormIptWiotTh" style="margin-top:58px;">
						<a onclick="submitForm()" href="#" id="loginform:j_id19" name="loginform:j_id19">
						<span
							id="loginform:loginBtn" class="btn btn-login"
							style="margin-top:-36px;">登录</span>
						</a>
					</div>
					<div class="loginFormIpt loginFormIptWiotTh" style="margin-top:58px;">
						<a onclick="clearForm()" href="#" id="loginform:j_id19" name="loginform:j_id19">
						<span
							id="loginform:loginBtn" class="btn btn-login"
							style="margin-top:-36px;">重置</span>
						</a>
					</div>
					
				</form>
			</div>
		</div>
	</div>
	<div
		style="width: 900px; height: 50px; position: absolute; text-align: center; left: 50%; top: 50%; margin-left: -450px;; margin-top: 240px;">
		<span style="color: #488ED5;font-size:150%">版权所有：开心小宝马</span>
	</div>
</body>

</html>