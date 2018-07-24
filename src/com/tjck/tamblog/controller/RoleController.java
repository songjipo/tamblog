package com.tjck.tamblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tjck.tamblog.controller.base.BaseController;
import com.tjck.tamblog.entity.Trole;
import com.tjck.tamblog.service.IRoleService;
import com.tjck.tamblog.utils.Grid;
import com.tjck.tamblog.utils.Json;
import com.tjck.tamblog.utils.PageBean;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(RoleController.class);
	@Autowired
	private IRoleService roleService;

	@RequestMapping("/list")
	public String list() {
		return "admin/rolelist";
	}
	
	@RequestMapping("/listRole")
	@ResponseBody
	public String listRole(HttpServletRequest request, HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
		Grid grid = new Grid();
		grid.setRows(roleService.dataGrid(map, pb));
		grid.setTotal(roleService.count(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "admin/roleadd";
	}
	
	@RequestMapping("/addRole")
	public String addRole(Trole role,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		roleService.add(role);
		json.setSuccess(true);
		json.setMsg("添加角色成功");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		Trole tr = roleService.get(id);
		request.setAttribute("role", tr);
		return "admin/roleedit";
	}
	
	@RequestMapping(value = "/editRole",produces="application/json; charset=utf-8")
	public String editRole(Trole role,HttpServletRequest request,  
            HttpServletResponse response) {
		String funcids = request.getParameter("funcids");
		if (funcids.length() > 0) {
			role.setFunctionIds(funcids);
		}
		Json json = new Json();
		roleService.edit(role);
		json.setSuccess(true);
		json.setMsg("修改角色成功");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/deleteBatch")
	public String deleteBatch(@RequestParam(value = "ids")String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		roleService.deleteBatch(ids);;
		json.setSuccess(true);
		json.setMsg("角色已删除");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/getall")
	public void getallfunction(HttpServletResponse response){
		List<Trole> list = roleService.getAll();
		java2Json(list, new String[]{"functions","users"}, response);
	}
	
	@RequestMapping("/getbyid")
	public void getrolebyid(HttpServletRequest request, HttpServletResponse response){
		String userid = request.getParameter("userId");
		List<Trole> list = roleService.getrolesById(userid);
		java2Json(list, new String[]{"functions","users"}, response);
	}
	
}
