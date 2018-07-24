package com.tjck.tamblog.service;

import java.util.List;
import java.util.Map;

import com.tjck.tamblog.entity.Tparent;
import com.tjck.tamblog.utils.PageBean;

public interface IParentService {
	
	public List<Tparent> dataGrid(Map<String, String> map, PageBean pb);
	public Long count(Map<String, String> map, PageBean pb);
	public void add(Tparent parent);
	//public Tparent get(Long id);
	/*public void edit(Tparent parent);
	public void deleteBatch(String ids);
	public void restoreBatch(String ids);*/
}
