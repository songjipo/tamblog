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
<title>学生管理</title>

<script type="text/javascript">
	var dataGrid;
	function doAdd(){
		parent.$.modalDialog({
			title : '添加',
			width : 400,
			height : 400,
			href : '${ctx}/student/addPage',
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
			height : 400,
			href : '${ctx}/student/editPage?id=' + id,
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
            $.messager.alert('提示信息','请选择要禁用的学生','info');  
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
        parent.$.messager.confirm('询问', '您是否要禁用所选学生？', function(b) {
			if (b) {
				$.post('${ctx}/student/deleteBatch', {
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
	
	function doRestore(){
		var ss = $('#grid').datagrid('getChecked');    
        if (ss.length == 0) {
        	parent.$.messager.alert('提示信息', "请选择要恢复正常状态的学生", 'info');
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
        
        parent.$.messager.confirm('询问', '您是否要恢复所选学生？', function(b) {
			if (b) {
				$.post('${ctx}/student/restoreBatch', {
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
	
	function doBind(){
		var ss = $('#grid').datagrid('getChecked');    
        if (ss.length == 0) {
            $.messager.alert('提示信息','请选择要绑定的学生','warning');  
            return;
        }
        if (ss.length > 1) {
            $.messager.alert('提示信息','一次只能绑定一个学生','warning');  
            return;
        }
        var id;
		var row = $('#grid').datagrid('getSelected');  
       	id = row.id;
		
		parent.$.modalDialog({
			title : '绑定家长信息',
			width : 400,
			height : 400,
			href : '${ctx}/student/addParentPage?id=' + id,
			buttons : [ {
				text : '添加家长信息',
				handler : function() {
					//parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#schoolAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'name',
		title : '学生姓名',
		width : 120,
		align : 'center'
	},{
		field : 'classesname',
		title : '所属班级',
		width : 120,
		align : 'center'
	},{
		field : 'schoolname',
		title : '所属幼儿园',
		width : 120,
		align : 'center'
	},{
		field : 'gender',
		title : '性别',
		width : 120,
		align : 'center',
		sortable : true,
		formatter : function(data,row, index){
			if(data=="1"){
				return "男"
			}else{
				return "女";
			}
		}
	},{
		field : 'nation',
		title : '民族',
		width : 120,
		align : 'center'
	},{
		field : 'birthday',
		title : '出生日期',
		width : 120,
		align : 'center',
		sortable : true
	},{
		field : 'address',
		title : '地址',
		width : 120,
		align : 'center'
	},{
		field : 'createtime',
		title : '创建日期',
		width : 200,
		align : 'center',
		sortable : true
	},{
		field : 'status',
		title : '状态',
		width : 120,
		align : 'center',
		sortable : true,
		formatter : function(data,row, index){
			if(data=="1"){
				return "正常"
			}else{
				return "禁用";
			}
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
			url : "${ctx}/student/listStudent",
			idField : 'id',
			sortName : 'createtime',
			sortOrder : 'desc',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
	});

	function doDblClickRow(rowIndex, rowData){
		var id = rowData.id;
		parent.$.modalDialog({
			title : '编辑',
			width : 400,
			height : 400,
			href : '${ctx}/student/editPage?id=' + id,
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
		var flag = true;
		var starttime = $('#createdatetimeStart').val();
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
		}
		if(flag){
			dataGrid.datagrid('load', {
				sname:$('#name').val(),
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


	function cleansearch() {
		$('#searchForm : input').each(function() {
			$(this).val('');
			//$("input[name='info[gqq_shuxing]'][value='不限']").attr("checked",true); 
		});
		//dataGrid.datagrid('load', {});
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;" onkeydown="enterlogin();">
	<div region="center" border="false" title="学生列表">
    	<table id="grid"></table>
	</div>
	<shiro:hasPermission name="student-addPage">
	<form id="searchForm">
		<div id="toolbar" style="padding:2px 5px;">
        	<table>
				<tr>
					<td><a href="javascript:void(0);" onclick="doAdd()" class="easyui-linkbutton" iconCls="icon-add">增加</a></td>
					<td><a href="" onclick="doEdit();return false" class="easyui-linkbutton" iconCls="icon-edit">编辑</a></td>
					<td><a href="" onclick="doDelete();return false" class="easyui-linkbutton" iconCls="icon-cancel">禁用</a></td>
					<td><a href="" onclick="doRestore();return false" class="easyui-linkbutton" iconCls="icon-save">恢复</a></td>
					<td><a href="javascript:void(0);" onclick="doBind();return false" class="easyui-linkbutton" iconCls="icon-add">绑定卡片</a></td>
					<th>学生姓名:</th>
					<td><input id="name" name="name" placeholder="请输入学生姓名" style = "width:150px;"/></td>
					<th>出生日期:</th>
					<td><input id="createdatetimeStart" name="createdatetimeStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" />至<input id="createdatetimeEnd" name="createdatetimeEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" />
					<td><a id="search" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="doSearch()">查询</a></td>
				</tr>
			</table>
    	</div>
    </form></shiro:hasPermission>
</body>
</html>	