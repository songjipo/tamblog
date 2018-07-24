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
import org.springframework.web.bind.annotation.ResponseBody;

import com.tjck.tamblog.controller.base.BaseController;
import com.tjck.tamblog.entity.Tschool;
import com.tjck.tamblog.service.ISchoolService;
import com.tjck.tamblog.utils.Grid;
import com.tjck.tamblog.utils.Json;
import com.tjck.tamblog.utils.PageBean;
import com.tjck.tamblog.utils.TBUtils;

/**
 * @author wangkaixin
 */

@Controller
@RequestMapping("/school")
public class SchoolController extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(SchoolController.class);
	@Autowired
	private ISchoolService schoolService;
	
	@RequestMapping("/list")
	public String list() {
		return "school/schoollist";
	}
	
	@RequestMapping("/listSchool")
	@ResponseBody
	public String listSchool(HttpServletRequest request,HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
		
		String queryname = request.getParameter("name");
		String queryphone = request.getParameter("phone");
		String starttime = request.getParameter("createdatetimeStart");
		String endtime = request.getParameter("createdatetimeEnd");
		String pid = request.getParameter("provinceid");
		String cid = request.getParameter("cityid");
		String tid = request.getParameter("townid");
		if(!(TBUtils.ADMIN).equals(TBUtils.getLoginUser().getUsername().trim())) {
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
		if (!StringUtils.isEmpty(pid) && !(TBUtils.ZERO).equals(pid)) {
			map.put("pid", pid);
		}
		if (!StringUtils.isEmpty(cid) && !(TBUtils.ZERO).equals(cid)) {
			map.put("cid", cid);
		}
		if (!StringUtils.isEmpty(tid) && !(TBUtils.ZERO).equals(tid)) {
			map.put("tid", tid);
		}
		Grid grid = new Grid();
		grid.setRows(schoolService.dataGrid(map, pb));
		grid.setTotal(schoolService.count(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "school/schooladd";
	}
	
	@RequestMapping("/addSchool")
	public String addSchool(Tschool tschool, HttpServletResponse response) {
		Json json = new Json();
		try {
			schoolService.add(tschool);
			json.setSuccess(true);
			json.setMsg("添加幼儿园成功");
		} catch (Exception e) {
			json.setMsg("幼儿园名称已经存在，请修改");
		}
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Tschool school = schoolService.get(id);
		request.setAttribute("sch", school);
		return "school/schooledit";
	}
	
	@RequestMapping(value = "/editSchool",produces="application/json; charset=utf-8")
	public String editSchool(Tschool school,HttpServletResponse response) {
		Json json = new Json();
		try {
			schoolService.edit(school);
			json.setSuccess(true);
			json.setMsg("修改幼儿园信息成功");
		} catch (Exception e) {
			json.setMsg("幼儿园名称已经存在");
		}
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/deleteBatch")
	public String deleteBatch(String ids,HttpServletResponse response) {
		Json json = new Json();
		try {
			schoolService.deleteBatch(ids);
			json.setSuccess(true);
			json.setMsg("幼儿园已禁用");
		} catch (Exception e) {
			json.setMsg(e.getMessage());
		}
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/restoreBatch")
	public String restoreBatch(String ids,HttpServletResponse response) {
		Json json = new Json();
		try {
			schoolService.restoreBatch(ids);
			json.setSuccess(true);
			json.setMsg("幼儿园已恢复");
		} catch (Exception e) {
			json.setMsg(e.getMessage());
		}
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/getSchoolById")
	public List<Tschool> getSchoolById(Map<String, String> map,HttpServletResponse response){
		if(!(TBUtils.ADMIN).equals(TBUtils.getLoginUser().getUsername().trim())) {
			if(TBUtils.getSessionInfo() != null) {
				map.put("schoolid", TBUtils.getSessionInfo().getSchoolid());
			}
		}
		List<Tschool> list = schoolService.getSchoolsById(map);
		writeJson(list, response);
		return null;
	}
}
