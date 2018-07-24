<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../top.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生考勤信息</title>

<script type="text/javascript">
	var dataGrid;
	
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'schname',
		title : '幼儿园',
		width : 120,
		align : 'center'
	},{
		field : 'clname',
		title : '班级',
		width : 120,
		align : 'center'
	},{
		field : 'cardno',
		title : '卡号',
		width : 120,
		align : 'center',
		sortable: true
	},{
		field : 'stuname',
		title : '学生姓名',
		width : 120,
		align : 'center'
	},{
		field : 'pname',
		title : '家长姓名',
		width : 120,
		align : 'center'
	},{
		field : 'rname',
		title : '关系',
		width : 120,
		align : 'center'
	},{
		field : 'time',
		title : '打卡时间',
		width : 120,
		align : 'center',
		sortable: true
	},{
		field : 'action',
		title : '操作',
		width : 100,
		align : 'center',
		formatter : function(value, row, index) {
			var str = '';
			if(row.isdefault!=0){
				str += $.formatString('<a href="javascript:void(0)" onclick="openPhoto(\'{0}\');" >查看图片</a>', row.photo);
			}
			return str;
		}
	}] ];
	
	$(function(){
		$("body").css({visibility:"visible"});
		
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
			url : "${ctx}/sattend/listAttend",
			idField : 'id',
			sortName : 'time',
			sortOrder : 'desc',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
	});
	
	function openPhoto(photo) {
		parent.$.modalDialog({
			title : '查看图片',
			width : 500,
			height : 500,
			href : '${ctx}/sattend/editPage?photo=' + photo,
			buttons : [ {
				text : '关闭',
				handler : function() {
					parent.$.modalDialog.handler.dialog('close')
				}
			} ]
		});
	}
	
	function doDblClickRow(rowIndex, rowData){
		var photo = rowData.photo;
		parent.$.modalDialog({
			title : '查看图片',
			width : 500,
			height : 500,
			href : '${ctx}/sattend/editPage?photo=' + photo,
			buttons : [ {
				text : '关闭',
				handler : function() {
					parent.$.modalDialog.handler.dialog('close')
				}
			} ]
		});
	}
	
	function doSearch() {
			dataGrid.datagrid('load', {
				scardno:$('#cardno').val(),
			});
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
	<div region="center" border="false" title="学生考勤列表">
    	<table id="grid"></table>
	</div>
	<!-- <form id="searchForm">
		<div id="toolbar" style="padding:2px 5px;">
        	<table>
				<tr>
					<td><a href="javascript:void(0);" onclick="doAdd()" class="easyui-linkbutton" iconCls="icon-add">增加</a></td>
					<td><a href="" onclick="doEdit();return false" class="easyui-linkbutton" iconCls="icon-edit">编辑</a></td>
					<td><a href="" onclick="doDelete();return false" class="easyui-linkbutton" iconCls="icon-cancel">禁用</a></td>
					<td><a href="" onclick="doRestore();return false" class="easyui-linkbutton" iconCls="icon-save">恢复</a></td>
					<th>卡号:</th>
					<td><input id="cardno" name="cardno" placeholder="请输入卡号" style = "width:150px;"/></td>
					<td><a id="search" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="doSearch()">查询</a></td>
				</tr>
			</table>
    	</div>
    </form> -->
</body>
</html>	