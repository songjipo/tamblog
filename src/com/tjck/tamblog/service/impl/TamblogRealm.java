package com.tjck.tamblog.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.tjck.tamblog.entity.Tfunction;
import com.tjck.tamblog.entity.Tuser;
import com.tjck.tamblog.service.IFunctionService;
import com.tjck.tamblog.service.IUserService;

public class TamblogRealm extends AuthorizingRealm{
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IFunctionService functionService;
	
	//授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获取当前登录用户对象
		Tuser user = (Tuser) SecurityUtils.getSubject().getPrincipal();
		// 根据当前登录用户查询数据库，获取实际对应的权限
		List<Tfunction> list = null;
		if(user.getUsername().trim().equals("admin")) {
			//超级管理员内置用户，查询所有权限数据
			list = functionService.getFunctions();
		}else {
			list = functionService.findFunctionListByUserId(user.getId());
		}
		for(Tfunction f:list) {
			info.addStringPermission(f.getCode());
		}
		return info;
	}
	
	//认证方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("执行自定义realm中的认证方法");
		UsernamePasswordToken passwordToken = (UsernamePasswordToken)token;
		//获得页面输入的用户名
		String username = passwordToken.getUsername();
		//根据用户名查询数据库中的密码
		Tuser tuser = userService.getByUsername(username);
		if(tuser == null){
			//页面输入的用户名不存在
			//return null;
			//throw new UnknownAccountException("没有这个用户");
			throw new AuthenticationException();
		}
		//简单认证信息对象
		AuthenticationInfo info = new SimpleAuthenticationInfo(tuser, tuser.getPassword(), this.getName());
		//框架负责比对数据库中的密码和页面输入的密码是否一致
		return info;
	}

}
