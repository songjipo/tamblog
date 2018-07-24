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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "teacher",uniqueConstraints = {
		@UniqueConstraint(columnNames= {"phone","schoolid"}),
		@UniqueConstraint(columnNames= {"cardno","schoolid"})})
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tteacher implements Serializable {

	private static final long serialVersionUID = -1307072795042035702L;
	
	@Id
	@GeneratedValue(generator = "teachertableGenerator")
	@GenericGenerator(name = "teachertableGenerator", strategy = "native")
	@Column
	private Long id;
	private String name;
	private String phone;
	private String cardno;
	private String gender;
	private String nation;
	private String birthday;
	private String photo;
	private String status;
	private String createtime;
	private String schoolid;
	private String userid;
	
	@Transient
	private String classIds;
	@Transient
	private String classesNames;
	@Transient
	private String schoolName;
	
	public String getClassesNames() {
		String classesNames = "";
		if (classesList != null) {
			for(Tclasses classes:classesList) {
				String name = classes.getName();
				classesNames += name + "   ";
			}
		}
		return classesNames;
	}

	public void setClassesNames(String classesNames) {
		this.classesNames = classesNames;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "teacher_classes", joinColumns = { @JoinColumn(name = "teacher_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "classes_id", nullable = false, updatable = false) })
	@OrderBy("id ASC")
	private Set<Tclasses> classesList = new HashSet<Tclasses>(0);

	public Tteacher() {
		super();
	}

	public Tteacher(Long id, String name, String phone, String gender, String nation, String birthday, String photo,
			String status, String createtime, String schoolid, String userid, Set<Tclasses> classesList) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.gender = gender;
		this.nation = nation;
		this.birthday = birthday;
		this.photo = photo;
		this.status = status;
		this.createtime = createtime;
		this.schoolid = schoolid;
		this.userid = userid;
		this.classesList = classesList;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
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

	public String getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public Set<Tclasses> getClassesList() {
		return classesList;
	}

	public void setClassesList(Set<Tclasses> classesList) {
		this.classesList = classesList;
	}

	public String getClassIds() {
		return classIds;
	}

	public void setClassIds(String classIds) {
		this.classIds = classIds;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
}
