<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="edge" />
<link rel="shortcut icon" href="${ctx}/images/login/favicon.png" />

<!-- [my97日期时间控件] -->
<script type="text/javascript"
	src="${ctx}/js/My97DatePicker/WdatePicker.js" charset="utf-8"></script>

<!-- [jQuery] -->
<script type="text/javascript" src="${ctx }/js/easyui/jquery.min.js" charset="utf-8"></script>

<!-- [EasyUI] -->
<link id="easyuiTheme" rel="stylesheet" href="${ctx }/js/easyui/themes/<c:out value="${cookie.easyuiThemeName.value}" default="bootstrap"/>/easyui.css" type="text/css">
<link id="easyuiTheme" rel="stylesheet" href="${ctx }/js/easyui/themes/icon.css" type="text/css">
<link id="easyuiTheme" rel="stylesheet" href="${ctx }/js/easyui/ext/portal.css" type="text/css">
<link rel="stylesheet" type="text/css"  href="${ctx }/css/default.css">	
<script type="text/javascript" src="${ctx }/js/easyui/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript" src="${ctx }/js/easyui/ext/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<!-- [zTree] -->
<link rel="stylesheet" href="${ctx }/js/ztree/zTreeStyle.css" type="text/css">


<!-- [扩展JS] -->
<script type="text/javascript" src="${ctx}/js/extJs.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/js/myvalidate.js" charset="utf-8"></script>
<style>
    .datagrid-header-row td{background-color:blue;color:#fff}
</style>