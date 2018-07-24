package com.tjck.tamblog.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "school",uniqueConstraints = {@UniqueConstraint(columnNames= {"name","provinceid","cityid","townid"})})
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tschool implements Serializable{

	private static final long serialVersionUID = -1772769339466335325L;
	
	@Id
	@GeneratedValue(generator = "schooltableGenerator")
	@GenericGenerator(name = "schooltableGenerator", strategy = "native")
	@Column
	private Long id;    //主键id
	private String name;	//幼儿园名称
	private String manager; //幼儿园负责人
	private String phone;   //负责人手机号
	private String provinceid; //幼儿园所在省份ID
	private String cityid;    //幼儿园所在城市ID
	private String townid;   //幼儿园所在区县ID
	private String street;   //幼儿园所在街道
	private String createtime;  //幼儿园创建时间
	private String status;     //幼儿园状态,1,正常；0，禁用
	private String note;   //幼儿园备注信息
	
	@Transient
	private String provincename;
	@Transient
	private String cityname;
	@Transient
	private String townname;
	@Transient
	private String createdatetimeStart;
	@Transient
	private String createdatetimeEnd;
	
	@OneToMany(targetEntity = Tclasses.class,cascade = CascadeType.ALL,orphanRemoval = true,mappedBy="school")
	private Set<Tclasses> classes = new HashSet<Tclasses>(0);
	
	public Tschool() {
		super();
	}
	
	public Tschool(Long id, String name, String manager, String phone, String provinceid, String cityid, String townid,
			String street, String createtime, String status, String note, String provincename, String cityname,
			String townname, String createdatetimeStart, String createdatetimeEnd, Set<Tclasses> classes) {
		super();
		this.id = id;
		this.name = name;
		this.manager = manager;
		this.phone = phone;
		this.provinceid = provinceid;
		this.cityid = cityid;
		this.townid = townid;
		this.street = street;
		this.createtime = createtime;
		this.status = status;
		this.note = note;
		this.provincename = provincename;
		this.cityname = cityname;
		this.townname = townname;
		this.createdatetimeStart = createdatetimeStart;
		this.createdatetimeEnd = createdatetimeEnd;
		this.classes = classes;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getTownid() {
		return townid;
	}

	public void setTownid(String townid) {
		this.townid = townid;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getTownname() {
		return townname;
	}

	public void setTownname(String townname) {
		this.townname = townname;
	}

	public String getCreatedatetimeStart() {
		return createdatetimeStart;
	}

	public void setCreatedatetimeStart(String createdatetimeStart) {
		this.createdatetimeStart = createdatetimeStart;
	}

	public String getCreatedatetimeEnd() {
		return createdatetimeEnd;
	}

	public void setCreatedatetimeEnd(String createdatetimeEnd) {
		this.createdatetimeEnd = createdatetimeEnd;
	}

	public Set<Tclasses> getClasses() {
		return classes;
	}
	
	public void setClasses(Set<Tclasses> classes) {
		this.classes = classes;
	}
	
}
