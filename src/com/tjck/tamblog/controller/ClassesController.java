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
import com.tjck.tamblog.entity.Tclasses;
import com.tjck.tamblog.service.IClassesService;
import com.tjck.tamblog.utils.Grid;
import com.tjck.tamblog.utils.Json;
import com.tjck.tamblog.utils.PageBean;
import com.tjck.tamblog.utils.TBUtils;

@Controller
@RequestMapping("/classes")
public class ClassesController extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ClassesController.class);
	@Autowired
	private IClassesService classesService;
	
	@RequestMapping("/list")
	public String list() {
		return "classes/classeslist";
	}
	
	@RequestMapping("/listClasses")
	@ResponseBody
	public String listClasses(HttpServletRequest request, HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
		String queryname = request.getParameter("sname");
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
		if (!StringUtils.isEmpty(starttime)) {
			map.put("starttime", starttime);
		}
		if (!StringUtils.isEmpty(endtime)) {
			map.put("endtime", endtime);
		}
		Grid grid = new Grid();
		grid.setRows(classesService.dataGrid(map, pb));
		grid.setTotal(classesService.count(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "classes/classesadd";
	}
	
	@RequestMapping("/addClasses")
	public String addClasses(Tclasses classes,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		classes.setSchoolid(request.getParameter("schoolid"));
		try {
			classesService.add(classes);
			json.setSuccess(true);
			json.setMsg("添加班级成功");
		} catch (Exception e) {
			json.setMsg("班级名称重复，请修改");
		}
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Tclasses tclasses = classesService.get(id);
		request.setAttribute("classes", tclasses);
		return "classes/classesedit";
	}
	
	@RequestMapping(value = "/editClasses",produces="application/json; charset=utf-8")
	public String editClasses(Tclasses tclasses,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		try {
			classesService.edit(tclasses);
			json.setSuccess(true);
			json.setMsg("修改班级信息成功");
		} catch (Exception e) {
			json.setMsg("班级名称重复，请重新修改");
		}
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/deleteBatch")
	public String deleteBatch(String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		classesService.deleteBatch(ids);;
		json.setSuccess(true);
		json.setMsg("班级已禁用");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/restoreBatch")
	public String restoreBatch(String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		classesService.restoreBatch(ids);;
		json.setSuccess(true);
		json.setMsg("班级已恢复");
		writeJson(json, response);
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getclasses")
	public List<Tclasses> getclasses(Map<String, String> map, HttpServletResponse response){
		if(!TBUtils.getLoginUser().getUsername().trim().equals("admin")) {
			if(TBUtils.getSessionInfo() != null) {
				map.put("schoolid", TBUtils.getSessionInfo().getSchoolid());
			}
		}
		List list = classesService.getClasses(map);
		writeJson(list, response);
		return null;
	}
	
	@RequestMapping("/getbyid")
	public List<Tclasses> getbyid(@RequestParam(value = "code")String code,HttpServletResponse response){
		List<Tclasses> list = classesService.getClassesById(code);
		writeJson(list, response);
		return null;
	}
	
	/**
	 * 中间表插值用
	 */
	
	@RequestMapping("/getClassesBySchoolid")
	public void getClassesBySchoolid(@RequestParam(value = "schoolid")String schoolid,HttpServletResponse response){
		List<Tclasses> list = classesService.getClassesBySchoolid(schoolid);
		java2Json(list, null, response);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getall")
	public void getall(HttpServletResponse response){
		List list = null;
		if(!TBUtils.getLoginUser().getUsername().trim().equals("admin")) {
			if(TBUtils.getSessionInfo() != null) {
				list = classesService.getClassesBySchoolid(TBUtils.getSessionInfo().getSchoolid());
			}
		}else {
			list = classesService.getAllClasses();
		}
		java2Json(list, null, response);
	}
	//编辑教师
	@RequestMapping("/getbyteacherid")
	public void getbyteacherid(HttpServletRequest request, HttpServletResponse response){
		String teacherId = request.getParameter("teacherId");
		List<Tclasses> list = classesService.getclassessById(teacherId);
		java2Json(list, new String[]{"teacherList","tstudents","school"}, response);
	}
	
	/*@RequestMapping("/getall")
	public List<Tclasses> getall(Map<String, String> map, HttpServletResponse response){
		if(!TBUtils.getLoginUser().getUsername().trim().equals("admin")) {
			if(TBUtils.getSessionInfo() != null) {
				map.put("schoolid", TBUtils.getSessionInfo().getSchoolid());
			}
		}
		List<Tclasses> list = classesService.getAll(map);
		
		List<String> ids = new ArrayList<String>();
		List<TreeBean> trees = new ArrayList<TreeBean>();
		
		for(Tclasses t:list) {
			if(!ids.contains(t.getSchoolid())) {
				ids.add(t.getSchoolid());
				TreeBean tb = new TreeBean(t.getSchoolid(),"0",t.getSchoolname());
				TreeBean tb1 = new TreeBean(String.valueOf(t.getId()),t.getSchoolid(),t.getName());
				trees.add(tb);
				trees.add(tb1);
			}else {
				TreeBean tb1 = new TreeBean(String.valueOf(t.getId()),t.getSchoolid(),t.getName());
				trees.add(tb1);
			}
		}
		writeJson(trees, response);
		return null;
	}*/
	
}
