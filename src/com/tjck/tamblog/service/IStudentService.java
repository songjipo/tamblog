package com.tjck.tamblog.service;

import java.util.List;
import java.util.Map;

import com.tjck.tamblog.entity.Tstudent;
import com.tjck.tamblog.utils.PageBean;

public interface IStudentService {
	
	public List<Tstudent> dataGrid(Map<String, String> map, PageBean pb);
	public Long count(Map<String, String> map, PageBean pb);
	public void add(Tstudent student);
	public Tstudent get(Long id);
	public void edit(Tstudent student);
	public void deleteBatch(String ids);
	public void restoreBatch(String ids);
}
