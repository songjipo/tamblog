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
		
		$('#schoolEditForm').form({
			url : '${ctx}/role/editRole',
			onSubmit : function(param) {
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
				param.funcids = functionIds;
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
				view: {
		                showTitle: false,
		            },
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
		
		
		var treeNode;
		var roleId = ${role.id};
		$.ajax({  
            url: '${ctx}/function/getbyid',  
            data: {"roleId" : roleId},   
            type: 'post',  
            async:false,  
            dataType: "json",  
            success: function(menus){
            	treeNode=menus;
            }  
          });
		
		$.ajax({
			url : '${ctx}/function/getall',
			type : 'POST',
			dataType : 'json',
			async:false,
			success : function(data) {
				$.fn.zTree.init($("#functionTree"), setting, data);
				if (treeNode.length > 0) {
					var treeObj = $.fn.zTree.getZTreeObj("functionTree");
					for (var i = 0; i < treeNode.length; i++){
						if (treeNode[i].pId!=0 || ! ("0"==treeNode[i].pId )){
							var nodes = treeObj.getNodesByParam("id", treeNode[i].id, null);
							treeObj.checkNode(nodes[0],true,true);
						}else{
							var nodes = treeObj.getNodesByParam("id", treeNode[i].id, null);
							treeObj.checkNode(nodes[0],true,false);
							treeObj.expandNode(nodes[0]);
						}
					}
				}
			},
			error : function(msg) {
				alert('树加载异常!');
			}
		});
	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="schoolEditForm" method="post">
		<input type="hidden" name="functionIds">
		<input name="id" type="hidden" value="${role.id}">
			<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">角色信息</td>
					</tr>
					<tr>
						<td>角色名称</td>
						<td><input type="text" data-options="validType:'CHS'" name="name" class="easyui-validatebox" required="true" value="${role.name }"/></td>
					</tr>
					<tr>
						<td>角色关键字</td>
						<td><input type="text" name="code" class="easyui-validatebox" required="true" value="${role.code }"/></td>
					</tr>
					<tr>
						<td>角色描述</td>
						<td><input type="text" data-options="validType:'CHS'" name="description" class="easyui-validatebox" required="true" value="${role.description }"/></td>
					</tr>
					<tr>
						<td>授权</td>
						<td><div class="ztree-wrap"><ul id="functionTree" class="ztree"></ul></div></td>
					</tr>
					</table>
		</form>
	</div>
</div>