package com.tjck.tamblog.service;

import java.util.List;
import java.util.Map;

import com.tjck.tamblog.entity.Tclasses;
import com.tjck.tamblog.utils.PageBean;

public interface IClassesService {
	
	public List<Tclasses> dataGrid(Map<String, String> map, PageBean pb);
	public Long count(Map<String, String> map, PageBean pb);
	public void add(Tclasses classes);
	public Tclasses get(Long id);
	public void edit(Tclasses tclasses);
	public void deleteBatch(String ids);
	public void restoreBatch(String ids);
	public List<Tclasses> getClasses(Map<String, String> map);
	public List<Tclasses> getClassesById(String schoolid);
	public List<Tclasses> getAll(Map<String, String> map);
	//中间值用
	public List<Tclasses> getClassesBySchoolid(String schoolid);
	public List<Tclasses> getAllClasses();
	//public List<Tclasses> getClById(String schoolid);
	
	//编辑教师使用
	public List<Tclasses> getclassessById(String teacherId);
}
