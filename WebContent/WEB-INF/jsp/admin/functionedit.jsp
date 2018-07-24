<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

	$(function() {
		$('#schoolEditForm').form({
			url : '${ctx}/function/editFunction',
			onSubmit : function(param) {
				param.pid = $('#parentid').combotree('getValue')
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
          $('#parentid').combotree({
  		    url:'${ctx}/function/getall',
  		    editable:false,
  		    onLoadSuccess: function () {
  		    	$("#parentid").combotree("setValue", '${func.parentFunction.id}');
  		        $("#parentid").combotree("setText", '${func.parentFunction.name}');
              },
            });
		
		$("#generatemenu").combobox({
		    valueField: 'value',
		    textField: 'label',
		    //panelHeight:"auto",
		    onLoadSuccess: function () {
		    	$("#generatemenu").combobox("setValue", '${func.generatemenu}');
            },
		    data: [{
		          label: '是',
		          value: '1'
		    }, {
		          label: '否',
		          value: '0'
		    }]
		});
		
	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="schoolEditForm" method="post">
		<input name="id" type="hidden" value="${func.id}">
			<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">权限信息</td>
					</tr>
					<tr>
						<td>权限名称</td>
						<td><input type="text" data-options="validType:'CHS'" name="name" class="easyui-validatebox" required="true" value="${func.name }"/></td>
					</tr>
					<tr>
						<td>权限关键字</td>
						<td><input type="text" name="code" class="easyui-validatebox" required="true" value="${func.code }"/></td>
					</tr>
					<tr>
						<td>权限描述</td>
						<td><input type="text" data-options="validType:'CHS'" name="description" class="easyui-validatebox" required="true" value="${func.description }"/></td>
					</tr>
					<tr>
						<td>权限路径</td>
						<td><input type="text" name="url" class="easyui-validatebox" value="${func.url }"/></td>
					</tr>
					<tr>
						<td>是否生成菜单</td>
						<td><input id="generatemenu" name="generatemenu" class="easyui-combobox easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>优先级</td>
						<td><input type="text" data-options="validType:'numberCheckSub'" name="zindex" class="easyui-validatebox" value="${func.zindex }"/></td>
					</tr>
					<tr>
						<td>上级权限</td>
						<td><input id="parentid" name="parentFunction.id" class="easyui-combotree"/></td>
					</tr>
					</table>
		</form>
	</div>
</div>