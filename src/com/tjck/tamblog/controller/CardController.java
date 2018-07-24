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
import com.tjck.tamblog.service.ICardService;
import com.tjck.tamblog.utils.Grid;
import com.tjck.tamblog.utils.Json;
import com.tjck.tamblog.utils.PageBean;
import com.tjck.tamblog.utils.TBUtils;

@Controller
@RequestMapping("/card")
public class CardController extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(CardController.class);
	@Autowired
	private ICardService cardService;
	
	@RequestMapping("/list")
	public String list() {
		return "card/cardlist";
	}
	
	@RequestMapping("/listCard")
	@ResponseBody
	public String listCard(HttpServletRequest request, HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
		String querycardno = request.getParameter("scardno");
		if (!StringUtils.isEmpty(querycardno)) {
			map.put("cardno", querycardno);
		}
		if(!TBUtils.getLoginUser().getUsername().trim().equals("admin")) {
			if(TBUtils.getSessionInfo() != null) {
				map.put("schoolid", TBUtils.getSessionInfo().getSchoolid());
			}
		}
		Grid grid = new Grid();
		grid.setRows(cardService.dataGrid(map, pb));
		grid.setTotal(cardService.count(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	/*@RequestMapping("/editPage")
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
	}*/
	
	@RequestMapping("/deleteBatch")
	public String deleteBatch(@RequestParam(value = "ids")String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		cardService.deleteBatch(ids);;
		json.setSuccess(true);
		json.setMsg("卡已禁用");
		writeJson(json, response);
		return null;
	}
	
	@RequestMapping("/restoreBatch")
	public String restoreBatch(@RequestParam(value = "ids")String ids,HttpServletRequest request,  
            HttpServletResponse response) {
		Json json = new Json();
		cardService.restoreBatch(ids);;
		json.setSuccess(true);
		json.setMsg("卡已恢复");
		writeJson(json, response);
		return null;
	}
}
