package com.tjck.tamblog.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tuser implements Serializable{
	
	private static final long serialVersionUID = -313334905017294677L;
	@Id
	@GeneratedValue(generator = "usertableGenerator")
	@GenericGenerator(name = "usertableGenerator", strategy = "native")
	@Column
	private String id;    //主键id
	private String openid; //微信唯一标识
	private String phone;  //手机号
	private String wxname; //微信用户名
	private String username;  //登陆用户名
	private String password;  //登陆密码
	private String status;   //用户状态
	private String platform;  //用户来源，暂不使用
	private String createtime;
	
	@Transient
	private String roleIds;
	
	@Transient
	private String roleNames;
	
	public String getRoleNames(){
		String roleNames = "";
		if (roles != null) {
			for(Trole role : roles){
				String name = role.getName();
				roleNames += name + "   ";
			}
		}
		return roleNames;
	}
	
	/**------教师-------*/
	@Transient
	private String tid;
	@Transient
	private String tname;
	@Transient
	private String tphone;
	@Transient
	private String tgender;
	@Transient
	private String tnation;
	@Transient
	private String tbirthday;
	@Transient
	private String tschoolid;
	@Transient
	private String tsname;
	/**-------------*/
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	@OrderBy("id ASC")
	private Set<Trole> roles = new HashSet<Trole>(0);
	
	public Tuser() {
		super();
	}

	public Tuser(String id, String openid, String phone, String wxname, String username, String password, String status,
			String platform, String createtime, Set<Trole> roles) {
		super();
		this.id = id;
		this.openid = openid;
		this.phone = phone;
		this.wxname = wxname;
		this.username = username;
		this.password = password;
		this.status = status;
		this.platform = platform;
		this.createtime = createtime;
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWxname() {
		return wxname;
	}

	public void setWxname(String wxname) {
		this.wxname = wxname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public Set<Trole> getRoles() {
		return roles;
	}

	public void setRoles(Set<Trole> roles) {
		this.roles = roles;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTphone() {
		return tphone;
	}

	public void setTphone(String tphone) {
		this.tphone = tphone;
	}

	public String getTgender() {
		return tgender;
	}

	public void setTgender(String tgender) {
		this.tgender = tgender;
	}

	public String getTnation() {
		return tnation;
	}

	public void setTnation(String tnation) {
		this.tnation = tnation;
	}

	public String getTbirthday() {
		return tbirthday;
	}

	public void setTbirthday(String tbirthday) {
		this.tbirthday = tbirthday;
	}

	public String getTschoolid() {
		return tschoolid;
	}

	public void setTschoolid(String tschoolid) {
		this.tschoolid = tschoolid;
	}

	public String getTsname() {
		return tsname;
	}

	public void setTsname(String tsname) {
		this.tsname = tsname;
	}
	
}
