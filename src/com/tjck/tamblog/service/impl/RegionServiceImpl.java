package com.tjck.tamblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjck.tamblog.dao.base.IBaseDao;
import com.tjck.tamblog.entity.Region;
import com.tjck.tamblog.service.IRegionService;

@Service
@Transactional
@SuppressWarnings("rawtypes")
public class RegionServiceImpl implements IRegionService{
	
	@Autowired
	private IBaseDao<Region> regionDao;

	@Override
	public List getProvince() {
		String sql = "select * from region r where r.parentid = 0";
		List list = regionDao.findBySql(sql);
		return list;
	}

	@Override
	public List getCity(String code) {
		String sql = "select * from region r where r.parentid = " + code;
		List list = regionDao.findBySql(sql);
		return list;
	}

	@Override
	public List getTown(String code) {
		String sql = "select * from region r where r.parentid = " + code;
		List list = regionDao.findBySql(sql);
		return list;
	}

}
