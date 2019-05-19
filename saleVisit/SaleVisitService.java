package com.jzfblog.crm.service;

import org.hibernate.criterion.DetachedCriteria;

import com.jzfblog.crm.domain.PageBean;
import com.jzfblog.crm.domain.SaleVisit;

public interface SaleVisitService {

	PageBean findByPage(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize);

	void save(SaleVisit saleVisit);

	void update(SaleVisit saleVisit);

	void delete(SaleVisit saleVisit);

	SaleVisit findById(SaleVisit saleVisit);

}
