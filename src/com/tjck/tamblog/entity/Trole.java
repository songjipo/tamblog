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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Trole implements Serializable{
	
	private static final long serialVersionUID = -1486066922572225987L;
	
	@Id
	@GeneratedValue(generator = "roletableGenerator")
	@GenericGenerator(name = "roletableGenerator", strategy = "native")
	@Column
	private String id;    //主键id
	private String name;  //角色名称
	private String code;  //关键字
	private String description;  //角色描述
	
	@Transient
	private String functionIds;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "role_function", joinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "function_id", nullable = false, updatable = false) })
	//@OrderBy("id ASC")
	private Set<Tfunction> functions = new HashSet<Tfunction>(0);
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) })
	@OrderBy("id ASC")
	private Set<Tuser> users = new HashSet<Tuser>(0);

	
	public Trole() {
		super();
	}
	
	public Trole(String id) {
		this.id = id;
	}

	public Trole(String id, String name, String code, String description, Set<Tfunction> functions, Set<Tuser> users) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.description = description;
		this.functions = functions;
		this.users = users;
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

	public Set<Tfunction> getFunctions() {
		return functions;
	}

	public void setFunctions(Set<Tfunction> functions) {
		this.functions = functions;
	}

	public Set<Tuser> getUsers() {
		return users;
	}

	public void setUsers(Set<Tuser> users) {
		this.users = users;
	}

	public String getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

}
