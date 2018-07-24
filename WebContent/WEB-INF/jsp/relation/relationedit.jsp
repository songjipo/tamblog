<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

	$(function() {
		$('#schoolEditForm').form({
			url : '${ctx}/relation/editRelation',
			onSubmit : function(param) {
				param.schoolid = $("#schoolid").combobox('getValue');
				var isValid = $(this).form('validate');
				return isValid;
			},
			success : function(result) {
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.messager.alert('提示信息', result.msg, 'info');
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
		
		$('#schoolid').combobox({
		    url:'${ctx}/school/getschool',
		    editable:false,
		    cache: false,
		    valueField:'id',   
		    textField:'name',
		    onLoadSuccess: function () {
		    	$("#schoolid").combobox("setValue", '${relation.schoolid}');
		        $("#schoolid").combobox("setText", '${relation.schoolname}');
            },
          });
		
		$("#schoolid").combobox({ disabled: true });
		
	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="schoolEditForm" method="post">
		<input name="id" type="hidden" value="${relation.id}">
			<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">关系信息</td>
					</tr>
					<tr>
						<td>幼儿园名称</td>
						<td><input name="schoolid" id="schoolid" class="easyui-combobox easyui-validatebox"></td>
					</tr>
					<tr>
						<td>关系名称</td>
						<td><input type="text" data-options="validType:'CHS'" name="name" class="easyui-validatebox" required="true" value="${relation.name }"/></td>
					</tr>
					</table>
		</form>
	</div>
</div>