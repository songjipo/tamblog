package com.tjck.tamblog.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "classes",uniqueConstraints = {@UniqueConstraint(columnNames= {"name","schoolid"})})
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tclasses implements Serializable{
	
	private static final long serialVersionUID = -1087267557518481298L;
	
	@Id
	@GeneratedValue(generator = "classestableGenerator")
	@GenericGenerator(name = "classestableGenerator", strategy = "native")
	@Column
	private Long id;
	private String schoolid;
	private String name;
	private String status;
	private String createtime;
	
	@Transient
	private String schoolname;
	
	@ManyToOne(targetEntity = Tschool.class,fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "schoolid",referencedColumnName = "id",insertable = false, updatable = false)
	private Tschool school;
	
	@OneToMany(targetEntity = Tstudent.class,cascade = CascadeType.ALL,orphanRemoval = true,mappedBy="classes")
	private Set<Tstudent> tstudents;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "teacher_classes", joinColumns = { @JoinColumn(name = "classes_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "teacher_id", nullable = false, updatable = false) })
	@OrderBy("id ASC")
	private Set<Tteacher> teacherList = new HashSet<Tteacher>(0);
	
	public Tclasses() {
		super();
	}
	
	public Tclasses(Long id) {
		this.id = id;
	}

	public Tclasses(Long id, String schoolid, String name, String status, String createtime, String schoolname,
			Tschool school, Set<Tstudent> tstudents, Set<Tteacher> teacherList) {
		super();
		this.id = id;
		this.schoolid = schoolid;
		this.name = name;
		this.status = status;
		this.createtime = createtime;
		this.schoolname = schoolname;
		this.school = school;
		this.tstudents = tstudents;
		this.teacherList = teacherList;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Tschool getSchool() {
		return school;
	}
	public void setSchool(Tschool school) {
		this.school = school;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public Set<Tstudent> getTstudents() {
		return tstudents;
	}

	public void setTstudents(Set<Tstudent> tstudents) {
		this.tstudents = tstudents;
	}
	
	public Set<Tteacher> getTeacherList() {
		return teacherList;
	}

	public void setTeacherList(Set<Tteacher> teacherList) {
		this.teacherList = teacherList;
	}

}
