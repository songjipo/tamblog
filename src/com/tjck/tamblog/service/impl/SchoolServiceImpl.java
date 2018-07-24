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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tjck.tamblog.dao.base.IBaseDao;
import com.tjck.tamblog.entity.Region;
import com.tjck.tamblog.entity.Tclasses;
import com.tjck.tamblog.entity.Tschool;
import com.tjck.tamblog.service.ISchoolService;
import com.tjck.tamblog.utils.DateUtils;
import com.tjck.tamblog.utils.PageBean;

@Service
public class SchoolServiceImpl implements ISchoolService {
	
	@Autowired
	private IBaseDao<Tschool> schoolDao;
	@Autowired
	private IBaseDao<Region> regionDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Tschool> dataGrid(Map<String, String> map, PageBean pb) {
		List<Tschool> tsList = new ArrayList<Tschool>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Tschool t ";
		
		if (map != null && map.size() > 0) {
			hql += "where 1=1";
			if (!StringUtils.isEmpty(map.get("pid"))) {
				hql += " and t.provinceid = :provinceid";
				params.put("provinceid", map.get("pid"));
			}
			if (!StringUtils.isEmpty(map.get("cid"))) {
				hql += " and t.cityid = :cityid";
				params.put("cityid", map.get("cid"));
			}
			if (!StringUtils.isEmpty(map.get("tid"))) {
				hql += " and t.townid = :townid";
				params.put("townid", map.get("tid"));
			}
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "%%" + map.get("name") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("phone"))) {
				hql += " and t.phone like :phone";
				params.put("phone", "%%" + map.get("phone") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("starttime"))) {
				hql += " and t.createtime >= :createdatetimeStart";
				params.put("createdatetimeStart", map.get("starttime"));
			}
			if (!StringUtils.isEmpty(map.get("endtime"))) {
				hql += " and t.createtime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", map.get("endtime"));
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += " and t.id = :id";
				params.put("id", Long.valueOf(map.get("schoolid")));
			}
		}
		
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by t." + pb.getSort() + " " + pb.getOrder();
		}
		
		//tsList = schoolDao.find(hql + whereHql(map, params) + orderHql(pb), params, pb.getPage(), pb.getRows());
		
		tsList = schoolDao.find(hql, params, pb.getPage(), pb.getRows());
		for(Tschool ts:tsList) {
			String phql = "select * from region r where r.codeid = " + ts.getProvinceid();
			Map mp = regionDao.findBySql(phql).get(0); 
			ts.setProvincename(String.valueOf(mp.get("cityName")));
			String chql = "select * from region r where r.codeid = " + ts.getCityid();
			Map  mc = regionDao.findBySql(chql).get(0);
			ts.setCityname(String.valueOf(mc.get("cityName")));
			String thql = "select * from region r where r.codeid = " + ts.getTownid();
			Map  mt = regionDao.findBySql(thql).get(0);
			ts.setTownname(String.valueOf(mt.get("cityName")));
		}
		return tsList;
	}

	@Override
	public Long count(Map<String, String> map, PageBean pb) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Tschool t ";
		
		if (map != null && map.size() > 0) {
			hql += "where 1=1";
			if (!StringUtils.isEmpty(map.get("pid"))) {
				hql += " and t.provinceid = :provinceid";
				params.put("provinceid", map.get("pid"));
			}
			if (!StringUtils.isEmpty(map.get("cid"))) {
				hql += " and t.cityid = :cityid";
				params.put("cityid", map.get("cid"));
			}
			if (!StringUtils.isEmpty(map.get("tid"))) {
				hql += " and t.townid = :townid";
				params.put("townid", map.get("tid"));
			}
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "%%" + map.get("name") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("phone"))) {
				hql += " and t.phone like :phone";
				params.put("phone", "%%" + map.get("phone") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("starttime"))) {
				hql += " and t.createtime >= :createdatetimeStart";
				params.put("createdatetimeStart", map.get("starttime"));
			}
			if (!StringUtils.isEmpty(map.get("endtime"))) {
				hql += " and t.createtime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", map.get("endtime"));
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += " and t.id = :id";
				params.put("id", Long.valueOf(map.get("schoolid")));
			}
		}
		
		//return schoolDao.count("select count(*) " + hql + whereHql(tschool, params), params);
		return schoolDao.count("select count(*) " + hql, params);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	public void add(Tschool tschool) {
		tschool.setStatus("1");
		tschool.setCreatetime(DateUtils.getCurrentTime());
		schoolDao.save(tschool);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Tschool get(Long id) {
		Tschool school = schoolDao.get("from Tschool t where t.id = " + id);
		String phql = "select * from region r where r.codeid = " + school.getProvinceid();
		Map mp = regionDao.findBySql(phql).get(0); 
		school.setProvincename(String.valueOf(mp.get("cityName")));
		String chql = "select * from region r where r.codeid = " + school.getCityid();
		Map  mc = regionDao.findBySql(chql).get(0);
		school.setCityname(String.valueOf(mc.get("cityName")));
		String thql = "select * from region r where r.codeid = " + school.getTownid();
		Map  mt = regionDao.findBySql(thql).get(0);
		school.setTownname(String.valueOf(mt.get("cityName")));
		return school;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	public void edit(Tschool school) {
		schoolDao.saveOrUpdate(school);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tschool tschool = schoolDao.get(Tschool.class, Long.valueOf(id));
				Set<Tclasses> sets = tschool.getClasses();
				Iterator<Tclasses> it = sets.iterator();
				while(it.hasNext()) {
					Tclasses tclasses = it.next();
					tclasses.setStatus("0");
				}
				tschool.setStatus("0");//更改为禁用状态 0
				schoolDao.update(tschool);
			}
			
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	public void restoreBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tschool tschool = schoolDao.get(Tschool.class, Long.valueOf(id));
				Set<Tclasses> sets = tschool.getClasses();
				Iterator<Tclasses> it = sets.iterator();
				while(it.hasNext()) {
					Tclasses tclasses = it.next();
					tclasses.setStatus("1");
				}
				tschool.setStatus("1");//更改为正常状态 1
				schoolDao.update(tschool);
			}
			
		}
	}
	
	@Override
	public List<Tschool> getSchoolsById(Map<String, String> map) {
		String hql = "from Tschool t where t.status = 1";
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += " and t.id = " + map.get("schoolid");
			}
		}
		List<Tschool> list = schoolDao.find(hql);
		return list;
	}

	@Override
	public Tschool getbyid(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tschool school = schoolDao.get("from Tschool t where t.id = :id", params);
		return school;
	}
	
	/*private String whereHql(Tschool tschool, Map<String, Object> params) {
		String hql = "";
		if (tschool.getId() != null) {
			hql += "where 1=1 ";
			if (!StringUtils.isEmpty(tschool.getProvinceid())) {
				hql += " and t.provinceid = :provinceid";
				params.put("provinceid", tschool.getProvinceid());
			}
			if (!StringUtils.isEmpty(tschool.getCityid())) {
				hql += " and t.cityid = :cityid";
				params.put("cityid", tschool.getCityid());
			}
			if (!StringUtils.isEmpty(tschool.getTownid())) {
				hql += " and t.townid = :townid";
				params.put("townid", tschool.getTownid());
			}
			if (!StringUtils.isEmpty(tschool.getName())) {
				hql += " and t.name like :name";
				params.put("name", "%%" + tschool.getName() + "%%");
			}
			if (!StringUtils.isEmpty(tschool.getPhone())) {
				hql += " and t.phone like :phone";
				params.put("phone", "%%" + tschool.getPhone() + "%%");
			}
			if (!StringUtils.isEmpty(tschool.getCreatedatetimeStart())) {
				hql += " and t.createtime >= :createdatetimeStart";
				params.put("createdatetimeStart", tschool.getCreatedatetimeStart());
			}
			if (!StringUtils.isEmpty(tschool.getCreatedatetimeEnd())) {
				hql += " and t.createtime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", tschool.getCreatedatetimeEnd());
			}
			if (!TBUtils.getLoginUser().getUsername().trim().equals("admin")) {
				if (TBUtils.getSessionInfo() != null) {
					hql += " and t.id = :id";
					params.put("id", Long.valueOf(TBUtils.getSessionInfo().getSchoolid()));
				}
			}
		}
		return hql;
	}

	private String orderHql(PageBean pb) {
		String orderString = "";
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			orderString = " order by t." + pb.getSort() + " " + pb.getOrder();
		}
		return orderString;
	}*/
	
}
