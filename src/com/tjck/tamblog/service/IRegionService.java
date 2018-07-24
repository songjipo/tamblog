package com.tjck.tamblog.service;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface IRegionService {
	
	//查询省  
	public List getProvince();  
	//查询市  
	public List getCity(String code);  
	//查询县区  
	public List getTown(String code); 
	
}
