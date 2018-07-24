<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../top.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>幼儿园管理</title>

<script type="text/javascript">
	var dataGrid;
	function doAdd(){
		parent.$.modalDialog({
			title : '添加',
			width : 500,
			height : 500,
			href : '${ctx}/school/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#schoolAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	function doEdit() {
        var ss = $('#grid').datagrid('getChecked');    
        if (ss.length == 0) {
            $.messager.alert('提示信息','请选择要编辑的数据','warning');  
            return;
        }
        if (ss.length > 1) {
            $.messager.alert('提示信息','一次只能编辑一条数据','warning');  
            return;
        }
        var id;
		var row = $('#grid').datagrid('getSelected');  
       	id = row.id;
       	
       	parent.$.modalDialog({
			title : '编辑',
			width : 500,
			height : 500,
			href : '${ctx}/school/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#schoolEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function doDelete(){
		var ss = $('#grid').datagrid('getChecked');    
        if (ss.length == 0) {
            $.messager.alert('提示信息','请选择要禁用的数据','info');  
            return;
        }
        
        var array = new Array();
        var rows= $('#grid').datagrid("getSelections");//获得所有选中的行
        for(var i=0;i<rows.length;i++){
			var school = rows[i];//json对象
			var id = school.id;
			array.push(id);
		}
		var ids = array.join(",");//1,2,3,4,5
        parent.$.messager.confirm('询问', '禁用幼儿园同时将禁用幼儿园所有班级，您是否要禁用所选幼儿园？', function(b) {
			if (b) {
				$.post('${ctx}/school/deleteBatch', {
					ids : ids
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示信息', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
				}, 'json');
			}
		});
       	
	}
	
	function doRestore(){
		var ss = $('#grid').datagrid('getChecked');    
        if (ss.length == 0) {
        	parent.$.messager.alert('提示信息', "请选择要恢复正常状态的幼儿园数据", 'info');
            return;
        }
        
        var array = new Array();
        var rows= $('#grid').datagrid("getSelections");//获得所有选中的行
        for(var i=0;i<rows.length;i++){
			var school = rows[i];//json对象
			var id = school.id;
			array.push(id);
		}
		var ids = array.join(",");//1,2,3,4,5
        
        parent.$.messager.confirm('询问', '恢复幼儿园同时将恢复幼儿园所有班级，您是否要恢复所选幼儿园？', function(b) {
			if (b) {
				$.post('${ctx}/school/restoreBatch', {
					ids : ids
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示信息', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
				}, 'json');
			}
		});
	}
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'name',
		title : '幼儿园名称',
		width : 120,
		align : 'center'
	},{
		field : 'provincename',
		title : '省市名称',
		width : 120,
		align : 'center'
	},{
		field : 'cityname',
		title : '城市名称',
		width : 120,
		align : 'center'
	},{
		field : 'townname',
		title : '区/县名称',
		width : 120,
		align : 'center'
	},{
		field : 'street',
		title : '街道地址',
		width : 120,
		align : 'center'
	},{
		field : 'manager',
		title : '负责人',
		width : 120,
		align : 'center'
	}, {
		field : 'phone',
		title : '手机号',
		width : 120,
		align : 'center',
		sortable : true
	},{
		field : 'createtime',
		title : '创建日期',
		width : 200,
		align : 'center',
		sortable : true
	}, {
		field : 'status',
		title : '幼儿园状态',
		width : 120,
		align : 'center',
		sortable : true,
		formatter : function(data,row, index){
			if(data=="1"){
				return "正常"
			}else{
				return "禁用";
			}
		}
	},{
		field : 'note',
		title : '备注',
		width : 300,
		align : 'center'
	}] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 幼儿园信息表格
		dataGrid = $('#grid').datagrid({
			fit : true,
			border : false,
			rownumbers : true,
			fitColumns : true,
			striped : true,
			pageSize : 30,
			pageList: [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			pagination : true,
			//toolbar : toolbar,
			toolbar:'#toolbar',
			url : "${ctx}/school/listSchool",
			idField : 'id',
			sortName : 'createtime',
			sortOrder : 'desc',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		$("#pid").combobox("setValue", '0');
        $("#pid").combobox("setText", '-请选择-');
		
		$.ajax({
            url: '${ctx}/region/getprovince',
            dataType: 'json',
            success: function (jsonstr) {
                jsonstr.unshift({
                    'codeid': '0',
                    'cityName': '-请选择-'
                });//向json数组开头添加自定义数据
                $('#pid').combobox({
                    data: jsonstr,
                    valueField: 'codeid',
                    textField: 'cityName',
                    onLoadSuccess: function () {
                    	$("#cid").combobox("setValue", '0');
                        $("#cid").combobox("setText", '-请选择-');
                        $("#tid").combobox("setValue", '0');
                        $("#tid").combobox("setText", '-请选择-');
                    	var val = $('#pid').combobox('getData');  
                        for (var item in val[0]) {  
                            if (item == 'codeid') {  
                                $(this).combobox('select', val[0][item]);  
                            }  
                        } 
                    }
                });
                
            }
        });
             
             $('#pid').combobox({
     		    editable:false, //不可编辑状态
     		    cache: false,
     		    //panelHeight: 'auto',//自动高度适合
     		    valueField:'codeid',
     		    textField:'cityName',
     		    onHidePanel: function(){

     			    $("#cid").combobox("setValue",'');
     			    $("#tid").combobox("setValue",'');
     				var province = $('#pid').combobox('getValue');
     				if(province!=''&& province !=0){
     				$.ajax({
     					type: "POST",
     					data:{code:province},
     					url: "${ctx}/region/getcity",
     					cache: false,
     					dataType : "json",
     					success: function(data){
     						data.unshift({  
                                 'codeid': '0',  
                                 'cityName': '-请选择-'  
                             });
     						$("#cid").combobox("loadData",data);
     						$('#cid').combobox('select', data[0]['codeid']);
     			         }
     		                     }); 	
     		         }
                  } 
                  });

		$('#cid').combobox({
		
		    editable:false, //不可编辑状态
		    cache: false,
		    //panelHeight: 'auto',//自动高度适合
		    valueField:'codeid',   
		    textField:'cityName',
		    onHidePanel: function(){
			    //$("#tid").combobox("setValue",'');
				var city = $('#cid').combobox('getValue');		
				if(city!=''&&city!=0){
				$.ajax({
					type: "POST",
					data:{code:city},
					url: "${ctx}/region/gettown",
					cache: false,
					dataType : "json",
					success: function(data){
						data.unshift({  
                            'codeid': '0',  
                            'cityName': '-请选择-'  
                        });	
						$("#tid").combobox("loadData",data);
						$('#tid').combobox('select', data[0]['codeid']);
			                               }
		                               }); 	
		                       }
		                 }
		   });  
		$('#tid').combobox({ 
		    editable:false, //不可编辑状态
		    cache: false,
		   // panelHeight: 'auto',//自动高度适合
		    valueField:'codeid',   
		    textField:'cityName'
		 }); 
		
	});

	function doDblClickRow(rowIndex, rowData){
		var id = rowData.id;
		parent.$.modalDialog({
			title : '编辑',
			width : 500,
			height : 500,
			href : '${ctx}/school/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#schoolEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function doSearch() {
		var flag = true;
		var starttime = $('#createdatetimeStart').val();
		starttime = starttime.replace(/\-/g, "/");
		var endtime = $('#createdatetimeEnd').val();
		endtime = endtime.replace(/\-/g, "/");
		if(starttime!=""&&endtime!=""&& starttime>endtime){
			parent.$.messager.alert('警告', '开始时间不能大于结束时间', 'warning');
			flag = false;
			return;
		}
		var currenttime = new Date();
		var start = new Date(Date.parse(starttime));
		var end = new Date(Date.parse(endtime));
		if(start >= currenttime){
			parent.$.messager.alert('警告', '开始时间不能大于当前时间', 'warning');
			flag = false;
			return;
		}
		if(flag){
			$('#grid').datagrid('load', {
				name:$('#name').val(),
				phone:$('#phone').val(),
				createdatetimeStart:$('#createdatetimeStart').val(),
				createdatetimeEnd:$('#createdatetimeEnd').val(),
				provinceid:$('#pid').combobox('getValue'),
				cityid:$('#cid').combobox('getValue'),
				townid:$('#tid').combobox('getValue'),
			});
			
			/* dataGrid.datagrid('load', $.serializeObject($('#searchForm'))); */
			
			/* var searchForm = $("#searchForm").form();
			$("#grid").datagrid('load',serializeObject(searchForm)); */
			
		}
	}
	
	
	function serializeObject(form) {
		var o = {};
		$.each(form.serializeArray(), function(index) {
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + this['value'];
			} else {
				o[this['name']] = this['value'];
			}
		})
		return o;
	}

	function enterlogin() {
		if (event.keyCode == 13) {
			event.returnValue = false;
			event.cancel = true;
			$('#search').trigger("click");
		}
	}

	function cleansearch() {
		$("#pid").combobox("setText", '-请选择-');
		$("#cid").combobox("setText", '-请选择-');
		$("#tid").combobox("setText", '-请选择-');
		$('#name').val('');
		$('#phone').val('');
		$('#createdatetimeStart').val('');
		$('#createdatetimeEnd').val('');
		//dataGrid.datagrid('load', {});
	}

	/* $.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	}; */
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;" onkeydown="enterlogin();">
	<div region="center" border="false" title="幼儿园列表">
    	<table id="grid"></table>
	</div>
	<form id="searchForm">
		<div id="toolbar" style="padding:2px 5px;">
        	<table>
				<tr>
					<shiro:hasPermission name="school-addPage"><td><a href="javascript:void(0);" onclick="doAdd()" class="easyui-linkbutton" iconCls="icon-add">增加</a></td></shiro:hasPermission>
					<shiro:hasPermission name="school-editPage"><td><a href="" onclick="doEdit();return false" class="easyui-linkbutton" iconCls="icon-edit">编辑</a></td></shiro:hasPermission>
					<shiro:hasPermission name="school-delete"><td><a href="" onclick="doDelete();return false" class="easyui-linkbutton" iconCls="icon-cancel">禁用</a></td></shiro:hasPermission>
					<shiro:hasPermission name="school-restore"><td><a href="" onclick="doRestore();return false" class="easyui-linkbutton" iconCls="icon-save">恢复</a></td>
					<th>省份:</th>
					<td><input name="provinceid" id="pid" class="easyui-combobox" style="width:80px;"></td>
					<th>市:</th>
					<td><input name="cityid" id="cid" class="easyui-combobox" style="width:80px;"></td>
					<th>区/县:</th>
					<td><input name="townid" id="tid" class="easyui-combobox" style="width:80px;"></td>
					<th>名称:</th>
					<td><input id="name" name="name" placeholder="请输入名称" style = "width:110px;"/></td>
					<th>手机号:</th>
					<td><input id="phone" name="phone" placeholder="请输入手机号" style = "width:100px;"/></td>
					<th>创建时间:</th>
					<td><input id="createdatetimeStart" name="createdatetimeStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style = "width:120px;"/>至<input id="createdatetimeEnd" name="createdatetimeEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" style = "width:120px;"/>
					<td><a id="search" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="doSearch()">查询</a></td></shiro:hasPermission>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleansearch()">清空查询条件</a></td>
				</tr>
			</table>
    	</div>
    </form>
</body>
</html>	