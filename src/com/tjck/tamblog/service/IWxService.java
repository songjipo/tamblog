package com.tjck.tamblog.service;

import com.tjck.tamblog.entity.Twxtoken;

public interface IWxService {
	public Twxtoken get(String id);
	public Twxtoken getbyhql(String hql);
	public void edit(Twxtoken wxtoken);
}
