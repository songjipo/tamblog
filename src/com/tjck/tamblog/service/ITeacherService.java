package com.tjck.tamblog.service;

import java.util.List;
import java.util.Map;

import com.tjck.tamblog.entity.Tteacher;
import com.tjck.tamblog.utils.PageBean;

public interface ITeacherService {
	
	public List<Tteacher> dataGrid(Map<String, String> map, PageBean pb);
	public Long count(Map<String, String> map, PageBean pb);
	public void add(Tteacher teacher);
	public Tteacher get(Long id);
	public void edit(Tteacher teacher);
	public void deleteBatch(String ids);
	public void restoreBatch(String ids);
	
	public Tteacher getbyuserid(String id);
}
