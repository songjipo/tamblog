package com.tjck.tamblog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjck.tamblog.dao.base.IBaseDao;
import com.tjck.tamblog.entity.Tfunction;
import com.tjck.tamblog.entity.Tuser;
import com.tjck.tamblog.service.IFunctionService;
import com.tjck.tamblog.utils.PageBean;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService{
	
	@Autowired
	private IBaseDao<Tfunction> functionDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<Tfunction> dataGrid(Map<String, String> map, PageBean pb) {
		List<Tfunction> sList = new ArrayList<Tfunction>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t.* from function t";
		
		if (map != null && map.size() > 0) {
			hql += " where 1=1";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "%%" + map.get("name") + "%%");
			}
		}
		
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by t." + pb.getSort() + " " + pb.getOrder();
		}
		
		List<Map> listmap = functionDao.findBySql(hql, params, pb.getPage(), pb.getRows());
		for(Map s:listmap) {
			Tfunction tf = new Tfunction();
			tf.setId(s.get("id").toString());
			tf.setName(String.valueOf(s.get("name")));
			tf.setCode(String.valueOf(s.get("code")));
			tf.setDescription(String.valueOf(s.get("description")));
			tf.setUrl(String.valueOf(s.get("url")));
			tf.setGeneratemenu(String.valueOf(s.get("generatemenu")));
			tf.setZindex(Integer.valueOf(String.valueOf(s.get("zindex"))));
			if (s.get("pid") != null) {
				String pid = String.valueOf(s.get("pid"));
				String hl = "from Tfunction t where t.id = " + pid;
				Tfunction tfunction = functionDao.get(hl);
				tf.setParentFunction(tfunction);
				tf.setPname(tfunction.getText());
			}
			tf.setStatus(String.valueOf(s.get("status")));
			sList.add(tf);
		}
		return sList;
	}

	@Override
	public Long count(Map<String, String> map, PageBean pb) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t.* from function t ";
		
		if (map != null && map.size() > 0) {
			hql += "where 1=1";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "%%" + map.get("name").trim() + "%%");
			}
		}
		
		return Long.valueOf(String.valueOf(functionDao.countBySql("select count(*) from (" + hql + ") as t", params)));
	}

	@Override
	public void add(Tfunction function) {
		function.setStatus("1");
		functionDao.save(function);
	}

	@Override
	public Tfunction get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tfunction tf = functionDao.get("from Tfunction t where t.id = :id", params);
		return tf;
	}

	@Override
	public void edit(Tfunction function) {
		Tfunction tf = functionDao.get(Tfunction.class, function.getId());
		tf.setName(function.getName());
		tf.setCode(function.getCode());
		tf.setDescription(function.getDescription());
		tf.setUrl(function.getUrl());
		tf.setGeneratemenu(function.getGeneratemenu());
		tf.setZindex(function.getZindex());
		tf.setParentFunction(function.getParentFunction());
		functionDao.update(tf);
	}

	@Override
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tfunction function = functionDao.get(Tfunction.class, id);
				Set<Tfunction> sets = function.getChildren();
				if (sets != null) {
					Iterator<Tfunction> it = sets.iterator();
					while(it.hasNext()) {
						Tfunction tf = it.next();
						tf.setStatus("0");
					}
				}
				function.setStatus("0");//更改为禁用状态 0
				functionDao.update(function);
			}
			
		}
	}
	
	@Override
	public void restoreBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tfunction function = functionDao.get(Tfunction.class, id);
				Set<Tfunction> sets = function.getChildren();
				if (sets != null) {
					Iterator<Tfunction> it = sets.iterator();
					while(it.hasNext()) {
						Tfunction tf = it.next();
						tf.setStatus("1");
					}
				}
				function.setStatus("1");//更改为正常状态 1
				functionDao.update(function);
			}
			
		}
	}
	
	@Override
	public List<Tfunction> getFunctions() {
		String hql = "from Tfunction t";
		List<Tfunction> list = functionDao.find(hql);
		return list;
	}
	
	@Override
	public List<Tfunction> getAll() {
		String hql = "from Tfunction t where t.parentFunction is null order by t.zindex asc";
		List<Tfunction> list = functionDao.find(hql);
		return list;
	}
	
	@Override
	public List<Tfunction> getFunctionsById(String roleId) {
		String hql = "select distinct f from Tfunction f left outer join f.roles r where r.id = " + roleId;
		List<Tfunction> list = functionDao.find(hql);
		return list;
	}

	@Override
	public List<Tfunction> findFunctionListByUserId(String userId) {
		String hql = "select distinct f from Tfunction f left outer join f.roles"
				+ " r left outer join r.users u where u.id = " + userId;
		List<Tfunction> list = functionDao.find(hql);
		return list;
	}

	@Override
	public List<Tfunction> findMenu(HttpServletRequest request) {
		String hql = "";
		List<Tfunction> list = null;
		//Tuser user = TBUtils.getLoginUser();
		Tuser user = (Tuser) request.getSession().getAttribute("loginUser");
		if(user.getUsername().equals("admin")){
			//如果是超级管理员内置用户，查询所有菜单
			hql = "from Tfunction f where f.generatemenu = '1' order by f.zindex asc";
			list = functionDao.find(hql);
		}else{
			//其他用户，根据用户id查询菜单
			hql = "select distinct f from Tfunction f left outer join f.roles"
					+ " r left outer join r.users u where u.id = " + user.getId() + " and f.generatemenu = '1' "
					+ "order by f.zindex asc";
			list = functionDao.find(hql);
		}
		return list;
	}
	
}
