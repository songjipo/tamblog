package com.tjck.tamblog.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "student_parent", uniqueConstraints = {
		@UniqueConstraint(columnNames= {"studentid","parentid","cardid"}),
		@UniqueConstraint(columnNames= {"studentid","cardid"})})
@DynamicInsert(true)
@DynamicUpdate(true)
public class T_student_parent implements Serializable {

	private static final long serialVersionUID = -5847267260570168721L;
	
	@Id
	@GeneratedValue(generator = "stuparenttableGenerator")
	@GenericGenerator(name = "stuparenttableGenerator", strategy = "native")
	@Column
	private Long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "parentid")
	private Tparent parent;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "studentid")
	private Tstudent student;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cardid")
	private Tcard card;
	
	private String relationid;

	public T_student_parent() {
		super();
	}
	
	public T_student_parent(Long id, Tparent parent, Tstudent student, Tcard card, String relationid) {
		super();
		this.id = id;
		this.parent = parent;
		this.student = student;
		this.card = card;
		this.relationid = relationid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Tparent getParent() {
		return parent;
	}

	public void setParent(Tparent parent) {
		this.parent = parent;
	}
	
	public Tstudent getStudent() {
		return student;
	}

	public void setStudent(Tstudent student) {
		this.student = student;
	}
	
	public String getRelationid() {
		return relationid;
	}

	public void setRelationid(String relationid) {
		this.relationid = relationid;
	}

	public Tcard getCard() {
		return card;
	}

	public void setCard(Tcard card) {
		this.card = card;
	}
	
}
