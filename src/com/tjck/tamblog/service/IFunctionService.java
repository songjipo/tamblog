package com.tjck.tamblog.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tjck.tamblog.entity.Tfunction;
import com.tjck.tamblog.utils.PageBean;

public interface IFunctionService {
	
	public List<Tfunction> dataGrid(Map<String, String> map, PageBean pb);
	public Long count(Map<String, String> map, PageBean pb);
	public void add(Tfunction function);
	public Tfunction get(String id);
	public void edit(Tfunction function);
	public void deleteBatch(String ids);
	public void restoreBatch(String ids);
	public List<Tfunction> getFunctions();
	public List<Tfunction> getAll();
	public List<Tfunction> getFunctionsById(String roleId);
	// 根据用户id查询对应的权限
	public List<Tfunction> findFunctionListByUserId(String userId);
	// 查询所有菜单
	public List<Tfunction> findMenu(HttpServletRequest request);
}
