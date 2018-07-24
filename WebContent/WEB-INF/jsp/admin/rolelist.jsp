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
<title>角色列表管理</title>

<script type="text/javascript">
	var dataGrid;
	function doAdd(){
		parent.$.modalDialog({
			title : '添加',
			width : 400,
			height : 700,
			href : '${ctx}/role/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#schoolAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	function doEdit() {
        var ss = $('#grid').datagrid('getChecked');    
        if (ss.length == 0) {
            $.messager.alert('提示信息','请选择要编辑的数据','warning');  
            return;
        }
        if (ss.length > 1) {
            $.messager.alert('提示信息','一次只能编辑一条数据','warning');  
            return;
        }
        var id;
		var row = $('#grid').datagrid('getSelected');  
       	id = row.id;
       	
       	parent.$.modalDialog({
			title : '编辑',
			width : 400,
			height : 700,
			href : '${ctx}/role/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#schoolEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function doDelete(){
		var ss = $('#grid').datagrid('getChecked');    
        if (ss.length == 0) {
            $.messager.alert('提示信息','请选择要删除的角色','info');  
            return;
        }
        
        var array = new Array();
        var rows= $('#grid').datagrid("getSelections");//获得所有选中的行
        for(var i=0;i<rows.length;i++){
			var school = rows[i];//json对象
			var id = school.id;
			array.push(id);
		}
		var ids = array.join(",");//1,2,3,4,5
        parent.$.messager.confirm('询问', '删除角色，也将删除该角色下所有权限，是否要删除所选角色？', function(b) {
			if (b) {
				$.post('${ctx}/role/deleteBatch', {
					ids : ids
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示信息', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
				}, 'JSON');
			}
		});
       	
	}
	
	var columns = [ [ {
		field : 'id',
		checkbox : true
	},{
		field : 'name',
		title : '角色名称',
		width : 120,
		align : 'center'
	},{
		field : 'code',
		title : '角色标识',
		width : 120,
		align : 'center'
	},{
		field : 'description',
		title : '角色描述',
		width : 120,
		align : 'center'
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
			url : "${ctx}/role/listRole",
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		$('#grid').datagrid({
			rowStyler: function(index,row){
				if (index % 2 != 0){
					return 'background-color:#6293BB;color:#fff;';
				}else{
					return 'background-color:#8258FA;color:#fff;';
				}
			}
		});
		
	});



	function doDblClickRow(rowIndex, rowData){
		var id = rowData.id;
		parent.$.modalDialog({
			title : '编辑',
			width : 400,
			height : 700,
			href : '${ctx}/role/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#schoolEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function doSearch() {
			dataGrid.datagrid('load', {
				sname:$('#name').val(),
				sphone:$('#phone').val(),
				scardno:$('#cardno').val(),
				starttime:$('#createdatetimeStart').val(),
				endtime:$('#createdatetimeEnd').val()
			});
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false" title="角色列表">
    	<table id="grid"></table>
	</div>
	<shiro:hasPermission name="role-list">
	<form id="searchForm">
		<div id="toolbar" style="padding:2px 5px;">
        	<table>
				<tr>
					<td><a href="javascript:void(0);" onclick="doAdd()" class="easyui-linkbutton" iconCls="icon-add">增加</a></td>
					<td><a href="" onclick="doEdit();return false" class="easyui-linkbutton" iconCls="icon-edit">编辑</a></td>
					<td><a href="" onclick="doDelete();return false" class="easyui-linkbutton" iconCls="icon-cancel">删除</a></td>
					<!-- <td><a href="" onclick="doRestore();return false" class="easyui-linkbutton" iconCls="icon-save">恢复</a></td>
					<th>权限名称:</th>
					<td><input id="name" name="name" placeholder="请输入权限名称" style = "width:150px;"/></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="doSearch()">查询</a></td> -->
				</tr>
			</table>
    	</div>
    </form></shiro:hasPermission>
</body>
</html>	