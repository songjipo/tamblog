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
<title>微信用户列表</title>

<script type="text/javascript">
	var dataGrid;
	
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'wxname',
		title : '微信名称',
		width : 120,
		align : 'center',
		sortable : true
	},{
		field : 'phone',
		title : '手机号',
		width : 120,
		align : 'center',
		sortable : true
	},{
		field : 'openid',
		title : '微信标识',
		width : 400,
		align : 'center',
		sortable : true
	},{
		field : 'createtime',
		title : '关注时间',
		width : 200,
		align : 'center',
		sortable : true
	}] ];
	
	$(function(){
		$("body").css({visibility:"visible"});
		
		// 用户信息表格
		dataGrid = $('#grid').datagrid({
			fit : true,
			border : false,
			rownumbers : true,
			fitColumns : true,
			striped : true,
			pageSize : 30,
			pageList: [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			pagination : true,
			toolbar:'#toolbar',
			url : "${ctx}/user/listWXUser",
			idField : 'id',
			sortName : 'createtime',
			sortOrder : 'desc',
			columns : columns
		});
		
	});
	
	function doSearch() {
		var flag = true;
		/* var starttime = $('#createdatetimeStart').val();
		starttime = starttime.replace(/\-/g, "/");
		var endtime = $('#createdatetimeEnd').val();
		endtime = endtime.replace(/\-/g, "/");
		if(starttime!=""&&endtime!=""&& starttime>endtime){
			parent.$.messager.alert('警告', '开始时间不能大于结束时间', 'warning');
			flag = false;
			return;
		}
		var currenttime = new Date();
		var start = new Date(Date.parse(starttime));
		var end = new Date(Date.parse(endtime));
		if(start >= currenttime){
			parent.$.messager.alert('警告', '开始时间不能大于当前时间', 'warning');
			flag = false;
			return;
		} */
		if(flag){
			dataGrid.datagrid('load', {
				sname:$('#wxname').val(),
				sphone:$('#phone').val(),
				starttime:$('#createdatetimeStart').val(),
				endtime:$('#createdatetimeEnd').val()
			});
		}
		
	}
	
	function enterlogin(){
		if (event.keyCode == 13){
        	event.returnValue=false;
        	event.cancel = true;
        	$('#search').trigger("click");
    	}
	}

</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;" onkeydown="enterlogin();">
	<div region="center" border="false" title="微信用户列表">
    	<table id="grid"></table>
	</div>
	<shiro:hasPermission name="user-list">
	<form id="searchForm">
		<div id="toolbar" style="padding:2px 5px;">
        	<table>
				<tr>
					<th>微信名称:</th>
					<td><input id="wxname" name="wxname" placeholder="请输入微信名" style = "width:150px;"/></td>
					<th>手机号:</th>
					<td><input id="phone" name="phone" placeholder="请输入手机号" style = "width:150px;"/></td>
					<!-- <th>创建时间:</th> -->
					<!-- <td><input id="createdatetimeStart" name="createdatetimeStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至<input id="createdatetimeEnd" name="createdatetimeEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /> -->
					<td><a id="search" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="doSearch()">查询</a></td>
				</tr>
			</table>
    	</div>
    </form>
    </shiro:hasPermission>
</body>
</html>	