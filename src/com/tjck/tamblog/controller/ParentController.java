package com.tjck.tamblog.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tjck.tamblog.controller.base.BaseController;
import com.tjck.tamblog.service.IParentService;
import com.tjck.tamblog.utils.Grid;
import com.tjck.tamblog.utils.PageBean;
import com.tjck.tamblog.utils.TBUtils;

@Controller
@RequestMapping("/parent")
public class ParentController extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ParentController.class);
	@Autowired
	private IParentService parentService;
	
	@RequestMapping("/list")
	public String list() {
		return "parent/parentlist";
	}
	
	@RequestMapping("/listParent")
	@ResponseBody
	public String listParent(HttpServletRequest request, HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
		String queryname = request.getParameter("sname");
		String queryphone = request.getParameter("sphone");
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
		Grid grid = new Grid();
		grid.setRows(parentService.dataGrid(map, pb));
		grid.setTotal(parentService.count(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	
	/*@RequestMapping("/addPage")
	public String addPage() {
		return "student/studentadd";
	}
	
	@RequestMapping("/addStudent")
	public String addStudent(Tstudent student,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		//TODO 从session获取
		student.setSchoolid("1");
		student.setClassesid(request.getParameter("classesid"));
		student.setBirthday(request.getParameter("birthday"));
		studentService.add(student);
		json.setSuccess(true);
		json.setMsg("添加学生成功");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Tstudent tstudent = studentService.get(id);
		request.setAttribute("stu", tstudent);
		return "student/studentedit";
	}
	
	@RequestMapping(value = "/editStudent",produces="application/json; charset=utf-8")
	public String editStudent(Tstudent tstudent,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		studentService.edit(tstudent);
		json.setSuccess(true);
		json.setMsg("修改学生信息成功");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/deleteBatch")
	public String deleteBatch(@RequestParam(value = "ids")String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		studentService.deleteBatch(ids);;
		json.setSuccess(true);
		json.setMsg("学生已禁用");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/restoreBatch")
	public String restoreBatch(@RequestParam(value = "ids")String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		studentService.restoreBatch(ids);;
		json.setSuccess(true);
		json.setMsg("学生已恢复");
		writeJson(json, response);
		return null;
	}*/
	
}
