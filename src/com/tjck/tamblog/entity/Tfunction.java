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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "function")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tfunction implements Serializable{
	
	private static final long serialVersionUID = 5229028860716098337L;
	
	@Id
	@GeneratedValue(generator = "functiontableGenerator")
	@GenericGenerator(name = "functiontableGenerator", strategy = "native")
	@Column
	private String id;    //主键id
	private String name;  //权限名称
	private String code;  //关键字
	private String description;  //权限描述
	private String url;     //权限路径
	private String generatemenu;  //是否生成菜单，1是，0否
	private Integer zindex;    //优先级
	private String status;   //权限状态，1正常，0禁用
	
	@Transient
	private String pname;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pid")
	private Tfunction parentFunction;  //父权限
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_function", joinColumns = { @JoinColumn(name = "function_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	//@OrderBy("zindex ASC")
	private Set<Trole> roles = new HashSet<Trole>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentFunction")
	private Set<Tfunction> children = new HashSet<Tfunction>(0);
	
	
	public Tfunction() {
		super();
	}

	public Tfunction(String functionId) {
		this.id = functionId;
	}
	
	public String getpId(){
		if(parentFunction == null){
			return "0";
		}
		return parentFunction.getId();
	}
	
	public String getText(){
		return name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGeneratemenu() {
		return generatemenu;
	}

	public void setGeneratemenu(String generatemenu) {
		this.generatemenu = generatemenu;
	}

	public Integer getZindex() {
		return zindex;
	}

	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Tfunction getParentFunction() {
		return parentFunction;
	}

	public void setParentFunction(Tfunction parentFunction) {
		this.parentFunction = parentFunction;
	}

	public Set<Trole> getRoles() {
		return roles;
	}

	public void setRoles(Set<Trole> roles) {
		this.roles = roles;
	}
	
	public Set<Tfunction> getChildren() {
		return children;
	}

	public void setChildren(Set<Tfunction> children) {
		this.children = children;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

}
