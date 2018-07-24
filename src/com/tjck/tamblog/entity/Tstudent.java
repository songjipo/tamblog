package com.tjck.tamblog.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "student",uniqueConstraints = {@UniqueConstraint(columnNames= {"name","classesid","schoolid"})})
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tstudent implements Serializable {

	private static final long serialVersionUID = 1974382882022666536L;
	@Id
	@GeneratedValue(generator = "studenttableGenerator")
	@GenericGenerator(name = "studenttableGenerator", strategy = "native")
	@Column
	private Long id;
	private String name;
	private String gender;
	private String nation;
	private String birthday;
	private String address;
	private String photo;
	private String status;
	private String createtime;
	private String classesid;
	private String schoolid;
	
	@Transient
	private String classesname;
	@Transient
	private String schoolname;
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity = Tclasses.class,optional = false)
	@JoinColumn(name = "classesid",referencedColumnName = "id",insertable = false, updatable = false)
	private Tclasses classes;
	
	@OneToMany(mappedBy = "student",cascade=CascadeType.ALL)
	private Set<T_student_parent> studentParentList;

	public Tstudent() {
		super();
	}

	public Tstudent(Long id, String name, String gender, String nation, String birthday, String address, String photo,
			String status, String createtime, String classesid, String schoolid, String classesname, String schoolname, Tclasses classes,
			Set<T_student_parent> studentParentList) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.nation = nation;
		this.birthday = birthday;
		this.address = address;
		this.photo = photo;
		this.status = status;
		this.createtime = createtime;
		this.classesid = classesid;
		this.schoolid = schoolid;
		this.classesname = classesname;
		this.schoolname = schoolname;
		this.classes = classes;
		this.studentParentList = studentParentList;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getClassesid() {
		return classesid;
	}

	public void setClassesid(String classesid) {
		this.classesid = classesid;
	}

	public String getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}

	public String getClassesname() {
		return classesname;
	}

	public void setClassesname(String classesname) {
		this.classesname = classesname;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public Tclasses getClasses() {
		return classes;
	}

	public void setClasses(Tclasses classes) {
		this.classes = classes;
	}
	
	public Set<T_student_parent> getStudentParentList() {
		return studentParentList;
	}

	public void setStudentParentList(Set<T_student_parent> studentParentList) {
		this.studentParentList = studentParentList;
	}
	
}
