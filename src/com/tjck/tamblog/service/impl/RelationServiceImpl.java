package com.tjck.tamblog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjck.tamblog.dao.base.IBaseDao;
import com.tjck.tamblog.entity.Trelation;
import com.tjck.tamblog.service.IRelationService;
import com.tjck.tamblog.utils.PageBean;

@Service
@Transactional
public class RelationServiceImpl implements IRelationService{
	
	@Autowired
	private IBaseDao<Trelation> relationDao;
	
	@Override
	public void add(Trelation relation) {
		relationDao.save(relation);
	}
	
	@Override
	public Trelation get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Trelation relation = relationDao.get("from Trelation t where t.id = :id", params);
		return relation;
	}
	
	@Override
	public void edit(Trelation relation) {
		Trelation r = relationDao.get(Trelation.class, relation.getId());
		r.setSchoolid(relation.getSchoolid());
		r.setName(relation.getName());
		relationDao.update(r);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Trelation> dataGrid(Map<String, String> map, PageBean pb) {
		List<Trelation> sList = new ArrayList<Trelation>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t.*,s.name schoolname from relation t left join school s on t.schoolid = s.id";
		
		if (map != null && map.size() > 0) {
			hql += " where 1=1";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "%%" + map.get("name") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += " and t.schoolid = :schoolid";
				params.put("schoolid", map.get("schoolid"));
			}
		}
		
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by t." + pb.getSort() + " " + pb.getOrder();
		}
		
		List<Map> listmap = relationDao.findBySql(hql, params, pb.getPage(), pb.getRows());
		for(Map s:listmap) {
			Trelation ts = new Trelation();
			ts.setSchoolname(String.valueOf(s.get("schoolname")));
			ts.setName(String.valueOf(s.get("name")));
			ts.setId(Long.valueOf(s.get("id").toString()));
			sList.add(ts);
		}
		
		//sList = relationDao.find(hql, params, pb.getPage(), pb.getRows());
		return sList;
	}
	
	@Override
	public Long count(Map<String, String> map, PageBean pb) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t.* from relation t left join school s on t.schoolid = s.id";
		
		if (map != null && map.size() > 0) {
			hql += " where 1=1";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "%%" + map.get("name").trim() + "%%");
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += " and t.schoolid = :schoolid";
				params.put("schoolid", map.get("schoolid"));
			}
		}
		
		return Long.valueOf(String.valueOf(relationDao.countBySql("select count(*) from (" + hql + ") as t", params)));
	}

	@Override
	public List<Trelation> getRelations(Map<String, String> map) {
		String hql = "from Trelation t ";
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += "where t.schoolid = " + map.get("schoolid");
			}
		}
		List<Trelation> list = relationDao.find(hql);
		return list;
	}

}
