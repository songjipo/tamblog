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
import com.tjck.tamblog.entity.Tparent;
import com.tjck.tamblog.entity.Trole;
import com.tjck.tamblog.entity.Tteacher;
import com.tjck.tamblog.entity.Tuser;
import com.tjck.tamblog.service.IUserService;
import com.tjck.tamblog.utils.DateUtils;
import com.tjck.tamblog.utils.MD5Utils;
import com.tjck.tamblog.utils.PageBean;

@Service
@Transactional
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private IBaseDao<Tuser> userDao;
	@Autowired
	private IBaseDao<Tteacher> teacherDao;
	@Autowired
	private IBaseDao<Tparent> parentDao;
	
	@Override
	public Tuser login(String username, String password) {
		String md5pass = MD5Utils.md5(password);
		String hql = "from Tuser u where u.username = :username and u.password = :password";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("password", md5pass);
		List<Tuser> list = userDao.find(hql, params);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Tuser getByUsername(String username) {
		String hql = "from Tuser u where u.username = :username";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		List<Tuser> list = userDao.find(hql, params);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Tuser get(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", String.valueOf(id));
		Tuser tuser = userDao.get("from Tuser t where t.id = :id", params);
		return tuser;
	}
	
	@Override
	public void edit(Tuser tuser) {
		Tuser user = userDao.get(Tuser.class, tuser.getId());
		user.setUsername(tuser.getUsername());
		user.setPhone(tuser.getPhone());
		
		String roleIds = tuser.getRoleIds();
		
		Set<Trole> roles = user.getRoles();
		if (roles != null) {
			Iterator<Trole> it = roles.iterator();
			while(it.hasNext()) {
				Trole tr = it.next();
				tr.getUsers().remove(tuser);
				tuser.getRoles().remove(tr);
				it.remove();
			}
		}
		
		if(StringUtils.isNotBlank(roleIds)){
			String[] rIds = roleIds.split(",");
			for (String roleId : rIds) {
				Trole role = new Trole(roleId);
				user.getRoles().add(role);
			}
		}
		
		
		userDao.update(user);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] schooIds = ids.split(",");
			for(String id:schooIds) {
				Tuser tuser = userDao.get(Tuser.class, id);
				tuser.setStatus("0");//更改为禁用状态 0
				userDao.update(tuser);
				String sql = "select t.* from teacher t join user u on t.userid = u.id where u.id = " + id;
				List<Map> listmap = teacherDao.findBySql(sql);
				if (listmap != null && listmap.size() > 0) {
					Tteacher t = teacherDao.get("from Tteacher t where t.id = " + listmap.get(0).get("id"));
					t.setStatus("0");
					teacherDao.update(t);
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
				Tuser tuser = userDao.get(Tuser.class, id);
				tuser.setStatus("1");//更改为正常状态 1
				userDao.update(tuser);
				String sql = "select t.* from teacher t join user u on t.userid = u.id where u.id = " + id;
				List<Map> listmap = teacherDao.findBySql(sql);
				if (listmap != null && listmap.size() > 0) {
					Tteacher t = teacherDao.get("from Tteacher t where t.id = " + listmap.get(0).get("id"));
					t.setStatus("1");
					teacherDao.update(t);
				}
			}
			
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Tuser> dataGrid_t(Map<String, String> map, PageBean pb) {
		List<Tuser> sList = new ArrayList<Tuser>();
		List<Tuser> tList = new ArrayList<Tuser>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Tuser t where t.username <> 'admin'";
		String shql = "";
		
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.username like :username";
				params.put("username", "%%" + map.get("name") + "%%");
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
				shql = " and t.schoolid = " + map.get("schoolid");
			}
		}
		
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by t." + pb.getSort() + " " + pb.getOrder();
		}
		
		sList = userDao.find(hql, params, pb.getPage(), pb.getRows());
		for (Tuser user : sList) {
			String tsql = "select t.*,s.name sname from teacher t join school s on t.schoolid = s.id where t.userid = "
					+ user.getId() + shql;
			List<Map> listmap = teacherDao.findBySql(tsql);
			if(listmap != null && listmap.size() > 0) {
				user.setTid(String.valueOf(listmap.get(0).get("id")));
				user.setTbirthday(String.valueOf(listmap.get(0).get("birthday")));
				user.setTgender(String.valueOf(listmap.get(0).get("gender")));
				user.setTname(String.valueOf(listmap.get(0).get("name")));
				user.setTnation(String.valueOf(listmap.get(0).get("nation")));
				user.setTphone(String.valueOf(listmap.get(0).get("phone")));
				user.setTschoolid(String.valueOf(listmap.get(0).get("schoolid")));
				user.setTsname(String.valueOf(listmap.get(0).get("sname")));
				tList.add(user);
			}
		}
		return tList;
	}
	
	@Override
	public Long count_t(Map<String, String> map, PageBean pb) {
		String sql = "select t.*,s.name sname from teacher t join school s on t.schoolid = s.id join user u on t.userid = u.id where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("name"))) {
				sql += " and u.username like :username";
				params.put("username", "%%" + map.get("name") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("phone"))) {
				sql += " and u.phone like :phone";
				params.put("phone", "%%" + map.get("phone") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("starttime"))) {
				sql += " and u.createtime >= :createdatetimeStart";
				params.put("createdatetimeStart", map.get("starttime"));
			}
			if (!StringUtils.isEmpty(map.get("endtime"))) {
				sql += " and u.createtime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", map.get("endtime"));
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				sql += " and sc.id = " + map.get("schoolid");
			}
		}
		return Long.valueOf(String.valueOf(teacherDao.countBySql("select count(*) from (" + sql + ") as t", params)));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Tuser> dataGrid_p(Map<String, String> map, PageBean pb) {
		List<Tuser> sList = new ArrayList<Tuser>();
		List<Tuser> tList = new ArrayList<Tuser>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Tuser t where t.username <> 'admin'";
		String shql = "";
		
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("name"))) {
				hql += " and t.username like :username";
				params.put("username", "%%" + map.get("name") + "%%");
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
				shql = " and sc.id = " + map.get("schoolid");
			}
		}
		
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by t." + pb.getSort() + " " + pb.getOrder();
		}
		
		sList = userDao.find(hql, params, pb.getPage(), pb.getRows());
		for (Tuser user : sList) {
			String tsql = "select p.id id,p.name tname,p.phone tphone,sc.id schoolid,sc.name tsname from parent p "
					+ "join student_parent sp on p.id = sp.parentid "
					+ "join student s on sp.studentid = s.id "
					+ "join school sc on s.schoolid = sc.id where p.userid = "
					+ user.getId() + shql;
			List<Map> listmap = parentDao.findBySql(tsql);
			if(listmap != null && listmap.size() > 0) {
				user.setTid(String.valueOf(listmap.get(0).get("id")));
				user.setTname(String.valueOf(listmap.get(0).get("tname")));
				user.setTphone(String.valueOf(listmap.get(0).get("tphone")));
				user.setTschoolid(String.valueOf(listmap.get(0).get("schoolid")));
				user.setTsname(String.valueOf(listmap.get(0).get("tsname")));
				tList.add(user);
			}
		}
		return tList;
	}
	
	@Override
	public Long count_p(Map<String, String> map, PageBean pb) {
		String sql = "select p.id id,p.name tname,p.phone tphone,sc.id schoolid,sc.name tsname from parent p "
				+ "join student_parent sp on p.id = sp.parentid "
				+ "join student s on sp.studentid = s.id "
				+ "join school sc on s.schoolid = sc.id "
				+ "join user u on p.userid = u.id where 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();
		
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("name"))) {
				sql += " and u.username like :username";
				params.put("username", "%%" + map.get("name") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("phone"))) {
				sql += " and u.phone like :phone";
				params.put("phone", "%%" + map.get("phone") + "%%");
			}
			if (!StringUtils.isEmpty(map.get("starttime"))) {
				sql += " and u.createtime >= :createdatetimeStart";
				params.put("createdatetimeStart", map.get("starttime"));
			}
			if (!StringUtils.isEmpty(map.get("endtime"))) {
				sql += " and u.createtime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", map.get("endtime"));
			}
			if (!StringUtils.isEmpty(map.get("schoolid"))) {
				sql += " and sc.id = " + map.get("schoolid");
			}
		}
		return Long.valueOf(String.valueOf(parentDao.countBySql("select count(*) from (" + sql + ") as t", params)));
	}
	
	@Override
	public List<Tuser> dataGrid_wx(Map<String, String> map, PageBean pb) {
		List<Tuser> sList = new ArrayList<Tuser>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Tuser t where t.username is null";
		
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("wxname"))) {
				hql += " and t.wxname like :wxname";
				params.put("wxname", "%%" + map.get("wxname") + "%%");
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
		}
		
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by t." + pb.getSort() + " " + pb.getOrder();
		}
		
		sList = userDao.find(hql, params, pb.getPage(), pb.getRows());
		return sList;
	}
	
	@Override
	public Long count_wx(Map<String, String> map, PageBean pb) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Tuser t where t.username is null";
		
		if (map != null && map.size() > 0) {
			if (!StringUtils.isEmpty(map.get("wxname"))) {
				hql += " and t.wxname like :wxname";
				params.put("wxname", "%%" + map.get("wxname") + "%%");
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
		}
		
		if ((pb.getSort() != null) && (pb.getOrder() != null)) {
			hql += " order by t." + pb.getSort() + " " + pb.getOrder();
		}
		return Long.valueOf(String.valueOf(userDao.countBySql("select count(*) from (" + hql + ") as t", params)));
	}

	@Override
	public void editPwd(Tuser user,String userid) {
		Tuser u = userDao.get(Tuser.class, userid);
		u.setPassword(MD5Utils.md5(user.getPassword()));
		userDao.update(u);
	}
	
	@Override
	public void add(Tuser tuser) {
		tuser.setStatus("1");
		tuser.setPlatform("1"); //默认微信公众号关注
		tuser.setCreatetime(DateUtils.getCurrentTime());
		tuser.setPassword(MD5Utils.md5("123456")); //默认初始密码123456
		userDao.save(tuser);
		String roleIds = tuser.getRoleIds();
		if(StringUtils.isNotBlank(roleIds)){
			String[] rIds = roleIds.split(",");
			for (String roleId : rIds) {
				Trole role = new Trole(roleId);
				tuser.getRoles().add(role);
			}
		}
	}

}
