package com.jzfblog.crm.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.jzfblog.crm.domain.Customer;
import com.jzfblog.crm.domain.LinkMan;
import com.jzfblog.crm.domain.PageBean;

public interface LinkManService {

	void save(LinkMan linkMan);

	PageBean findByPage(DetachedCriteria detachedCriteria, Integer currentPageNum, Integer pageSize);

	void delete(LinkMan linkMan);

	LinkMan findById(LinkMan linkMan);

	void update(LinkMan linkMan);

}
