package com.tjck.tamblog.service;

import java.util.List;
import java.util.Map;

import com.tjck.tamblog.entity.Tattend;
import com.tjck.tamblog.utils.PageBean;

public interface IAttendService {
	
	public List<Tattend> dataGrid(Map<String, String> map, PageBean pb);
	public Long count(Map<String, String> map, PageBean pb);
	//public Tattend get(String id);
}
