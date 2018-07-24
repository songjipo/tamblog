package com.tjck.tamblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tjck.tamblog.controller.base.BaseController;
import com.tjck.tamblog.dao.base.IBaseDao;
import com.tjck.tamblog.entity.Tfunction;
import com.tjck.tamblog.service.IFunctionService;
import com.tjck.tamblog.utils.Grid;
import com.tjck.tamblog.utils.Json;
import com.tjck.tamblog.utils.PageBean;

@Controller
@RequestMapping("/function")
public class FunctionController extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(FunctionController.class);
	@Autowired
	private IFunctionService functionService;
	@Autowired
	private IBaseDao<Tfunction> functionDao;
	
	@RequestMapping("/list")
	public String list() {
		return "admin/functionlist";
	}
	
	@RequestMapping("/listFunction")
	@ResponseBody
	public String listFunction(HttpServletRequest request, HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
		String queryname = request.getParameter("sname");
		if (!StringUtils.isEmpty(queryname)) {
			map.put("name", queryname);
		}
		Grid grid = new Grid();
		grid.setRows(functionService.dataGrid(map, pb));
		grid.setTotal(functionService.count(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "admin/functionadd";
	}
	
	@RequestMapping("/addFunction")
	public String addFunction(Tfunction function,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		String parentid = request.getParameter("parentid");
		if (parentid.length() > 0) {
			function.setParentFunction(functionDao.get(Tfunction.class, parentid));
		}
		functionService.add(function);
		json.setSuccess(true);
		json.setMsg("添加权限成功");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		Tfunction tf = functionService.get(id);
		request.setAttribute("func", tf);
		return "admin/functionedit";
	}
	
	@RequestMapping(value = "/editFunction",produces="application/json; charset=utf-8")
	public String editFunction(Tfunction function,HttpServletRequest request,  
            HttpServletResponse response) {
		String pid = request.getParameter("pid");
		if (pid != null && pid.length() > 0) {
			function.setParentFunction(functionDao.get(Tfunction.class, pid));
		}else {
			function.setParentFunction(null);
		}
		Json json = new Json();
		functionService.edit(function);
		json.setSuccess(true);
		json.setMsg("修改权限信息成功");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/deleteBatch")
	public String deleteBatch(@RequestParam(value = "ids")String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		functionService.deleteBatch(ids);;
		json.setSuccess(true);
		json.setMsg("权限已禁用");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/restoreBatch")
	public String restoreBatch(@RequestParam(value = "ids")String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		functionService.restoreBatch(ids);;
		json.setSuccess(true);
		json.setMsg("权限已恢复");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/getfunctions")
	public List<Tfunction> getfunctions(HttpServletRequest request, HttpServletResponse response){
		List<Tfunction> list = functionService.getFunctions();
		java2Json(list, new String[]{"function","roles"}, response);
		//writeJson(list, response);
		return null;
	}
	
	@RequestMapping("/getall")
	public void getallfunction(HttpServletResponse response){
		List<Tfunction> list = functionService.getAll();
		java2Json(list, new String[]{"parentFunction","roles"}, response);
	}
	
	@RequestMapping("/getbyid")
	public void getfunctionbyid(HttpServletRequest request, HttpServletResponse response){
		String roleid = request.getParameter("roleId");
		List<Tfunction> list = functionService.getFunctionsById(roleid);
		java2Json(list, new String[]{"parentFunction","roles"}, response);
	}
	
	/**
	 * 根据当前登录人查询对应的菜单数据，返回json
	 */
	@RequestMapping("/getmenu")
	public void findMenu(HttpServletResponse response,HttpServletRequest request){
		List<Tfunction> list = functionService.findMenu(request);
		java2Json(list, new String[]{"parentFunction","roles","children"},response);
	}
	
}
