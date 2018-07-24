package com.tjck.tamblog.service;

import java.util.List;
import java.util.Map;

import com.tjck.tamblog.entity.Trole;
import com.tjck.tamblog.utils.PageBean;

public interface IRoleService {
	
	public List<Trole> dataGrid(Map<String, String> map, PageBean pb);
	public Long count(Map<String, String> map, PageBean pb);
	public void add(Trole role);
	public Trole get(String id);
	public void edit(Trole role);
	public void deleteBatch(String ids);
	public List<Trole> getAll();
	public List<Trole> getrolesById(String userId);
}
