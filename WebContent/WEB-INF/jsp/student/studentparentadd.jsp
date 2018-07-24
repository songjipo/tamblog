<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%request.setCharacterEncoding("UTF-8"); %>
<script type="text/javascript">


	$(function() {
		
		$('#schoolAddForm').form({
			url : '${ctx}/student/addParent',
			onSubmit : function(param) {
				param.id = $('#stuid').val();
				param.relationid = $('#relationid').combobox('getValue');
				param.cardno = $('#cardno').val();
				var isValid = $(this).form('validate');
				return isValid;
			},
			success : function(result) {
				result = $.parseJSON(result);
				if (result.success) {
					//parent.$.modalDialog.openner_dataGrid.datagrid('reload');
					parent.$.modalDialog.handler.dialog('close');
					parent.$.messager.alert('提示', result.msg, 'info');
				} else {
					parent.$.messager.alert('提示', result.msg, 'warning');
				}
			}
		});
		var schoolid = ${stu.schoolid};
		$('#relationid').combobox({ 
		    url:'${ctx}/relation/getrelationsbyid',
		    queryParams:{"code":schoolid},
		    editable:false, //不可编辑状态
		    cache: false,
		    //panelHeight: 'auto',//自动高度适合
		    valueField:'id',   
		    textField:'name',
		    onLoadSuccess: function () {
            	var val = $('#relationid').combobox('getData');  
                for (var item in val[0]) {  
                    if (item == 'id') {  
                        $(this).combobox('select', val[0][item]);  
                    }  
                } 
            }
          });
		
	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="schoolAddForm" method="post">
		<input id="stuid" name="stuid" type="hidden" value="${stu.id}">
			<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">家长信息</td>
					</tr>
					<tr>
						<td>家长姓名</td>
						<td><input type="text" data-options="validType:'name',required:true" name="name" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>关系</td>
						<td><input name="relationid" id="relationid" data-options="required:true" class="easyui-combobox easyui-validatebox"/></td>
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
							name="phone" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>卡号</td>
						<td><input type="text" data-options="validType:'numberCheckSub',required:true" id="cardno" name="cardno" class="easyui-validatebox"/></td>
					</tr>
					<!-- <tr>
						<td>性别</td>
						<td><input id="gender" type="text" name="gender" class="easyui-combobox easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>民族</td>
						<td><input type="text" name="nation" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>出生日期</td>
						<td><input id="birthday" name="birthday" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" readonly="readonly" />
					</tr>
					<tr>
						<td>地址</td>
						<td><input type="text" name="address" class="easyui-validatebox"/></td>
					</tr> -->
					</table>
		</form>
	</div>
</div>