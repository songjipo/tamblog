package com.tjck.tamblog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjck.tamblog.dao.base.IBaseDao;
import com.tjck.tamblog.entity.Tfunction;
import com.tjck.tamblog.entity.Trole;
import com.tjck.tamblog.service.IRoleService;
import com.tjck.tamblog.utils.PageBean;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService{
	
	@Autowired
	private IBaseDao<Trole> roleDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<Trole> dataGrid(Map<String, String> map, PageBean pb) {
		List<Trole> sList = new ArrayList<Trole>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t.* from role t";
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by t." + pb.getSort() + " " + pb.getOrder();
		}
		List<Map> listmap = roleDao.findBySql(hql, params, pb.getPage(), pb.getRows());
		for(Map s:listmap) {
			Trole tf = new Trole();
			tf.setId(s.get("id").toString());
			tf.setName(String.valueOf(s.get("name")));
			tf.setCode(String.valueOf(s.get("code")));
			tf.setDescription(String.valueOf(s.get("description")));
			sList.add(tf);
		}
		return sList;
	}

	@Override
	public Long count(Map<String, String> map, PageBean pb) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t.* from role t ";
		return Long.valueOf(String.valueOf(roleDao.countBySql("select count(*) from (" + hql + ") as t", params)));
	}

	@Override
	public void add(Trole role) {
		roleDao.save(role);
		String functionIds = role.getFunctionIds();
		if(StringUtils.isNotBlank(functionIds)){
			String[] fIds = functionIds.split(",");
			for (String functionId : fIds) {
				Tfunction function = new Tfunction(functionId);
				role.getFunctions().add(function);
			}
		}
	}

	@Override
	public Trole get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Trole role = roleDao.get("from Trole t where t.id = :id", params);
		StringBuffer sb = new StringBuffer();
		String funcids;
		Set<Tfunction> tfList = role.getFunctions();
		if (tfList != null && tfList.size() > 0) {
			Iterator<Tfunction> it = tfList.iterator();
			while(it.hasNext()) {
				Tfunction tf = it.next();
				sb.append(tf.getId()).append(",");
			}
			if (sb.length() > 0) {
				funcids = sb.substring(0, sb.length()-1);
			}else
				funcids = "";
		}else
			funcids = "";
		role.setFunctionIds(funcids);
		return role;
	}

	@Override
	public void edit(Trole role) {
		Trole tr = roleDao.get(Trole.class, role.getId());
		tr.setName(role.getName());
		tr.setCode(role.getCode());
		tr.setDescription(role.getDescription());
		String functionIds = role.getFunctionIds();
		
		Set<Tfunction> functions = tr.getFunctions();
		if (functions != null) {
			Iterator<Tfunction> it = functions.iterator();
			while(it.hasNext()) {
				Tfunction tf = it.next();
				tf.getRoles().remove(role);
				role.getFunctions().remove(tf);
				it.remove();
			}
		}
		
		if(StringUtils.isNotBlank(functionIds)){
			String[] fIds = functionIds.split(",");
			for (String functionId : fIds) {
				Tfunction function = new Tfunction(functionId);
				tr.getFunctions().add(function);
			}
		}
		roleDao.update(tr);
	}

	@Override
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Trole role = roleDao.get(Trole.class, id);
				roleDao.delete(role);
			}
			
		}
	}
	
	@Override
	public List<Trole> getAll() {
		String hql = "from Trole t";
		List<Trole> list = roleDao.find(hql);
		return list;
	}
	
	@Override
	public List<Trole> getrolesById(String userId) {
		String hql = "select distinct f from Trole f left outer join f.users u where u.id = " + userId;
		List<Trole> list = roleDao.find(hql);
		return list;
	}
	
}
