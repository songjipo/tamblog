package com.tjck.tamblog.utils;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tjck.tamblog.entity.Tuser;

public class TBUtils {
	
	public static final String ADMIN = "admin";
	public static final String ZERO = "0";
	
	// 获取session对象
	public static HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}

	// 获取登录用户对象
	public static Tuser getLoginUser() {
		return (Tuser) getSession().getAttribute("loginUser");
	}
	
	//获取登陆用户的信息
	public static SessionInfo getSessionInfo() {
		return (SessionInfo) getSession().getAttribute("sessionInfo");
	}
}
