<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<jsp:include page="top.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>幼儿园信息管理系统</title>
<script type="text/javascript" src="${ctx }/js/ztree/jquery.ztree.core.js"></script>

<script type="text/javascript">
	// 初始化ztree菜单
	$(function() {
        var obj = $("#treeMenu");
		var zSetting = {
            data:{
                simpleData:{
                    enable:true,
                    idKey:"id",
                    pIdKey:"pId"
                },
            key:{
                   url:'_url'
               }
            },
            callback: {
              onClick: zTreeOnClick
          }
        };

       $.ajax({
           type:"post",
           url:"${ctx}/function/getmenu",
           async:false,
           dataType:"json",
           success:function(mes){
        	   $.fn.zTree.init(obj, zSetting, mes);
        	   var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
         	   treeObj.expandAll(true);
           }
       });
       
       
		
	});

		function zTreeOnClick(event, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeMenu");
    		if(treeNode.isParent) {
    			zTree.expandNode(treeNode);
                return false;
       		}else{
				var url = "${ctx}/"+treeNode.url;
             	var name = treeNode.name;
             	easyui_tab(name,url);
            	return true;
         	}
		};

		//生成新的选项卡
		function easyui_tab(text,url) {
			var content = "<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="+ url + "></iframe>";
    		if($("#tabs").tabs('exists', text)) {
           		 $("#tabs").tabs('select', text);
           		var tab = $('#tabs').tabs('getSelected'); 
				$('#tabs').tabs('update', {
				    tab: tab,
				    options: {
				        title: text,
				        content: content
				    }
				});
    		}else{
          		
          		$("#tabs").tabs('add', {
             		title : text,
             		closable : true,
             		content : content,
          		});
  			}
    	}
		
		window.setTimeout(function(){
			$.messager.show({
				title:"消息提示",
				msg:'欢迎登录，' + '${loginUser.username }' + '！ <a href="javascript:void" onclick="top.showAbout();">联系管理员</a>',
				timeout:5000
			});
		},3000);
		
		
		function btnCancel(){
			$('#editPwdWindow').window('close');
		}
		
		function btnEp(){
			//表单验证
			var v = $("#editPasswordForm").form("validate");
			if(v){
				//表单校验通过，手动校验两次输入是否一致
				var v1 = $("#txtNewPass").val();
				var v2 = $("#txtRePass").val();
				if(v1 == v2){
					//两次输入一致，发送ajax请求
					$.post("${ctx}/user/editPwd",{"password":v1},function(result){
						result = $.parseJSON(result);
						if(result.success){
							$.messager.alert('提示信息', result.msg, 'info');
							//修改成功，关闭修改密码窗口
							$("#editPwdWindow").window("close");
						}else{
							//修改密码失败，弹出提示
							$.messager.alert("提示信息","密码修改失败！","error");
						}
					});
				}else{
					//两次输入不一致，弹出错误提示
					$.messager.alert("提示信息","两次密码输入不一致！","warning");
				}
			}
		}
		
		/* $("#btnCancel").click(function(){
			$('#editPwdWindow').window('close');
		});
		
		$("#btnEp").click(function(){
			var v = $("#editPasswordForm").form("validate");
			if(v){
				var v1 = $("#txtNewPass").val();
				var v2 = $("#txtRePass").val();
				if(v1 == v2){
					$.post("${ctx}/user/editPwd",{"password":v1},function(result){
						result = $.parseJSON(result);
						if(result.success){
							$.messager.alert('提示信息', result.msg, 'info');
							$("#editPwdWindow").window("close");
						}else{
							$.messager.alert("提示信息","密码修改失败！","error");
						}
					});
				}else{
					$.messager.alert("提示信息","两次密码输入不一致！","warning");
				}
			}
		}); */
		
		
	

	/*******顶部特效 *******/
	/**
	 * 更换EasyUI主题的方法
	 * @param themeName
	 * 主题名称
	 */
	changeTheme = function(themeName) {
		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/'
				+ themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);
		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for ( var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			}
		}
	};
	// 退出登录
	function logoutFun() {
		$.messager
		.confirm('系统提示','您确定要退出本次登录吗?',function(isConfirm) {
			if (isConfirm) {
				location.href = '${ctx}/admin/logout';
			}
		});
	}
	// 修改密码
	function editPassword() {
		//打开修改密码窗口
		$('#editPwdWindow').window('open');
	}
	// 版权信息
	function showAbout(){
		$.messager.alert("版权所有：开心小宝马","管理员邮箱: xxxxxxxx<br><br><br>联系电话:xxxxxxxxx");
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false"
		style="height:60px;padding:10px;background:url('${ctx}/images/header_bg.png') repeat-x left;">
		<div id="sessionInfoDiv" style="position: absolute;right: 5px;top:10px;">
		<c:if test="${sessionInfo.schoolname != ''}">[<strong>${sessionInfo.schoolname }</strong>]，</c:if>
		<c:if test="${sessionInfo.schoolname == ''}"></c:if>
		<c:if test="${sessionInfo.name != ''}">[<strong>${sessionInfo.name }</strong>]，</c:if>
		<c:if test="${sessionInfo.name == ''}">[<strong>超级管理员</strong>]，</c:if>
		欢迎你！
			<%-- [<strong>${loginUser.username }</strong>]，欢迎你！ --%>
		</div>
		<div style="position: absolute; right: 5px; bottom: 10px; ">
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a>
		</div>
		<div id="layout_north_pfMenu" style="width: 120px; display: none;">
			<div onclick="changeTheme('default');">default</div>
			<div onclick="changeTheme('gray');">gray</div>
			<div onclick="changeTheme('black');">black</div>
			<div onclick="changeTheme('bootstrap');">bootstrap</div>
			<div onclick="changeTheme('metro');">metro</div>
		</div>
		<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
			<div onclick="editPassword();">修改密码</div>
			<div onclick="showAbout();">联系管理员</div>
			<div class="menu-sep"></div>
			<div onclick="logoutFun();">退出系统</div>
		</div>
	</div>
	<div data-options="region:'west',split:true,title:'菜单导航'" style="width:200px">
		<div class="easyui-accordion" fit="true" border="false">
			<div title="基本功能" data-options="iconCls:'icon-mini-add'" style="background-color:#6293BB;color:#fff;overflow:auto">
				<ul id="treeMenu" class="ztree" style="background-color:#6293BB;color:#fff;"></ul>
			</div>
			<!-- <div title="系统管理" data-options="iconCls:'icon-mini-add'" style="overflow:auto">  
				<ul id="adminMenu" class="ztree"></ul>
			</div> -->
		</div>
	</div>
	<div data-options="region:'center'">
		<div id="tabs" fit="true" class="easyui-tabs" border="false">
			<div title="首页" style="padding:20px;display:none;">
			<div align="center"><img src="${ctx}/images/welcome.jpg"/></div> 
             </div>
		</div>
	</div>
	
	<!--修改密码窗口-->
    <div id="editPwdWindow" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" modal="true" closed="true" resizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 160px; padding: 5px;
        background: #fafafa">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
               <form id="editPasswordForm">
	                <table cellpadding=3>
	                    <tr>
	                        <td>新密码：</td>
	                        <td><input  required="true" data-options="validType:'length[4,6]'" id="txtNewPass" type="Password" class="txt01 easyui-validatebox" /></td>
	                    </tr>
	                    <tr>
	                        <td>确认密码：</td>
	                        <td><input required="true" data-options="validType:'length[4,6]'" id="txtRePass" type="Password" class="txt01 easyui-validatebox" /></td>
	                    </tr>
	                </table>
               </form>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnEp" onclick="btnEp();" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >确定</a> 
                <a id="btnCancel" onclick="btnCancel();" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>
</body>
</html>