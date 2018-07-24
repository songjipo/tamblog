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
import com.tjck.tamblog.entity.Trelation;
import com.tjck.tamblog.service.IRelationService;
import com.tjck.tamblog.service.ISchoolService;
import com.tjck.tamblog.utils.Grid;
import com.tjck.tamblog.utils.Json;
import com.tjck.tamblog.utils.PageBean;
import com.tjck.tamblog.utils.TBUtils;

@Controller
@RequestMapping("/relation")
public class RelationController extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(RelationController.class);
	@Autowired
	private IRelationService relationService;
	@Autowired
	private ISchoolService schoolService;
	
	@RequestMapping("/list")
	public String list() {
		return "relation/relationlist";
	}
	
	@RequestMapping("/listRelation")
	@ResponseBody
	public String listRelation(HttpServletRequest request, HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
		String queryname = request.getParameter("sname");
		if(!TBUtils.getLoginUser().getUsername().trim().equals("admin")) {
			if(TBUtils.getSessionInfo() != null) {
				map.put("schoolid", TBUtils.getSessionInfo().getSchoolid());
			}
		}
		if (!StringUtils.isEmpty(queryname)) {
			map.put("name", queryname);
		}
		Grid grid = new Grid();
		grid.setRows(relationService.dataGrid(map, pb));
		grid.setTotal(relationService.count(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	
	@RequestMapping("/addPage")
	public String addPage() {
		return "relation/relationadd";
	}
	
	@RequestMapping("/addRelation")
	public String addRelation(Trelation relation,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		relation.setSchoolid(request.getParameter("schoolid"));
		relationService.add(relation);
		json.setSuccess(true);
		json.setMsg("添加关系成功");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Long id) {
		Trelation relation = relationService.get(id);
		relation.setSchoolname(schoolService.get(Long.valueOf(relation.getSchoolid())).getName());
		request.setAttribute("relation", relation);
		return "relation/relationedit";
	}
	
	@RequestMapping(value = "/editRelation",produces="application/json; charset=utf-8")
	public String editRelation(Trelation relation,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		relation.setSchoolid(request.getParameter("schoolid"));
		relationService.edit(relation);
		json.setSuccess(true);
		json.setMsg("修改关系名称成功");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/getrelations")
	public List<Trelation> getrelations(Map<String, String> map, HttpServletResponse response){
		if(!TBUtils.getLoginUser().getUsername().trim().equals("admin")) {
			if(TBUtils.getSessionInfo() != null) {
				map.put("schoolid", TBUtils.getSessionInfo().getSchoolid());
			}
		}
		List<Trelation> list = relationService.getRelations(map);
		writeJson(list, response);
		return null;
	}
	
	@RequestMapping("/getrelationsbyid")
	public List<Trelation> getrelationsbyid(Map<String, String> map, HttpServletRequest request, HttpServletResponse response){
		String schoolid= request.getParameter("code");
		map.put("schoolid", schoolid);
		List<Trelation> list = relationService.getRelations(map);
		writeJson(list, response);
		return null;
	}
	
}
