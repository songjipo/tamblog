<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%request.setCharacterEncoding("UTF-8"); %>
<style type="text/css">
.ztree-wrap {width:200px; height:480px; overflow:auto;}
</style>
<script type="text/javascript" src="${ctx }/js/ztree/jquery.ztree.all.js"></script>
<script type="text/javascript">


	$(function() {
		
		$('#schoolAddForm').form({
			url : '${ctx}/role/addRole',
			onSubmit : function() {
				var isValid = $(this).form('validate');
				//根据ztree的id获取ztree对象
				var treeObj = $.fn.zTree.getZTreeObj("functionTree");
				//获取ztree上选中的节点，返回数组对象
				var nodes = treeObj.getCheckedNodes(true);
				var array = new Array();
				for(var i=0;i<nodes.length;i++){
					var id = nodes[i].id;
					array.push(id);
				}
				var functionIds = array.join(",");
				//为隐藏域赋值（权限的id拼接成的字符串）
				$("input[name=functionIds]").val(functionIds);
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
		
		// 授权树初始化
		var setting = {
			data : {
				key : {
					title : ""
				},
				simpleData : {
					enable : true
				}
			},
			check : {
				enable : true//使用ztree的勾选效果
			}
		};
		
		$.ajax({
			url : '${ctx}/function/getall',
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				$.fn.zTree.init($("#functionTree"), setting, data);
				var treeObj = $.fn.zTree.getZTreeObj("functionTree");
	         	   treeObj.expandAll(true);
			},
			error : function(msg) {
				alert('树加载异常!');
			}
		});
		
	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="schoolAddForm" method="post">
		<input type="hidden" name="functionIds">
			<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">角色信息</td>
					</tr>
					<tr>
						<td>角色名称</td>
						<td><input type="text" data-options="validType:'CHS'" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>角色关键字</td>
						<td><input type="text" name="code" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>角色描述</td>
						<td><input type="text" data-options="validType:'CHS'" name="description" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>授权</td>
						<td><div class="ztree-wrap"><ul id="functionTree" class="ztree"></ul></div></td>
					</tr>
					</table>
		</form>
	</div>
</div>