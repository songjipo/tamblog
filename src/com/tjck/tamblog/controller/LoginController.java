package com.tjck.tamblog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/*import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;*/
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.tjck.tamblog.controller.base.BaseController;
import com.tjck.tamblog.entity.Tschool;
import com.tjck.tamblog.entity.Tteacher;
import com.tjck.tamblog.entity.Tuser;
import com.tjck.tamblog.service.ISchoolService;
import com.tjck.tamblog.service.ITeacherService;
import com.tjck.tamblog.utils.Json;
import com.tjck.tamblog.utils.MD5Utils;
import com.tjck.tamblog.utils.SessionInfo;

@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController{
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(LoginController.class);
	@Autowired
	private ITeacherService teacherService;
	@Autowired
	private ISchoolService schoolService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		return "index";
	}
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request) {
		return "main";
	}
	
	/**
	 * 用户登录,使用shiro框架提供的方式进行认证操作
	 */
	@RequestMapping(value = "/login1", produces = "application/json; charset=utf-8")
	public @ResponseBody String login1(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		String result = "{\"success\":\"0\",\"msg\":\"用户名或者密码错误\"}";
		Subject subject = SecurityUtils.getSubject();//获得当前用户对象,状态为“未认证”
		AuthenticationToken token = new UsernamePasswordToken(username,MD5Utils.md5(password));//创建用户名密码令牌对象
		try{
			subject.login(token);
		}catch(Exception e){
			e.printStackTrace();
			return result;
		}
		Tuser tuser = (Tuser) subject.getPrincipal();
		//Tuser tuser = userService.login(username, password);
		SessionInfo sessionInfo = new SessionInfo();
		if (tuser != null && tuser.getStatus().equals("1")) {
			request.getSession().setAttribute("loginUser", tuser);
			sessionInfo.setUserid(tuser.getId());
			sessionInfo.setUsername(tuser.getUsername());
			Tteacher teacher =  teacherService.getbyuserid(tuser.getId());
			if (teacher != null) {
				sessionInfo.setTeacherid(String.valueOf(teacher.getId()));
				sessionInfo.setName(teacher.getName());
				Tschool school = schoolService.getbyid(Long.valueOf(teacher.getSchoolid()));
				if (school != null) {
					sessionInfo.setSchoolid(String.valueOf(school.getId()));
					sessionInfo.setSchoolname(school.getName());
				}else {
					sessionInfo.setSchoolname("");
				}
			}else {
				sessionInfo.setSchoolname("");
				sessionInfo.setName("");
			}
			request.getSession().setAttribute("sessionInfo", sessionInfo);
			result = "{\"success\":\"1\",\"msg\":\"登陆成功\"}";
			return result;
		}else if(tuser != null && tuser.getStatus().equals("0")){
			result = "{\"success\":\"0\",\"msg\":\"该用户已被禁用，请联系管理员\"}";
			return result;
		}else {
			return result;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/login")
	public void login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		Json json = new Json();
		Subject subject = SecurityUtils.getSubject();//获得当前用户对象,状态为“未认证”
		AuthenticationToken token = new UsernamePasswordToken(username,MD5Utils.md5(password));//创建用户名密码令牌对象
		try{
			subject.login(token);
		}catch(Exception e){
			e.printStackTrace();
			json.setMsg("用户名或者密码错误");
			writeJson(json, response);
		}
		Tuser tuser = (Tuser) subject.getPrincipal();
		SessionInfo sessionInfo = new SessionInfo();
		if (tuser != null && tuser.getStatus().equals("1")) {
			request.getSession().setAttribute("loginUser", tuser);
			sessionInfo.setUserid(tuser.getId());
			sessionInfo.setUsername(tuser.getUsername());
			Tteacher teacher =  teacherService.getbyuserid(tuser.getId());
			if (teacher != null) {
				sessionInfo.setTeacherid(String.valueOf(teacher.getId()));
				sessionInfo.setName(teacher.getName());
				Tschool school = schoolService.getbyid(Long.valueOf(teacher.getSchoolid()));
				if (school != null) {
					sessionInfo.setSchoolid(String.valueOf(school.getId()));
					sessionInfo.setSchoolname(school.getName());
				}else {
					sessionInfo.setSchoolname("");
				}
			}else {
				sessionInfo.setSchoolname("");
				sessionInfo.setName("");
			}
			request.getSession().setAttribute("sessionInfo", sessionInfo);
			json.setSuccess(true);
			json.setMsg("登陆成功");
			writeJson(json, response);
		}else if(tuser != null && tuser.getStatus().equals("0")){
			json.setMsg("该用户已被禁用，请联系管理员");
			writeJson(json, response);
		}else {
			json.setMsg("用户名或者密码错误");
			writeJson(json, response);
		}
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request) {
		SecurityUtils.getSubject().logout();
		request.getSession().removeAttribute("loginUser");
		request.getSession().removeAttribute("sessionInfo");
		((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().invalidate();
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
	}
	
}
