package com.jzfblog.crm.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * 閫氱敤鐨凞AO鐨勬帴鍙�
 * @author jt
 *
 */
public interface BaseDao<T> {

	public void save(T t);
	
	public void update(T t);
	
	public void delete(T t);
	
	public T findById(Serializable id);
	
	// 鏌ヨ鎵�鏈�
	public List<T> findAll();
	
	// 缁熻涓暟鐨勬柟娉�
	public Integer findCount(DetachedCriteria detachedCriteria);
	
	// 鍒嗛〉鏌ヨ鐨勬柟娉�:
	public List<T> findByPage(DetachedCriteria detachedCriteria,Integer begin,Integer pageSize);
}
