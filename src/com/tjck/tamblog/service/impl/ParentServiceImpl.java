package com.tjck.tamblog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjck.tamblog.dao.base.IBaseDao;
import com.tjck.tamblog.entity.Tparent;
import com.tjck.tamblog.service.IParentService;
import com.tjck.tamblog.utils.PageBean;

@Service
public class ParentServiceImpl implements IParentService{
	
	@Autowired
	private IBaseDao<Tparent> parentDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<Tparent> dataGrid(Map<String, String> map, PageBean pb) {
		List<Tparent> sList = new ArrayList<Tparent>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select sp.*, p.name name,p.phone phone,s.name stuname,c.name cname,sc.name scname from student_parent sp"
				+ " left join parent p on sp.parentid = p.id"
				+ " left join student s on sp.studentid = s.id"
				+ " left join classes c on s.classesid= c.id"
				+ " left join school sc on s.schoolid = sc.id";
		if (map != null && map.size() > 0) {
			hql += " where 1=1";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and p.name like :name";
				params.put("name", "%%" + map.get("name") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("phone"))) {
				hql += " and p.phone like :phone";
				params.put("phone", "%%" + map.get("phone") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += " and sc.id = :schoolid";
				params.put("schoolid", map.get("schoolid"));
			}
		}
		hql += " group by p.id";
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by p." + pb.getSort() + " " + pb.getOrder();
		}
		
		List<Map> listmap = parentDao.findBySql(hql, params, pb.getPage(), pb.getRows());
		for(Map s:listmap) {
			Tparent tp = new Tparent();
			tp.setId(Long.valueOf(s.get("id").toString()));
			tp.setName(String.valueOf(s.get("name")));
			tp.setPhone(String.valueOf(s.get("phone")));
			tp.setStuname(String.valueOf(s.get("stuname")));
			tp.setCname(String.valueOf(s.get("cname")));
			tp.setScname(String.valueOf(s.get("scname")));
			sList.add(tp);
		}
		/*String hql = "from Tparent t";
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
		}
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by t." + pb.getSort() + " " + pb.getOrder();
		}
		sList = parentDao.find(hql, params, pb.getPage(), pb.getRows());*/
		return sList;
	}

	@Override
	public Long count(Map<String, String> map, PageBean pb) {
		Map<String, Object> params = new HashMap<String, Object>();
		/*String hql = "from Tparent t ";
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
		}
		return parentDao.count("select count(*) " + hql, params);*/
		String hql = "select sp.*, p.name name,p.phone phone,s.name stuname,c.name cname,sc.name scname from student_parent sp"
				+ " left join parent p on sp.parentid = p.id"
				+ " left join student s on sp.studentid = s.id"
				+ " left join classes c on s.classesid= c.id"
				+ " left join school sc on s.schoolid = sc.id";
		if (map != null && map.size() > 0) {
			hql += " where 1=1";
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and p.name like :name";
				params.put("name", "%%" + map.get("name") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("phone"))) {
				hql += " and p.phone like :phone";
				params.put("phone", "%%" + map.get("phone") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += " and sc.id = :schoolid";
				params.put("schoolid", map.get("schoolid"));
			}
		}
		hql += " group by p.id";
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by p." + pb.getSort() + " " + pb.getOrder();
		}
		return Long.valueOf(String.valueOf(parentDao.countBySql("select count(*) from (" + hql + ") as t", params)));
	}

	@Override
	public void add(Tparent parent) {
		parentDao.save(parent);
	}

	/*@Override
	public Tstudent get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tstudent student = studentDao.get("from Tstudent t where t.id = :id", params);
		String sql = "select r.id,r.name from classes r where r.id = " + student.getClassesid();
		Map mp = classesDao.findBySql(sql).get(0); 
		student.setClassesname(String.valueOf(mp.get("name")));
		return student;
	}*/

	/*@Override
	public void edit(Tstudent ts) {
		Tstudent student = studentDao.get(Tstudent.class, ts.getId());
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
	}*/
	
}
