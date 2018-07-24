package com.tjck.tamblog.po;

import java.io.Serializable;

public class School implements Serializable{

	private static final long serialVersionUID = -4599053530401654182L;
	
	private Long id;
	private String name;
	private String manager;
	private String phone;
	private String provinceid;
	private String provincename;
	private String cityid;
	private String cityname;
	private String townid;
	private String townname;
	private String street;
	private String createtime;
	private String status;
	private String note;
	private String createdatetimeStart;
	private String createdatetimeEnd;
	
	public School() {
		super();
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

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getTownid() {
		return townid;
	}

	public void setTownid(String townid) {
		this.townid = townid;
	}

	public String getTownname() {
		return townname;
	}

	public void setTownname(String townname) {
		this.townname = townname;
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

}
