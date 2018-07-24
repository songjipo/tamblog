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
@Table(name = "relation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Trelation implements Serializable{
	
	private static final long serialVersionUID = -1514951350780468013L;
	
	@Id
	@GeneratedValue(generator = "relationtableGenerator")
	@GenericGenerator(name = "relationtableGenerator", strategy = "native")
	@Column
	private Long id;
	private String schoolid;
	private String name;
	
	@Transient
	private String schoolname;
	
	public Trelation() {
		super();
	}

	public Trelation(Long id, String schoolid, String name) {
		super();
		this.id = id;
		this.schoolid = schoolid;
		this.name = name;
	}

	public String getSchoolid() {
		return schoolid;
	}
	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
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
	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

}
