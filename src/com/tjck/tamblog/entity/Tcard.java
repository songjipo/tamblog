package com.tjck.tamblog.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "card")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tcard implements Serializable {

	private static final long serialVersionUID = -5256791766144567349L;
	
	@Id
	@GeneratedValue(generator = "cardtableGenerator")
	@GenericGenerator(name = "cardtableGenerator", strategy = "native")
	@Column
	private Long id;
	private String cardno;
	private String isbind;
	private String status;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "card", cascade = CascadeType.ALL)
	private T_student_parent tsp;
	
	@Transient
	private String stuname;
	@Transient
	private String pname;
	@Transient
	private String cname;
	@Transient
	private String scname;

	public Tcard() {
		super();
	}

	public Tcard(Long id, String cardno, String isbind, String status, T_student_parent tsp, String stuname,
			String pname) {
		super();
		this.id = id;
		this.cardno = cardno;
		this.isbind = isbind;
		this.status = status;
		this.tsp = tsp;
		this.stuname = stuname;
		this.pname = pname;
	}



	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getIsbind() {
		return isbind;
	}

	public void setIsbind(String isbind) {
		this.isbind = isbind;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStuname() {
		return stuname;
	}

	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
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

	public T_student_parent getTsp() {
		return tsp;
	}

	public void setTsp(T_student_parent tsp) {
		this.tsp = tsp;
	}
	
}
