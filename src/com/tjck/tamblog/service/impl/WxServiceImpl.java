package com.tjck.tamblog.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjck.tamblog.dao.base.IBaseDao;
import com.tjck.tamblog.entity.Twxtoken;
import com.tjck.tamblog.service.IWxService;

@Service
public class WxServiceImpl implements IWxService{
	
	@Autowired
	private IBaseDao<Twxtoken> wxtokenDao;

	@Override
	@Transactional
	public void edit(Twxtoken wxtoken) {
		wxtokenDao.update(wxtoken);
	}

	@Override
	public Twxtoken get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Twxtoken wxtoken = wxtokenDao.get("from Twxtoken t where t.id = :id", params);
		return wxtoken;
	}

	@Override
	public Twxtoken getbyhql(String hql) {
		return wxtokenDao.find(hql).get(0);
	}
	
}
