<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%request.setCharacterEncoding("UTF-8"); %>
<script type="text/javascript">


	$(function() {
		
		$('#schoolAddForm').form({
			url : '${ctx}/function/addFunction',
			onSubmit : function(param) {
				param.parentid =  $('#parentid').combotree('getValue');
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
					parent.$.messager.alert('提示', result.msg, 'warning');
				}
			}
		});
		
		$('#parentid').combotree({ 
		    url:'${ctx}/function/getall',
          });
		
		$("#generatemenu").combobox({
		    valueField: 'value',
		    textField: 'label',
		    //panelHeight:"auto",
		    data: [{
		          label: '是',
		          value: '1'
		    }, {
		          label: '否',
		          value: '0',
		          selected:true //默认选中项
		    }]
		});
		
	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="schoolAddForm" method="post">
			<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">权限信息</td>
					</tr>
					<tr>
						<td>权限名称</td>
						<td><input type="text" data-options="validType:'CHS'" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>权限关键字</td>
						<td><input type="text" name="code" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>权限描述</td>
						<td><input type="text" data-options="validType:'CHS'" name="description" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>权限路径</td>
						<td><input type="text" name="url" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>是否生成菜单</td>
						<td><input id="generatemenu" name="generatemenu" class="easyui-combobox easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>优先级</td>
						<td><input type="text" data-options="validType:'numberCheckSub'" name="zindex" class="easyui-validatebox"/></td>
					</tr>
					<!-- <tr>
						<td>上级权限</td>
						<td><input id="parentid" name="parentid" class="easyui-combobox easyui-validatebox"/></td>
					</tr> -->
					<tr>
						<td>上级权限</td>
						<!-- <td><input id="parentid" name="parentFunction.id" class="easyui-combotree"/></td> -->
						<td><input id="parentid" name="parentid" class="easyui-combotree"/></td>
					</tr>
					</table>
		</form>
	</div>
</div>