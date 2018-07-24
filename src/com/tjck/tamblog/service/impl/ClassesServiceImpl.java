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
import com.tjck.tamblog.entity.Tclasses;
import com.tjck.tamblog.entity.Tstudent;
import com.tjck.tamblog.service.IClassesService;
import com.tjck.tamblog.utils.DateUtils;
import com.tjck.tamblog.utils.PageBean;

@Service
@Transactional
public class ClassesServiceImpl implements IClassesService{
	
	@Autowired
	private IBaseDao<Tclasses> classesDao;
	
	@Override
	public void add(Tclasses classes) {
		classes.setStatus("1");
		classes.setCreatetime(DateUtils.getCurrentTime());
		classesDao.save(classes);
	}
	
	@Override
	public Tclasses get(Long id) {
		Tclasses tclasses = classesDao.get("from Tclasses t where t.id = " + id);
		return tclasses;
	}
	
	@Override
	public void edit(Tclasses tclasses) {
		classesDao.update(tclasses);
	}
	
	@Override
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tclasses tclasses = classesDao.get(Tclasses.class, Long.valueOf(id));
				Set<Tstudent> sets = tclasses.getTstudents();
				Iterator<Tstudent> it = sets.iterator();
				while(it.hasNext()) {
					Tstudent tstudent = it.next();
					tstudent.setStatus("0");
				}
				tclasses.setStatus("0");//更改为禁用状态 0
				classesDao.update(tclasses);
			}
			
		}
	}
	
	@Override
	public void restoreBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tclasses tclasses = classesDao.get(Tclasses.class, Long.valueOf(id));
				Set<Tstudent> sets = tclasses.getTstudents();
				Iterator<Tstudent> it = sets.iterator();
				while(it.hasNext()) {
					Tstudent tstudent = it.next();
					tstudent.setStatus("1");
				}
				tclasses.setStatus("1");//更改为正常状态 1
				classesDao.update(tclasses);
			}
			
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Tclasses> dataGrid(Map<String, String> map, PageBean pb) {
		List<Tclasses> sList = new ArrayList<Tclasses>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t.*,s.name schoolname from classes t left join school s on t.schoolid = s.id";
		
		if (map != null && map.size() > 0) {
			hql += " where 1=1";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "%%" + map.get("name") + "%%");
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
				hql += " and t.schoolid = :schoolid";
				params.put("schoolid", map.get("schoolid"));
			}
		}
		
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by t." + pb.getSort() + " " + pb.getOrder();
		}
		
		//sList = classesDao.find(hql, params, pb.getPage(), pb.getRows());
		List<Map> listmap = classesDao.findBySql(hql, params, pb.getPage(), pb.getRows());
		for(Map s:listmap) {
			Tclasses ts = new Tclasses();
			ts.setSchoolname(String.valueOf(s.get("schoolname")));
			ts.setName(String.valueOf(s.get("name")));
			ts.setCreatetime(String.valueOf(s.get("createtime")));
			ts.setId(Long.valueOf(s.get("id").toString()));
			ts.setStatus(String.valueOf(s.get("status")));
			sList.add(ts);
		}
		
		//sList = classesDao.findBySql(sql, params, pb.getPage(), pb.getRows());
		return sList;
	}
	
	@Override
	public Long count(Map<String, String> map, PageBean pb) {
		Map<String, Object> params = new HashMap<String, Object>();
		//String hql = "from Tuser t ";
		String hql = "select t.* from classes t left join school s on t.schoolid = s.id";
		
		if (map != null && map.size() > 0) {
			hql += " where 1=1";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "%%" + map.get("name").trim() + "%%");
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
				hql += " and t.schoolid = :schoolid";
				params.put("schoolid", map.get("schoolid"));
			}
		}
		
		return Long.valueOf(String.valueOf(classesDao.countBySql("select count(*) from (" + hql + ") as t", params)));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Tclasses> getClasses(Map<String, String> map) {
		List<Tclasses> tclasses = new ArrayList<Tclasses>();
		String sql = "select * from classes c where c.status=1";
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				sql += " and c.schoolid = " + map.get("schoolid");
			}
		}
		List<Map> list = classesDao.findBySql(sql);
		for(Map m:list) {
			Tclasses tc = new Tclasses();
			tc.setId(Long.valueOf(m.get("id").toString()));
			tc.setName(String.valueOf(m.get("name")));
			tclasses.add(tc);
		}
		return tclasses;
	}

	@Override
	public List<Tclasses> getClassesById(String schoolid) {
		String sql = "from Tclasses t where t.schoolid = " + schoolid;
		List<Tclasses> list = classesDao.find(sql);
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Tclasses> getAll(Map<String, String> map) {
		List<Tclasses> tclasses = new ArrayList<Tclasses>();
		String sql = "select c.*,s.name schoolname from classes c left join school s on c.schoolid = s.id where c.status = 1 and s.status = 1";
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				sql += " and c.schoolid = " + map.get("schoolid");
			}
		}
		List<Map> list = classesDao.findBySql(sql);
		for(Map m:list) {
			Tclasses tc = new Tclasses();
			tc.setId(Long.valueOf(m.get("id").toString()));
			tc.setSchoolid(String.valueOf(m.get("schoolid")));
			tc.setSchoolname(String.valueOf(m.get("schoolname")));
			tc.setName(String.valueOf(m.get("name")));
			tclasses.add(tc);
		}
		return tclasses;
	}

	/**
	 * ----中间表使用
	 */
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Tclasses> getClassesBySchoolid(String schoolid) {
		List<Tclasses> tclasses = new ArrayList<Tclasses>();
		String sql = "select * from classes t where t.status = 1 and t.schoolid = " + schoolid;;
		List<Map> list = classesDao.findBySql(sql);
		for(Map m:list) {
			Tclasses tc = new Tclasses();
			tc.setId(Long.valueOf(m.get("id").toString()));
			tc.setName(String.valueOf(m.get("name")));
			tclasses.add(tc);
		}
		return tclasses;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Tclasses> getAllClasses() {
		List<Tclasses> tclasses = new ArrayList<Tclasses>();
		String sql = "select c.*,s.name schoolname from classes c left join school s on c.schoolid = s.id where c.status = 1 and s.status = 1";
		List<Map> list = classesDao.findBySql(sql);
		for(Map m:list) {
			Tclasses tc = new Tclasses();
			tc.setId(Long.valueOf(m.get("id").toString()));
			//tc.setSchoolid(String.valueOf(m.get("schoolid")));
			//tc.setSchoolname(String.valueOf(m.get("schoolname")));
			tc.setName(String.valueOf(m.get("schoolname")) + "------" + String.valueOf(m.get("name")));
			tclasses.add(tc);
		}
		return tclasses;
	}
	
	@Override
	public List<Tclasses> getclassessById(String teacherId) {
		String hql = "select distinct c from Tclasses c left outer join c.teacherList t where t.id = " + teacherId;
		List<Tclasses> list = classesDao.find(hql);
		return list;
	}

}
