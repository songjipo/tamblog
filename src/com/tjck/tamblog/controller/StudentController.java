package com.tjck.tamblog.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tjck.tamblog.controller.base.BaseController;
import com.tjck.tamblog.dao.base.IBaseDao;
import com.tjck.tamblog.entity.T_student_parent;
import com.tjck.tamblog.entity.Tcard;
import com.tjck.tamblog.entity.Tparent;
import com.tjck.tamblog.entity.Tstudent;
import com.tjck.tamblog.service.ICardService;
import com.tjck.tamblog.service.IParentService;
import com.tjck.tamblog.service.IStuParentService;
import com.tjck.tamblog.service.IStudentService;
import com.tjck.tamblog.utils.Grid;
import com.tjck.tamblog.utils.Json;
import com.tjck.tamblog.utils.PageBean;
import com.tjck.tamblog.utils.TBUtils;

@Controller
@RequestMapping("/student")
public class StudentController extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(StudentController.class);
	@Autowired
	private IStudentService studentService;
	@Autowired
	private IParentService parentService;
	@Autowired
	private IStuParentService tspService;
	@Autowired
	private ICardService cardService;
	@Autowired
	private IBaseDao<Tparent> parentDao;
	@Autowired
	private IBaseDao<Tcard> cardDao;
	
	@RequestMapping("/list")
	public String list() {
		return "student/studentlist";
	}
	
	@RequestMapping("/listStudent")
	@ResponseBody
	public String listStudent(HttpServletRequest request, HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
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
		grid.setRows(studentService.dataGrid(map, pb));
		grid.setTotal(studentService.count(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "student/studentadd";
	}
	
	@RequestMapping("/addStudent")
	public String addStudent(Tstudent student,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		student.setSchoolid(request.getParameter("schoolid"));
		student.setClassesid(request.getParameter("classesid"));
		student.setBirthday(request.getParameter("birthday"));
		try {
			studentService.add(student);
			json.setSuccess(true);
			json.setMsg("添加学生成功");
		} catch (Exception e) {
			json.setMsg("已经存在该学生，请修改");
		}
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
		try {
			studentService.edit(tstudent);
			json.setSuccess(true);
			json.setMsg("修改学生信息成功");
		} catch (Exception e) {
			json.setMsg("姓名重复，请修改");
		}
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
	}
	
	@RequestMapping("/addParentPage")
	public String addParentPage(HttpServletRequest request, Long id) {
		Tstudent tstudent = studentService.get(id);
		request.setAttribute("stu", tstudent);
		return "student/studentparentadd";
	}
	
	@RequestMapping("/addParent")
	@Transactional
	public String addParent(Tparent parent,HttpServletRequest request,  
            HttpServletResponse response) throws Exception{
		Json json = new Json();
		String stuid = request.getParameter("stuid");
		String relationid = request.getParameter("relationid");
		String cardno = request.getParameter("cardno");
		Tstudent student = studentService.get(Long.valueOf(stuid));
		Tcard card = new Tcard();
		card.setCardno(cardno);
		
		T_student_parent tsp = new T_student_parent();
		
		Tparent p = parentDao.get("from Tparent t where t.phone = '" + parent.getPhone() + "'");
		if (p != null) {
			tsp.setParent(p);
		}else {
			parentService.add(parent);
			tsp.setParent(parent);
		}
		Tcard c = cardDao.get("from Tcard t where t.cardno = " + cardno);
		if (c != null) {
			c.setTsp(tsp);
			tsp.setCard(c);
		}else {
			card.setTsp(tsp);
			tsp.setCard(card);
			cardService.add(card);
		}
		
		//tsp.setParent(parent);
		tsp.setStudent(student);
		tsp.setRelationid(relationid);
		//card.setTsp(tsp);
		//tsp.setCard(card);
		//parentService.add(parent);
		//cardService.add(card);
		try {
			tspService.add(tsp);
			json.setSuccess(true);
			json.setMsg("绑定家长和卡号信息成功");
		}catch (RuntimeException e) {
			json.setMsg("该家长或该卡号已被绑定，请查询重新操作");
		}
		writeJson(json, response);
		return null;
	}
}
