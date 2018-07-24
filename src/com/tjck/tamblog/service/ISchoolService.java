package com.tjck.tamblog.service;

import java.util.List;
import java.util.Map;

import com.tjck.tamblog.entity.Tschool;
import com.tjck.tamblog.utils.PageBean;

public interface ISchoolService {

	public List<Tschool> dataGrid(Map<String, String> map, PageBean pb);
	public Long count(Map<String, String> map, PageBean pb);
	
	public void add(Tschool tschool);
	public Tschool get(Long id);
	public void edit(Tschool school);
	public void deleteBatch(String ids);
	public void restoreBatch(String ids);
	
	public List<Tschool> getSchoolsById(Map<String, String> map);
	public Tschool getbyid(Long id);

}
