package com.jzfblog.crm.dao;

import java.util.List;

import com.jzfblog.crm.domain.BaseDict;

/**
 * 瀛楀吀DAO鐨勬帴鍙�
 * @author jt
 *
 */
public interface BaseDictDao extends BaseDao<BaseDict>{

	List<BaseDict> findByTypeCode(String dict_type_code);

}
