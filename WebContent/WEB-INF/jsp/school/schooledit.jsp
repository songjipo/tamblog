<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

	$(function() {
		$('#schoolEditForm').form({
			url : '${ctx}/school/editSchool',
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
		
		// 下拉框选择控件，下拉框的内容是动态查询数据库信息
	 $('#province').combobox({ 
				    url:'${ctx}/region/getprovince',
				    editable:false, //不可编辑状态
				    cache: false,
				    //panelHeight: 'auto',//自动高度适合
				    valueField:'codeid',
				    textField:'cityName',
				    onLoadSuccess : function() { 
				        $("#province").combobox("setValue", '${sch.provinceid}');
				        $("#province").combobox("setText", '${sch.provincename}');
				        $("#city").combobox("setValue", '${sch.cityid}');
				        $("#city").combobox("setText", '${sch.cityname}');
				        $("#town").combobox("setValue", '${sch.townid}');
				        $("#town").combobox("setText", '${sch.townname}');
				        
				        var province = $('#province').combobox('getValue');
						if(province!=''){
						$.ajax({
							type: "POST",
							data:{code:province},
							url: "${ctx}/region/getcity",
							cache: false,
							dataType : "json",
							success: function(data){
							$("#city").combobox("loadData",data);
					                               }
				                               }); 	
			                           }
						var city = $('#city').combobox('getValue');		
						if(city!=''){
						$.ajax({
							type: "POST",
							data:{code:city},
							url: "${ctx}/region/gettown",
							cache: false,
							dataType : "json",
							success: function(data){
							$("#town").combobox("loadData",data);
					                               }
				                               }); 	
			                           }
				        
				    },
				    
				    onHidePanel: function(){

			    $("#city").combobox("setValue",'');
			    $("#town").combobox("setValue",'');
				var province = $('#province').combobox('getValue');
				if(province!=''){
				$.ajax({
					type: "POST",
					data:{code:province},
					url: "${ctx}/region/getcity",
					cache: false,
					dataType : "json",
					success: function(data){
					$("#city").combobox("loadData",data);
			                               }
		                               }); 	
	                           }
	                     } 
	                 }); 
		
		$('#city').combobox({ 

		    editable:false, //不可编辑状态
		    cache: false,
		    //panelHeight: 'auto',//自动高度适合
		    valueField:'codeid',   
		    textField:'cityName',
		    onHidePanel: function(){
			    $("#town").combobox("setValue",'');
				var city = $('#city').combobox('getValue');		
				if(city!=''){
				$.ajax({
					type: "POST",
					data:{code:city},
					url: "${ctx}/region/gettown",
					cache: false,
					dataType : "json",
					success: function(data){
					$("#town").combobox("loadData",data);
			                               }
		                               }); 	
	                           }
	                     }
		   });  
		$('#town').combobox({ 
		    editable:false, //不可编辑状态
		    cache: false,
		   // panelHeight: 'auto',//自动高度适合
		    valueField:'codeid',   
		    textField:'cityName'
		 });
		
	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="schoolEditForm" method="post">
		<input name="id" type="hidden" value="${sch.id}">
		<input name="status" type="hidden" value="${sch.status}">
		<input name="createtime" type="hidden" value="${sch.createtime}">
			<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">幼儿园信息</td>
					</tr>
					<tr>
						<td>幼儿园名称</td>
						<td><input type="text" data-options="validType:'stringCheck',required:true" name="name" class="easyui-validatebox" value="${sch.name }"/></td>
					</tr>
					<tr>
						<td>省份</td>
						<td><input name="provinceid" id="province" class="easyui-combobox" data-options="required: true,missingMessage:'请选择省份'"></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input name="cityid" id="city" class="easyui-combobox" data-options="required: true,missingMessage:'请选择市'"></td>
					</tr>
					<tr>	
						<td>县/区</td>
						<td><input name="townid" id="town" class="easyui-combobox" data-options="required: true,missingMessage:'请选择县/区'"></td>
					</tr>
					<tr>
						<td>街道地址</td>
						<td><input type="text" name="street" class="easyui-validatebox" data-options="required: true" value="${sch.street }"/></td>
					</tr>
					<tr>
						<td>负责人</td>
						<td><input type="text" data-options="validType:'name',required:true" name="manager" class="easyui-validatebox" value="${sch.manager }"/></td>
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
						<input type="text" data-options="validType:'telephone',required:true" 
							name="phone" class="easyui-validatebox" value="${sch.phone }"/></td>
					</tr>
					<tr>
						<td>备注信息</td>
						<td><input name="note" class="easyui-textbox" data-options="multiline:true" style="height:100px" value="${sch.note }"/></td>
					</tr>
					</table>
		</form>
	</div>
</div>