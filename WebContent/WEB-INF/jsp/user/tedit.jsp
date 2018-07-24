<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style type="text/css">
  .red {
   color: #F00;
   font-size: 13px;
  }
  </style>
<script type="text/javascript">

	$(function() {
		$('#schoolEditForm').form({
			url : '${ctx}/user/editUser',
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
		
		var checkdata;
		var userId = ${tuser.id};
		$.ajax({  
            url: '${ctx}/role/getbyid',  
            data: {"userId" : userId},   
            type: 'post',  
            async:false,  
            dataType: "json",  
            success: function(menus){
            	checkdata=menus;
            }  
          });
		
		$.ajax({
			url : '${ctx}/role/getall',
			type : 'POST',
			dataType : 'json',
			async:false,
			success : function(data) {
				for(var i=0;i<data.length;i++){
						var id = data[i].id;
						var name = data[i].name;
						$("#roleTD").append('<input id="'+id+'" type="checkbox" name="roleIds" value="'+id+'"><label for="'+id+'">'+name+'</label>'+'</br>');
					}
				
				var boxObj = $("input[name='roleIds']");
				for(i=0;i<boxObj.length;i++){
					for(j=0;j<checkdata.length;j++){
						if(boxObj[i].value == checkdata[j].id){
							boxObj[i].checked= true;
							break;
						}
					}
				}
			}
		});
		
	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="schoolEditForm" method="post">
		<input name="id" type="hidden" value="${tuser.id}">
			<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">用户信息</td>
					</tr>
					<tr>
						<td>用户名称</td>
						<td><input type="text" data-options="validType:'stringCheck',required:true,readonly:true" name="username" class="easyui-validatebox" value="${tuser.username }"/><strong class="red">不可编辑</strong></td>
					</tr>
					<tr>
						<td>手机</td>
						<td>
							<script type="text/javascript">
								$(function(){
									var reg = /^1[3|4|5|7|8|9][0-9]{9}$/;
									//扩展手机号校验规则
									$.extend($.fn.validatebox.defaults.rules, { 
										telephone: { 
											validator: function(value,param){ 
											return reg.test(value);
										}, 
											message: '手机号输入有误！' 
										}
										}); 
									});
							</script>
						<input type="text" data-options="validType:'telephone',required:true,readonly:true" name="phone" class="easyui-validatebox" value="${tuser.phone }"/><strong class="red">不可编辑</strong></td>
					</tr>
					<tr>
	           		<td>选择角色:</td>
	           		<td colspan="3" id="roleTD">
	           			<!-- <script type="text/javascript">
	           				$(function(){
	           					$.post('${ctx}/role/getall',function(data){
	           						for(var i=0;i<data.length;i++){
	           							var id = data[i].id;
	           							var name = data[i].name;
	           							$("#roleTD").append('<input id="'+id+'" type="checkbox" name="roleIds" value="'+id+'"><label for="'+id+'">'+name+'</label>'+'</br>');
	           						}
	           					});
	           				});
	           			</script> -->
	           		</td>
	           	 </tr>
					</table>
		</form>
	</div>
</div>