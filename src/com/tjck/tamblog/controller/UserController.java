package com.tjck.tamblog.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tjck.tamblog.controller.base.BaseController;
import com.tjck.tamblog.entity.Tuser;
import com.tjck.tamblog.service.IUserService;
import com.tjck.tamblog.utils.Grid;
import com.tjck.tamblog.utils.Json;
import com.tjck.tamblog.utils.PageBean;
import com.tjck.tamblog.utils.TBUtils;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/listT")
	public String listT() {
		return "user/tlist";
	}
	
	@RequestMapping("/listTUser")
	@ResponseBody
	public String listTUser(HttpServletRequest request, HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
		String queryname = request.getParameter("sname");
		String queryphone = request.getParameter("sphone");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		
		if(!TBUtils.getLoginUser().getUsername().trim().equals("admin")) {
			if(TBUtils.getSessionInfo() != null) {
				map.put("schoolid", TBUtils.getSessionInfo().getSchoolid());
			}
		}
		if (!StringUtils.isEmpty(queryname)) {
			map.put("name", queryname);
		}
		if (!StringUtils.isEmpty(queryphone)) {
			map.put("phone", queryphone);
		}
		if (!StringUtils.isEmpty(starttime)) {
			map.put("starttime", starttime);
		}
		if (!StringUtils.isEmpty(endtime)) {
			map.put("endtime", endtime);
		}
		Grid grid = new Grid();
		grid.setRows(userService.dataGrid_t(map, pb));
		grid.setTotal(userService.count_t(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	@RequestMapping("/listP")
	public String listP() {
		return "user/plist";
	}
	
	@RequestMapping("/listPUser")
	@ResponseBody
	public String listPUser(HttpServletRequest request, HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
		String queryname = request.getParameter("sname");
		String queryphone = request.getParameter("sphone");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		
		if(!TBUtils.getLoginUser().getUsername().trim().equals("admin")) {
			if(TBUtils.getSessionInfo() != null) {
				map.put("schoolid", TBUtils.getSessionInfo().getSchoolid());
			}
		}
		if (!StringUtils.isEmpty(queryname)) {
			map.put("name", queryname);
		}
		if (!StringUtils.isEmpty(queryphone)) {
			map.put("phone", queryphone);
		}
		if (!StringUtils.isEmpty(starttime)) {
			map.put("starttime", starttime);
		}
		if (!StringUtils.isEmpty(endtime)) {
			map.put("endtime", endtime);
		}
		Grid grid = new Grid();
		grid.setRows(userService.dataGrid_p(map, pb));
		grid.setTotal(userService.count_p(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	@RequestMapping("/listWX")
	public String listWX() {
		return "user/wxlist";
	}
	
	@RequestMapping("/listWXUser")
	@ResponseBody
	public String listWXUser(HttpServletRequest request, HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
		String queryname = request.getParameter("sname");
		String queryphone = request.getParameter("sphone");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		
		if (!StringUtils.isEmpty(queryname)) {
			map.put("wxname", queryname);
		}
		if (!StringUtils.isEmpty(queryphone)) {
			map.put("phone", queryphone);
		}
		if (!StringUtils.isEmpty(starttime)) {
			map.put("starttime", starttime);
		}
		if (!StringUtils.isEmpty(endtime)) {
			map.put("endtime", endtime);
		}
		Grid grid = new Grid();
		grid.setRows(userService.dataGrid_wx(map, pb));
		grid.setTotal(userService.count_wx(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Tuser tuser = userService.get(id);
		request.setAttribute("tuser", tuser);
		return "user/tedit";
	}
	
	@RequestMapping(value = "/editUser",produces="application/json; charset=utf-8")
	public String editUser(Tuser tuser,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		userService.edit(tuser);
		json.setSuccess(true);
		json.setMsg("修改用户信息成功");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/deleteBatch")
	public String deleteBatch(@RequestParam(value = "ids")String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		userService.deleteBatch(ids);;
		json.setSuccess(true);
		json.setMsg("人员已禁用");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/restoreBatch")
	public String restoreBatch(@RequestParam(value = "ids")String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		userService.restoreBatch(ids);;
		json.setSuccess(true);
		json.setMsg("人员已恢复");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping(value = "/editPwd",produces="application/json; charset=utf-8")
	public String editPwd(Tuser user,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		String userid = TBUtils.getLoginUser().getId();
		userService.editPwd(user, userid);
		json.setSuccess(true);
		json.setMsg("密码修改成功");
		writeJson(json, response);
		return null;
	}

}
