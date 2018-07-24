package com.tjck.tamblog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjck.tamblog.dao.base.IBaseDao;
import com.tjck.tamblog.entity.Tcard;
import com.tjck.tamblog.service.ICardService;
import com.tjck.tamblog.utils.PageBean;

@Service
public class CardServiceImpl implements ICardService{
	
	@Autowired
	private IBaseDao<Tcard> cardDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<Tcard> dataGrid(Map<String, String> map, PageBean pb) {
		List<Tcard> sList = new ArrayList<Tcard>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "SELECT c.*, s.name stuname,p.name pname,cl.name cname, sc.name scname from card c "
				+ "left join student_parent sp on c.id = sp.cardid "
				+ "left join student s on sp.studentid = s.id "
				+ "left join classes cl on s.classesid = cl.id "
				+ "left join school sc on s.schoolid = sc.id "
				+ "left join parent p on sp.parentid = p.id";
		
		if (map != null && map.size() > 0) {
			hql += " where 1=1";
			if (!StringUtils.isEmpty(map.get("cardno"))) {
				hql += " and c.cardno like :cardno";
				params.put("cardno", "%%" + map.get("cardno") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += " and sc.id = :schoolid";
				params.put("schoolid", map.get("schoolid"));
			}
		}
		
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by c." + pb.getSort() + " " + pb.getOrder();
		}
		
		List<Map> listmap = cardDao.findBySql(hql, params, pb.getPage(), pb.getRows());
		for(Map s:listmap) {
			Tcard card = new Tcard();
			card.setId(Long.valueOf(s.get("id").toString()));
			card.setCardno(String.valueOf(s.get("cardno")));
			card.setIsbind(String.valueOf(s.get("isbind")));
			card.setStatus(String.valueOf(s.get("status")));
			card.setStuname(String.valueOf(s.get("stuname")));
			card.setCname(String.valueOf(s.get("cname")));
			card.setScname(String.valueOf(s.get("scname")));
			card.setPname(String.valueOf(s.get("pname")));
			sList.add(card);
		}
		return sList;
	}

	@Override
	public Long count(Map<String, String> map, PageBean pb) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "SELECT c.*, s.name stuname,p.name pname,cl.name cname, sc.name scname from card c "
				+ "left join student_parent sp on c.id = sp.cardid "
				+ "left join student s on sp.studentid = s.id "
				+ "left join classes cl on s.classesid = cl.id "
				+ "left join school sc on s.schoolid = sc.id "
				+ "left join parent p on sp.parentid = p.id";
		
		if (map != null && map.size() > 0) {
			hql += " where 1=1";
			if (!StringUtils.isEmpty(map.get("cardno"))) {
				hql += " and c.cardno like :cardno";
				params.put("cardno", "%%" + map.get("cardno").trim() + "%%");
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += " and sc.id = :schoolid";
				params.put("schoolid", map.get("schoolid"));
			}
		}
		
		//return schoolDao.count("select count(*) " + hql + whereHql(map, params), params);
		return Long.valueOf(String.valueOf(cardDao.countBySql("select count(*) from (" + hql + ") as t", params)));
	}

	@Override
	public void add(Tcard card) {
		card.setStatus("1");
		card.setIsbind("0");
		cardDao.save(card);
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
	}

	@Override
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
	}*/

	@Override
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tcard card = cardDao.get(Tcard.class, Long.valueOf(id));
				card.setStatus("0");//更改为禁用状态 0
				cardDao.update(card);
			}
			
		}
	}
	
	@Override
	public void restoreBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tcard card = cardDao.get(Tcard.class, Long.valueOf(id));
				card.setStatus("1");//更改为正常状态 1
				cardDao.update(card);
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
