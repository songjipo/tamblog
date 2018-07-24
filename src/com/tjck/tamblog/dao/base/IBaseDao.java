package com.tjck.tamblog.dao.base;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface IBaseDao<T> {

	public Serializable save(T entity);

	public void delete(T entity);

	public void update(T entity);

	public void saveOrUpdate(T entity);

	public T get(Class<T> c, Serializable id);

	public T get(String hql);

	public T get(String hql, Map<String, Object> params);

	public List<T> find(String hql);

	public List<T> find(String hql, Map<String, Object> params);

	public List<T> find(String hql, int page, int rows);

	public List<T> find(String hql, Map<String, Object> params, int page, int rows);

	public Long count(String hql);

	public Long count(String hql, Map<String, Object> params);

	public int executeHql(String hql);

	public int executeHql(String hql, Map<String, Object> params);

	@SuppressWarnings("rawtypes")
	public List<Map> findBySql(String sql);

	@SuppressWarnings("rawtypes")
	public List<Map> findBySql(String sql, int page, int rows);

	@SuppressWarnings("rawtypes")
	public List<Map> findBySql(String sql, Map<String, Object> params);

	@SuppressWarnings("rawtypes")
	public List<Map> findBySql(String sql, Map<String, Object> params, int page, int rows);

	public int executeSql(String sql);

	public int executeSql(String sql, Map<String, Object> params);

	public BigInteger countBySql(String sql);

	public BigInteger countBySql(String sql, Map<String, Object> params);

}
