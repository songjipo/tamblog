<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

	$(function() {
		$('#schoolEditForm').form({
			url : '${ctx}/classes/editClasses',
			onSubmit : function() {
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
		
	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="schoolEditForm" method="post">
		<input name="id" type="hidden" value="${classes.id}">
		<input name="schoolid" type="hidden" value="${classes.schoolid}">
		<input name="status" type="hidden" value="${classes.status}">
		<input name="createtime" type="hidden" value="${classes.createtime}">
			<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">班级信息</td>
					</tr>
					<tr>
						<td>班级名称</td>
						<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true"value="${classes.name }"/></td>
					</tr>
					</table>
		</form>
	</div>
</div>