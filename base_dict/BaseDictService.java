package com.jzfblog.crm.service;

import java.util.List;

import com.jzfblog.crm.domain.BaseDict;

public interface BaseDictService {

	List<BaseDict> findByTypeCode(String dict_type_code);

}
