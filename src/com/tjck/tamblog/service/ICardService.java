package com.tjck.tamblog.service;

import java.util.List;
import java.util.Map;

import com.tjck.tamblog.entity.Tcard;
import com.tjck.tamblog.utils.PageBean;

public interface ICardService {
	
	public List<Tcard> dataGrid(Map<String, String> map, PageBean pb);
	public Long count(Map<String, String> map, PageBean pb);
	public void add(Tcard card);
	/*public Tstudent get(Long id);
	public void edit(Tstudent student);*/
	public void deleteBatch(String ids);
	public void restoreBatch(String ids);
}
