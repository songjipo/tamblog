package com.tjck.tamblog.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "attend")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tattend implements Serializable{

	private static final long serialVersionUID = -7155024998089171890L;
	
	@Id
	@GeneratedValue(generator = "attendtableGenerator")
	@GenericGenerator(name = "attendtableGenerator", strategy = "native")
	@Column
	private String id;
	
	private String cardno;
	private String time;
	private String photo;
	private String type;
	
	@Transient
	private String stuname;
	@Transient
	private String schoolid;
	@Transient
	private String schname;
	@Transient
	private String classesid;
	@Transient
	private String clname;
	@Transient
	private String pname;
	@Transient
	private String rname;
	@Transient
	private String tname;
	
	
	public Tattend() {
		super();
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStuname() {
		return stuname;
	}
	public void setStuname(String stuname) {
		this.stuname = stuname;
	}
	public String getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}
	public String getSchname() {
		return schname;
	}
	public void setSchname(String schname) {
		this.schname = schname;
	}
	public String getClassesid() {
		return classesid;
	}
	public void setClassesid(String classesid) {
		this.classesid = classesid;
	}
	public String getClname() {
		return clname;
	}
	public void setClname(String clname) {
		this.clname = clname;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
}
