package com.tjck.tamblog.controller;

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
import com.tjck.tamblog.entity.Tschool;
import com.tjck.tamblog.entity.Tteacher;
import com.tjck.tamblog.service.ISchoolService;
import com.tjck.tamblog.service.ITeacherService;
import com.tjck.tamblog.utils.Grid;
import com.tjck.tamblog.utils.Json;
import com.tjck.tamblog.utils.PageBean;
import com.tjck.tamblog.utils.TBUtils;

@Controller
@RequestMapping("/teacher")
public class TeacherController extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(TeacherController.class);
	@Autowired
	private ITeacherService teacherService;
	@Autowired
	private ISchoolService schoolService;
	
	@RequestMapping("/list")
	public String list() {
		return "teacher/teacherlist";
	}
	
	@RequestMapping("/listTeacher")
	@ResponseBody
	public String listTeacher(HttpServletRequest request, HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
		String queryname = request.getParameter("sname");
		String queryphone = request.getParameter("sphone");
		String querycardno = request.getParameter("scardno");
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
		if (!StringUtils.isEmpty(querycardno)) {
			map.put("cardno", querycardno);
		}
		if (!StringUtils.isEmpty(starttime)) {
			map.put("starttime", starttime);
		}
		if (!StringUtils.isEmpty(endtime)) {
			map.put("endtime", endtime);
		}
		Grid grid = new Grid();
		grid.setRows(teacherService.dataGrid(map, pb));
		grid.setTotal(teacherService.count(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "teacher/teacheradd";
	}
	
	@RequestMapping("/addTeacher")
	public String addTeacher(Tteacher teacher,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		teacher.setSchoolid(request.getParameter("schoolid"));
		teacher.setBirthday(request.getParameter("birthday"));
		try {
			teacherService.add(teacher);
			json.setSuccess(true);
			json.setMsg("添加教师成功");
		} catch (Exception e) {
			json.setMsg("教师卡号或者手机号重复，请修改");
		}
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Tteacher teacher = teacherService.get(id);
		Tschool school = schoolService.get(Long.valueOf(teacher.getSchoolid()));
		request.setAttribute("schoolname", school.getName());
		request.setAttribute("teacher", teacher);
		return "teacher/teacheredit";
	}
	
	@RequestMapping(value = "/editTeacher",produces="application/json; charset=utf-8")
	public String editTeacher(Tteacher teacher,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		try {
			teacherService.edit(teacher);
			json.setSuccess(true);
			json.setMsg("修改教师信息成功");
		} catch (Exception e) {
			json.setMsg("教师手机号或者卡号已经存在，请修改");
		}
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/deleteBatch")
	public String deleteBatch(@RequestParam(value = "ids")String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		teacherService.deleteBatch(ids);;
		json.setSuccess(true);
		json.setMsg("教师已禁用");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/restoreBatch")
	public String restoreBatch(@RequestParam(value = "ids")String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		teacherService.restoreBatch(ids);;
		json.setSuccess(true);
		json.setMsg("教师已恢复");
		writeJson(json, response);
		return null;
	}
	
}
