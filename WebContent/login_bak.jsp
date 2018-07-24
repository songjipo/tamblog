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

<style type="text/css">
	div{
		height:200px;
		line-height:200px;
	}
</style>

<script type="text/javascript">
	if(window.self != window.top){
		window.top.location = window.location;
	}
	
	$(function() {
		$('#loginform').form({
		    url:'${ctx}/login',
		    onSubmit : function() {
				var isValid = $(this).form('validate');
				if(!isValid){
					$.messager.alert('警告','用户名和密码不能为空','warn');
				} 
				return isValid;
			},
		    success:function(result){
		    	result = $.parseJSON(result);
		    	if (result.success == 1) {
		    		window.location.href='${ctx}/main';
		    	}else{
		    		$.messager.alert('错误','用户名和密码错误','error',function(){
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
<style>
		#ie6-warning{width:100%;position:absolute;top:0;left:0;background:#fae692;padding:5px 0;font-size:12px}
		#ie6-warning p{width:960px;margin:0 auto;}
</style>

</head>
<body onkeydown="enterlogin();">
	<div>
		<form  method="post" id="loginform">
	    <div>
	    	<div>
	        	用户名：<input id="username" class="easyui-textbox" type="text" name="username" data-options="required:true"></input><br/>
	            密码：<input id="password" class="easyui-textbox" type="password" name="password" data-options="required:true"></input>
	        </div>
	        <button type="button" onclick="submitForm()">登录</button><button type="button" onclick="clearForm()">重置</button>
	    </div>
	    
	    </form>
	</div>
</body>
</html>