<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

	$(function() {
		$('#schoolEditForm').form({
			url : '${ctx}/student/editStudent',
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
			url:'${ctx}/school/getschool',
		    editable:false,
		    cache: false,
		    valueField:'id',   
		    textField:'name',
		    onLoadSuccess : function() { 
		        $("#schoolid").combobox("setValue", '${stu.schoolid}');
		        $("#schoolid").combobox("setText", '${stu.schoolname}');
		        $("#classesid").combobox("setValue", '${stu.classesid}');
		        $("#classesid").combobox("setText", '${stu.classesname}');
		        
		        var schoolid = $('#schoolid').combobox('getValue');
				if(schoolid!=''){
				$.ajax({
					type: "POST",
					data:{code:schoolid},
					url: "${ctx}/classes/getbyid",
					cache: false,
					dataType : "json",
					success: function(data){
					$("#classesid").combobox("loadData",data);
			                               }
		                               }); 	
	             }
		    },
		    onHidePanel: function(){
			    $("#classesid").combobox("setValue",'');
			    var schoolid = $('#schoolid').combobox('getValue');		
				if(schoolid!=''){
				$.ajax({
					type: "POST",
					data:{code:schoolid},
					url: "${ctx}/classes/getbyid",
					cache: false,
					dataType : "json",
					success: function(data){
					$("#classesid").combobox("loadData",data);
			                               }
		                               });
		    			}
	                     }
		   });  
		
		$('#classesid').combobox({ 
		    editable:false,
		    cache: false,
		    valueField:'id',   
		    textField:'name'
		 });
		
		$("#gender").combobox({
		    valueField: 'value',
		    textField: 'label',
		    //panelHeight:"auto",
		    onLoadSuccess: function () {
		    	$("#gender").combobox("setValue", '${stu.gender}');
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
		<input name="id" type="hidden" value="${stu.id}">
			<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">学生信息</td>
					</tr>
					<tr>
						<td>选择幼儿园</td>
						<td><input name="schoolid" id="schoolid" class="easyui-combobox easyui-validatebox" data-options="required: true,missingMessage:'请选择幼儿园'"/></td>
					</tr>
					<tr>
						<td>选择班级</td>
						<td><input name="classesid" id="classesid" class="easyui-combobox easyui-validatebox" data-options="required: true,missingMessage:'请选择班级'"/></td>
					</tr>
					<tr>
						<td>姓名</td>
						<td><input type="text" data-options="validType:'name'" name="name" class="easyui-validatebox" required="true" value="${stu.name }"/></td>
					</tr>
					<tr>
						<td>性别</td>
						<td><input id="gender" type="text" name="gender" class="easyui-combobox easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>民族</td>
						<td><input type="text" data-options="validType:'CHS'" name="nation" class="easyui-validatebox" value="${stu.nation }"/></td>
					</tr>
					<tr>
						<td>出生日期</td>
						<td><input id="birthday" name="birthday" value="${stu.birthday }" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" />
					</tr>
					<tr>
						<td>地址</td>
						<td><input type="text" name="address" class="easyui-validatebox" value="${stu.address }"/></td>
					</tr>
					
					
					</table>
		</form>
	</div>
</div>