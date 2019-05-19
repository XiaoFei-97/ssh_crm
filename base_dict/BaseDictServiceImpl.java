package com.jzfblog.crm.service.impl;

import java.util.List;

import com.jzfblog.crm.dao.BaseDictDao;
import com.jzfblog.crm.domain.BaseDict;
import com.jzfblog.crm.service.BaseDictService;

public class BaseDictServiceImpl implements BaseDictService {

	//  Ù–‘◊¢»Î
	private BaseDictDao baseDictDao;
	
	public void setBaseDictDao(BaseDictDao baseDictDao) {
		this.baseDictDao = baseDictDao;
	}

	public List<BaseDict> findByTypeCode(String dict_type_code) {
		
		return baseDictDao.findByTypeCode(dict_type_code);
	}

}
