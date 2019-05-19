package com.jzfblog.crm.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.jzfblog.crm.domain.Customer;
import com.jzfblog.crm.domain.PageBean;

public interface CustomerService {

	void save(Customer customer);

	PageBean findByPage(DetachedCriteria detachedCriteria, Integer currentPageNum, Integer pageSize);

	void delete(Customer customer);

	Customer findById(Customer customer);

	void update(Customer customer);

	List<Customer> findAll();

}
