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
import com.tjck.tamblog.service.IAttendService;
import com.tjck.tamblog.utils.Grid;
import com.tjck.tamblog.utils.PageBean;
import com.tjck.tamblog.utils.TBUtils;

@Controller
@RequestMapping("/sattend")
public class SAttendController extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(SAttendController.class);
	@Autowired
	private IAttendService attendService;
	
	@RequestMapping("/list")
	public String list() {
		return "attend/stulist";
	}
	
	@RequestMapping("/listAttend")
	@ResponseBody
	public String listAttend(HttpServletRequest request, HttpServletResponse response,Map<String, String> map,PageBean pb) throws Exception {
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
		grid.setRows(attendService.dataGrid(map, pb));
		grid.setTotal(attendService.count(map, pb));
		writeJson(grid, response);
		return null;
	}
	
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request) {
		String url = request.getParameter("photo");
		request.setAttribute("url", url);
		return "attend/stuedit";
	}
	
}
