package com.tjck.tamblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjck.tamblog.dao.base.IBaseDao;
import com.tjck.tamblog.entity.T_student_parent;
import com.tjck.tamblog.service.IStuParentService;

@Service
//@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class StuParentServiceImpl implements IStuParentService{
	
	@Autowired
	private IBaseDao<T_student_parent> tspDao;

	/*@SuppressWarnings("rawtypes")
	@Override
	public List<Tstudent> dataGrid(Map<String, String> map, PageBean pb) {
		List<Tstudent> sList = new ArrayList<Tstudent>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select t.*,c.name classesname from student t left join classes c on t.classesid = c.id";
		
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
		}
		
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by t." + pb.getSort() + " " + pb.getOrder();
		}
		
		//List<Tschool> tsList = schoolDao.find(hql + whereHql(map, params) + orderHql(pb), params, pb.getPage(), pb.getRows());
		List<Map> listmap = studentDao.findBySql(hql, params, pb.getPage(), pb.getRows());
		for(Map s:listmap) {
			Tstudent ts = new Tstudent();
			ts.setClassesname(String.valueOf(s.get("classesname")));
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
		String hql = "select t.*,c.name classesname from student t left join classes c on t.classesid = c.id ";
		
		if (map != null && map.size() > 0) {
			hql += "where 1=1";
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
		}
		
		//return schoolDao.count("select count(*) " + hql + whereHql(map, params), params);
		return Long.valueOf(String.valueOf(studentDao.countBySql("select count(*) from (" + hql + ") as t", params)));
	}*/

	@Override
	//@Transactional
	public void add(T_student_parent tsp) {
		//try {
			tspDao.save(tsp);
		/*}catch (org.springframework.dao.DuplicateKeyException e) {
			throw new RuntimeException();
		}*/
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
