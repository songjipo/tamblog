package com.tjck.tamblog.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "wxtoken")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Twxtoken implements Serializable{

	private static final long serialVersionUID = -7155024998089171890L;
	
	@Id
	@GeneratedValue(generator = "wxtokentableGenerator")
	@GenericGenerator(name = "wxtokentableGenerator", strategy = "native")
	@Column
	private String id;
	
	private String appid;
	private String secret;
	private String accesstoken;
	private String time;
	private String seconds;
	
	public Twxtoken() {
		super();
	}

	public Twxtoken(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSeconds() {
		return seconds;
	}

	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}
}
