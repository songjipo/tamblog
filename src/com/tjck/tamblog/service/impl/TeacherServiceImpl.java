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
import com.tjck.tamblog.entity.Tschool;
import com.tjck.tamblog.entity.Tteacher;
import com.tjck.tamblog.entity.Tuser;
import com.tjck.tamblog.service.IClassesService;
import com.tjck.tamblog.service.ITeacherService;
import com.tjck.tamblog.utils.DateUtils;
import com.tjck.tamblog.utils.PageBean;

@Service
@Transactional
public class TeacherServiceImpl implements ITeacherService{
	
	@Autowired
	private IBaseDao<Tteacher> teacherDao;
	@Autowired
	private IClassesService classService;
	@Autowired
	private IBaseDao<Tschool> schoolDao;
	@Autowired
	private IBaseDao<Tuser> userDao;

	@Override
	public List<Tteacher> dataGrid(Map<String, String> map, PageBean pb) {
		List<Tteacher> sList = new ArrayList<Tteacher>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Tteacher t";
		
		if (map != null && map.size() > 0) {
			hql += " where 1=1";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "%%" + map.get("name") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("phone"))) {
				hql += " and t.phone like :phone";
				params.put("phone", "%%" + map.get("phone") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("cardno"))) {
				hql += " and t.cardno like :cardno";
				params.put("cardno", "%%" + map.get("cardno") + "%%");
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
		
		sList = teacherDao.find(hql, params, pb.getPage(), pb.getRows());
		for(Tteacher t:sList) {
			Tschool school = schoolDao.get("from Tschool t where t.id = " + t.getSchoolid());
			t.setSchoolName(school.getName());
		}
		return sList;
	}

	@Override
	public Long count(Map<String, String> map, PageBean pb) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Tteacher t ";
		
		if (map != null && map.size() > 0) {
			hql += "where 1=1";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.name like :name";
				params.put("name", "%%" + map.get("name").trim() + "%%");
			}
			if (!StringUtils.isEmpty(map.get("phone"))) {
				hql += " and t.phone like :phone";
				params.put("phone", "%%" + map.get("phone") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("cardno"))) {
				hql += " and t.cardno like :cardno";
				params.put("cardno", "%%" + map.get("cardno") + "%%");
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
		return teacherDao.count("select count(*) " + hql, params);
		//return Long.valueOf(String.valueOf(teacherDao.countBySql("select count(*) from (" + hql + ") as t", params)));
	}

	@Override
	public void add(Tteacher teacher) {
		teacher.setStatus("1");
		teacher.setCreatetime(DateUtils.getCurrentTimeyyyyMMdd());
		teacherDao.save(teacher);
		String classIds = teacher.getClassIds();
		if(StringUtils.isNotBlank(classIds)){
			String[] cIds = classIds.split(",");
			for (String classId : cIds) {
				Tclasses classes = classService.get(Long.valueOf(classId));
				teacher.getClassesList().add(classes);
			}
		}
	}

	@Override
	public Tteacher get(Long id) {
		Tteacher teacher = teacherDao.get("from Tteacher t where t.id = " + id);
		return teacher;
	}

	@Override
	public void edit(Tteacher ts) {
		String classIds = ts.getClassIds();
		
		Set<Tclasses> classes = ts.getClassesList();
		if (classes != null) {
			Iterator<Tclasses> it = classes.iterator();
			while (it.hasNext()) {
				Tclasses cl = it.next();
				cl.getTeacherList().remove(ts);
				it.remove();
			}
		}

		if (StringUtils.isNotBlank(classIds)) {
			String[] cIds = classIds.split(",");
			for (String cId : cIds) {
				Tclasses cl = new Tclasses(Long.valueOf(cId));
				ts.getClassesList().add(cl);
			}
		}
		
		
		teacherDao.update(ts);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tteacher teacher = teacherDao.get(Tteacher.class, Long.valueOf(id));
				teacher.setStatus("0");//更改为禁用状态 0
				teacherDao.update(teacher);
				String sql = "select u.* from user u join teacher t on t.userid = u.id where t.id = " + id;
				List<Map> listmap = userDao.findBySql(sql);
				if (listmap != null && listmap.size() > 0) {
					Tuser user = userDao.get("from Tuser t where t.id = " + listmap.get(0).get("id"));
					user.setStatus("0");
					userDao.update(user);
				}
			}
			
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void restoreBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tteacher teacher = teacherDao.get(Tteacher.class, Long.valueOf(id));
				teacher.setStatus("1");//更改为正常状态 1
				teacherDao.update(teacher);
				String sql = "select u.* from user u join teacher t on t.userid = u.id where t.id = " + id;
				List<Map> listmap = userDao.findBySql(sql);
				if (listmap != null && listmap.size() > 0) {
					Tuser user = userDao.get("from Tuser t where t.id = " + listmap.get(0).get("id"));
					user.setStatus("1");
					userDao.update(user);
				}
			}
			
		}
	}

	@Override
	public Tteacher getbyuserid(String id) {
		Tteacher teacher = teacherDao.get("from Tteacher t where t.userid = " + id);
		return teacher;
	}
	
}
