<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%request.setCharacterEncoding("UTF-8"); %>
<script type="text/javascript">


	$(function() {
		
		$('#schoolAddForm').form({
			url : '${ctx}/relation/addRelation',
			onSubmit : function(param) {
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
		    editable:false, //不可编辑状态
		    cache: false,
		    //panelHeight: 'auto',//自动高度适合
		    valueField:'id',   
		    textField:'name',
		    onLoadSuccess:function(){   
                var data = $('#schoolid').combobox('getData');//获取所有下拉框数据  
                if (data.length > 0) {  
                    $('#schoolid').combobox('select',data[0].id);  
                }  
            }
          });
		
	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="schoolAddForm" method="post">
			<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">关系信息</td>
					</tr>
					<tr>
						<td>幼儿园名称</td>
						<td><input name="schoolid" id="schoolid" class="easyui-combobox easyui-validatebox"></td>
					</tr>
					<tr>
						<td>关系名称</td>
						<td><input type="text" data-options="validType:'CHS'" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					</table>
		</form>
	</div>
</div>