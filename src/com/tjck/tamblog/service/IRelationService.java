package com.tjck.tamblog.service;

import java.util.List;
import java.util.Map;

import com.tjck.tamblog.entity.Trelation;
import com.tjck.tamblog.utils.PageBean;

public interface IRelationService {
	
	public List<Trelation> dataGrid(Map<String, String> map, PageBean pb);
	public Long count(Map<String, String> map, PageBean pb);
	public void add(Trelation relation);
	public Trelation get(Long id);
	public void edit(Trelation relation);
	public List<Trelation> getRelations(Map<String, String> map);
}
