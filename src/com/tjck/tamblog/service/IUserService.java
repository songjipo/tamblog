package com.tjck.tamblog.service;

import java.util.List;
import java.util.Map;

import com.tjck.tamblog.entity.Tuser;
import com.tjck.tamblog.utils.PageBean;

public interface IUserService {
	public Tuser login(String username,String password); //用户登陆
	public Tuser getByUsername(String username);
	
	public List<Tuser> dataGrid_t(Map<String, String> map, PageBean pb);
	public Long count_t(Map<String, String> map, PageBean pb);
	public List<Tuser> dataGrid_p(Map<String, String> map, PageBean pb);
	public Long count_p(Map<String, String> map, PageBean pb);
	public List<Tuser> dataGrid_wx(Map<String, String> map, PageBean pb);
	public Long count_wx(Map<String, String> map, PageBean pb);
	public void add(Tuser tuser);
	public Tuser get(Long id);
	public void edit(Tuser tuser);
	public void deleteBatch(String ids);
	public void restoreBatch(String ids);
	public void editPwd(Tuser user, String userid);
}
