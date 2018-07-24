package com.tjck.tamblog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjck.tamblog.dao.base.IBaseDao;
import com.tjck.tamblog.entity.Tattend;
import com.tjck.tamblog.service.IAttendService;
import com.tjck.tamblog.utils.PageBean;

@Service
public class AttendServiceImpl implements IAttendService{
	
	@Autowired
	private IBaseDao<Tattend> attendDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<Tattend> dataGrid(Map<String, String> map, PageBean pb) {
		List<Tattend> sList = new ArrayList<Tattend>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select a.*,s.name stuname,s.schoolid schoolid,s.classesid classesid,sch.name schname,cl.name clname, p.name pname,r.name rname from attend a "
				+ "join card c on a.cardno = c.cardno "
				+ "join student_parent sp on c.id = sp.cardid "
				+ "join student s on sp.studentid = s.id "
				+ "join parent p on sp.parentid = p.id "
				+ "join relation r on sp.relationid = r.id "
				+ "join school sch on s.schoolid = sch.id "
				+ "join classes cl on s.classesid = cl.id where a.type= '0'";
		
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("cardno"))) {
				hql += " and a.cardno like :cardno";
				params.put("cardno", "%%" + map.get("cardno") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += " and s.schoolid = :schoolid";
				params.put("schoolid", map.get("schoolid"));
			}
		}
		
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by a." + pb.getSort() + " " + pb.getOrder();
		}
		
		List<Map> listmap = attendDao.findBySql(hql, params, pb.getPage(), pb.getRows());
		for(Map s:listmap) {
			Tattend attend = new Tattend();
			attend.setId(s.get("id").toString());
			attend.setCardno(String.valueOf(s.get("cardno")));
			attend.setTime(String.valueOf(s.get("time")));
			attend.setPhoto(String.valueOf(s.get("photo")));
			attend.setType(String.valueOf(s.get("type")));
			attend.setStuname(String.valueOf(s.get("stuname")));
			attend.setSchoolid(String.valueOf(s.get("schoolid")));
			attend.setClassesid(String.valueOf(s.get("classesid")));
			attend.setClname(String.valueOf(s.get("clname")));
			attend.setSchname(String.valueOf(s.get("schname")));
			attend.setPname(String.valueOf(s.get("pname")));
			attend.setRname(String.valueOf(s.get("rname")));
			sList.add(attend);
		}
		return sList;
	}

	@Override
	public Long count(Map<String, String> map, PageBean pb) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select a.*,s.name stuname,s.schoolid schoolid,s.classesid classesid,sch.name schname,cl.name clname, p.name pname,r.name rname from attend a "
				+ "join card c on a.cardno = c.cardno "
				+ "join student_parent sp on c.id = sp.cardid "
				+ "join student s on sp.studentid = s.id "
				+ "join parent p on sp.parentid = p.id "
				+ "join relation r on sp.relationid = r.id "
				+ "join school sch on s.schoolid = sch.id "
				+ "join classes cl on s.classesid = cl.id where a.type='0'";
		
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("cardno"))) {
				hql += " and a.cardno like :cardno";
				params.put("cardno", "%%" + map.get("cardno").trim() + "%%");
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				hql += " and s.schoolid = :schoolid";
				params.put("schoolid", map.get("schoolid"));
			}
		}
		
		//return schoolDao.count("select count(*) " + hql + whereHql(map, params), params);
		return Long.valueOf(String.valueOf(attendDao.countBySql("select count(*) from (" + hql + ") as t", params)));
	}

}
