<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

	$(function() {
		$('#schoolEditForm').form({
			url : '${ctx}/teacher/editTeacher',
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
		
		
		$('#schoolid').combobox({
			url:'${ctx}/school/getSchoolById',
		    editable:false,
		    cache: false,
		    valueField:'id',   
		    textField:'name',
		    onLoadSuccess : function() { 
		        $("#schoolid").combobox("setValue", '${teacher.schoolid}');
		        $("#schoolid").combobox("setText", '${schoolname}');
		        
		        
		        var checkdata;
				var teacherId = ${teacher.id};
				var schoolid = ${teacher.schoolid};
				$.ajax({  
		            url: '${ctx}/classes/getbyteacherid',
		            data: {teacherId : teacherId},   
		            type: 'post',  
		            async:false,  
		            dataType: "json",  
		            success: function(menus){
		            	checkdata=menus;
		            }  
		          });
				
				$.ajax({
					url : '${ctx}/classes/getClassesBySchoolid',
					data: {schoolid : schoolid}, 
					type : 'post',
					dataType : 'json',
					async:false,
					success : function(data) {
						var x = document.getElementsByName('classIds');
						if(x.length > 0){
							$("#classesTD").empty();
						}
						for(var i=0;i<data.length;i++){
								var id = data[i].id;
								var name = data[i].name;
								$("#classesTD").append('<input id="'+id+'" type="checkbox" name="classIds" value="'+id+'"><label for="'+id+'">'+name+'</label>');
								if(((i+1)%4) == 0){
	   								$("#classesTD").append('</br>');
	   							}
							}
						
						var boxObj = $("input[name='classIds']");
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
		        
		    },
		    onHidePanel: function(){
			    var schoolid = $('#schoolid').combobox('getValue');		
				if(schoolid != ''){
				$.ajax({
						type: "POST",
						data:{schoolid:schoolid},
						url: "${ctx}/classes/getClassesBySchoolid",
						cache: false,
						dataType : "json",
						success: function(data){
							var x = document.getElementsByName('classIds');
							if(x.length > 0){
								$("#classesTD").empty();
							}
	   						for(var i=0;i<data.length;i++){
	   							var id = data[i].id;
	   							var name = data[i].name;
	   							$("#classesTD").append('<input id="'+id+'" type="checkbox" name="classIds" value="'+id+'"><label for="'+id+'">'+name+'</label>');
	   							if(((i+1)%4) == 0){
	   								$("#classesTD").append('</br>');
	   							}
	   						}
	   					}
		             });
		    	}
	          }
		   });
		
		$("#schoolid").combobox({ disabled: true });  //控制教师只能同校调整
		
		
		
		$("#gender").combobox({
		    valueField: 'value',
		    textField: 'label',
		    onLoadSuccess: function () {
		    	$("#gender").combobox("setValue", '${teacher.gender}');
            },
		    data: [{
		          label: '男',
		          value: '1'
		    }, {
		          label: '女',
		          value: '0'
		    }]
		});
		
	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="schoolEditForm" method="post">
		<input name="id" type="hidden" value="${teacher.id}">
		<input name="status" type="hidden" value="${teacher.status}">
		<input name="createtime" type="hidden" value="${teacher.createtime}">
		<input name="schoolid" type="hidden" value="${teacher.schoolid}">
		<input name="userid" type="hidden" value="${teacher.userid}">
			<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="4">教师信息</td>
					</tr>
					<tr>
						<td>姓名</td>
						<td><input type="text" data-options="validType:'name',required:true" name="name" class="easyui-validatebox" value="${teacher.name }"/></td>
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
						<input type="text" data-options="validType:'telephone',required:true" name="phone" class="easyui-validatebox" value="${teacher.phone }"/>
						</td>
					</tr>
					<tr>
						<td>卡号</td>
						<td><input type="text" data-options="validType:'numberCheckSub',required:true" name="cardno" class="easyui-validatebox" value="${teacher.cardno }"/></td>
						<td>性别</td>
						<td><input id="gender" type="text" name="gender" class="easyui-combobox easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>民族</td>
						<td><input type="text" data-options="validType:'CHS'" name="nation" class="easyui-validatebox" value="${teacher.nation }"/></td>
						<td>出生日期</td>
						<td><input id="birthday" name="birthday" value="${teacher.birthday }" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" />
					</tr>
					<tr>
						<td>幼儿园名称</td>
						<td><input name="schoolid" id="schoolid" class="easyui-combobox easyui-validatebox" data-options="required: true,missingMessage:'请选择幼儿园'"></td>
					</tr>
					<tr>
						<td>选择班级:</td>
		           		<td colspan="3" id="classesTD">
		           		</td>
					</tr>
					</table>
		</form>
	</div>
</div>