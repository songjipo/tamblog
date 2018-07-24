package com.tjck.tamblog.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tjck.tamblog.controller.base.BaseController;
import com.tjck.tamblog.entity.Region;
import com.tjck.tamblog.service.IRegionService;

@Controller
@RequestMapping("/region")
@SuppressWarnings("rawtypes")
public class RegionController extends BaseController {
	
	@Autowired
	private IRegionService regionService;
	
	@RequestMapping("/getprovince")
	public List<Region> getProvince(HttpServletResponse response){
		List list = regionService.getProvince();
		writeJson(list, response);
		return null;
	}
	
	@RequestMapping("/getcity")
	public List<Region> getcity(@RequestParam(value = "code")String code,HttpServletResponse response){
		List list = regionService.getCity(code);
		writeJson(list, response);
		return null;
	}
	
	@RequestMapping("/gettown")
	public List<Region> gettown(@RequestParam(value = "code")String code,HttpServletResponse response){
		List list = regionService.getTown(code);
		writeJson(list, response);
		return null;
	}
	
	
	
}
