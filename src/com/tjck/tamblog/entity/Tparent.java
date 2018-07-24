package com.tjck.tamblog.entity;

import java.io.Serializable;
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
@Table(name = "parent", uniqueConstraints = {@UniqueConstraint(columnNames= {"name","phone"})})
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tparent implements Serializable {

	private static final long serialVersionUID = -5300443864979615978L;
	
	@Id
	@GeneratedValue(generator = "parenttableGenerator")
	@GenericGenerator(name = "parenttableGenerator", strategy = "native")
	@Column
	private Long id;
	private String userid;
	private String name;
	private String phone;
	
	@Transient
	private String stuname;
	@Transient
	private String cname;
	@Transient
	private String scname;
	
	@OneToMany(mappedBy="parent",cascade=CascadeType.ALL)
	private Set<T_student_parent> studentParentList;

	public Tparent() {
		super();
	}

	public Tparent(Long id, String userid, String name, String phone,
			Set<T_student_parent> studentParentList) {
		super();
		this.id = id;
		this.userid = userid;
		this.name = name;
		this.phone = phone;
		this.studentParentList = studentParentList;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getStuname() {
		return stuname;
	}

	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getScname() {
		return scname;
	}

	public void setScname(String scname) {
		this.scname = scname;
	}

	public Set<T_student_parent> getStudentParentList() {
		return studentParentList;
	}

	public void setStudentParentList(Set<T_student_parent> studentParentList) {
		this.studentParentList = studentParentList;
	}
	
}
