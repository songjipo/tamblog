<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%request.setCharacterEncoding("UTF-8"); %>
<script type="text/javascript">


	$(function() {
		
		$('#schoolAddForm').form({
			url : '${ctx}/student/addStudent',
			onSubmit : function(param) {
				param.schoolid = $('#schoolid').combobox('getValue');
				param.classesid = $('#classesid').combobox('getValue');
				param.genderid =  $('#gender').combobox('getValue');
				param.birthday = $('#birthday').val();
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
		
		
		$('#schoolid').combobox({
			url:'${ctx}/school/getschool',
		    editable:false,
		    cache: false,
		    valueField:'id',   
		    textField:'name',
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
		    textField:'name',
		    onLoadSuccess: function () {
            	var val = $('#classesid').combobox('getData');  
                for (var item in val[0]) {  
                    if (item == 'id') {  
                        $(this).combobox('select', val[0][item]);  
                    }  
                } 
            }
		 });
		
		
		
		
		/* $('#classesid').combobox({ 
		    url:'${ctx}/classes/getclasses',
		    editable:false,
		    cache: false,
		    valueField:'id',   
		    textField:'name',
		    onLoadSuccess: function () {
            	var val = $('#classesid').combobox('getData');  
                for (var item in val[0]) {  
                    if (item == 'id') {  
                        $(this).combobox('select', val[0][item]);  
                    }  
                } 
            }
          }); */
		
		$("#gender").combobox({
		    valueField: 'value',
		    textField: 'label',
		    //panelHeight:"auto",
		    data: [{
		          label: '男',
		          value: '1',
		          selected:true //默认选中项
		    }, {
		          label: '女',
		          value: '0'
		    }]
		});
		
	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="schoolAddForm" method="post">
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
						<td><input type="text" data-options="validType:'name',required:true" name="name" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>性别</td>
						<td><input id="gender" type="text" name="gender" class="easyui-combobox easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>民族</td>
						<td><input type="text" data-options="validType:'CHS'" name="nation" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>出生日期</td>
						<td><input id="birthday" name="birthday" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" />
					</tr>
					<tr>
						<td>地址</td>
						<td><input type="text" name="address" class="easyui-validatebox"/></td>
					</tr>
					</table>
		</form>
	</div>
</div>