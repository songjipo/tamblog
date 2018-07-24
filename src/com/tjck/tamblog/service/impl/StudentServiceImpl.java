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
import com.tjck.tamblog.entity.Tclasses;
import com.tjck.tamblog.entity.Tschool;
import com.tjck.tamblog.entity.Tstudent;
import com.tjck.tamblog.service.IStudentService;
import com.tjck.tamblog.utils.DateUtils;
import com.tjck.tamblog.utils.PageBean;

@Service
@Transactional
public class StudentServiceImpl implements IStudentService{
	
	@Autowired
	private IBaseDao<Tstudent> studentDao;
	@Autowired
	private IBaseDao<Tclasses> classesDao;
	@Autowired
	private IBaseDao<Tschool> schoolDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<Tstudent> dataGrid(Map<String, String> map, PageBean pb) {
		List<Tstudent> sList = new ArrayList<Tstudent>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t.*,c.name classesname,s.name schoolname from student t left join classes c on t.classesid = c.id left join school s on t.schoolid = s.id";
		
		if (map != null && map.size() > 0) {
			hql += " where 1=1";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "%%" + map.get("name") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("starttime"))) {
				hql += " and t.birthday >= :createdatetimeStart";
				params.put("createdatetimeStart", map.get("starttime"));
			}
			if (!StringUtils.isEmpty(map.get("endtime"))) {
				hql += " and t.birthday <= :createdatetimeEnd";
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
		
		//List<Tschool> tsList = schoolDao.find(hql + whereHql(map, params) + orderHql(pb), params, pb.getPage(), pb.getRows());
		List<Map> listmap = studentDao.findBySql(hql, params, pb.getPage(), pb.getRows());
		for(Map s:listmap) {
			Tstudent ts = new Tstudent();
			ts.setClassesname(String.valueOf(s.get("classesname")));
			ts.setSchoolname(String.valueOf(s.get("schoolname")));
			ts.setName(String.valueOf(s.get("name")));
			ts.setGender(String.valueOf(s.get("gender")));
			ts.setNation(String.valueOf(s.get("nation")));
			ts.setBirthday(String.valueOf(s.get("birthday")));
			ts.setAddress(String.valueOf(s.get("address")));
			ts.setPhoto(String.valueOf(s.get("photo")));
			ts.setCreatetime(String.valueOf(s.get("createtime")));
			ts.setId(Long.valueOf(s.get("id").toString()));
			ts.setStatus(String.valueOf(s.get("status")));
			sList.add(ts);
		}
		return sList;
	}

	@Override
	public Long count(Map<String, String> map, PageBean pb) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t.*,c.name classesname,s.name schoolname from student t left join classes c on t.classesid = c.id left join school s on t.schoolid = s.id";
		
		if (map != null && map.size() > 0) {
			hql += " where 1=1";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "%%" + map.get("name").trim() + "%%");
			}
			if (!StringUtils.isEmpty(map.get("starttime"))) {
				hql += " and t.birthday >= :createdatetimeStart";
				params.put("createdatetimeStart", map.get("starttime"));
			}
			if (!StringUtils.isEmpty(map.get("endtime"))) {
				hql += " and t.birthday <= :createdatetimeEnd";
				params.put("createdatetimeEnd", map.get("endtime"));
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += " and t.schoolid = :schoolid";
				params.put("schoolid", map.get("schoolid"));
			}
		}
		
		//return schoolDao.count("select count(*) " + hql + whereHql(map, params), params);
		return Long.valueOf(String.valueOf(studentDao.countBySql("select count(*) from (" + hql + ") as t", params)));
	}

	@Override
	public void add(Tstudent student) {
		student.setStatus("1");
		student.setCreatetime(DateUtils.getCurrentTimeyyyyMMdd());
		studentDao.save(student);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Tstudent get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tstudent student = studentDao.get("from Tstudent t where t.id = :id", params);
		String sql = "select r.id,r.name from classes r where r.id = " + student.getClassesid();
		Map mp = classesDao.findBySql(sql).get(0); 
		student.setClassesname(String.valueOf(mp.get("name")));
		String sql1 = "select s.id,s.name from school s where s.id = " + student.getSchoolid();
		Map mp1 = schoolDao.findBySql(sql1).get(0); 
		student.setSchoolname(String.valueOf(mp1.get("name")));
		return student;
	}

	@Override
	public void edit(Tstudent ts) {
		Tstudent student = studentDao.get(Tstudent.class, ts.getId());
		student.setSchoolid(ts.getSchoolid());
		student.setClassesid(ts.getClassesid());
		student.setName(ts.getName());
		student.setGender(ts.getGender());
		student.setNation(ts.getNation());
		student.setBirthday(ts.getBirthday());
		student.setAddress(ts.getAddress());
		student.setPhoto(ts.getPhoto());
		studentDao.update(student);
	}

	@Override
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tstudent tstudent = studentDao.get(Tstudent.class, Long.valueOf(id));
				tstudent.setStatus("0");//更改为禁用状态 0
				studentDao.update(tstudent);
			}
			
		}
	}
	
	@Override
	public void restoreBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tstudent tstudent = studentDao.get(Tstudent.class, Long.valueOf(id));
				tstudent.setStatus("1");//更改为正常状态 1
				studentDao.update(tstudent);
			}
			
		}
	}
	/*@Override
	public List getSchools() {
		String hql = "from Tschool";
		List list = schoolDao.find(hql);
		return list;
	}*/
	
	/*private String whereHql(Map<String, String> map, Map<String, Object> params) {
		String hql = "";
		if (map != null && map.size() > 0) {
			hql += " where 1=1 ";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "'%" + map.get("name") + "%'");
			}
			if (!StringUtils.isEmpty(map.get("starttime"))) {
				hql += " and t.createtime >= :createdatetimeStart";
				params.put("createdatetimeStart", map.get("starttime"));
			}
			if (!StringUtils.isEmpty(map.get("endtime"))) {
				hql += " and t.createtime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", map.get("endtime"));
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
