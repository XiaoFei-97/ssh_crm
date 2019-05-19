package com.jzfblog.crm.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.jzfblog.crm.dao.CustomerDao;
import com.jzfblog.crm.domain.Customer;
import com.jzfblog.crm.domain.PageBean;
import com.jzfblog.crm.service.CustomerService;

@Transactional
public class CustomerServiceImpl implements CustomerService {

	private CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	/**
	 * 保存用户
	 */
	public void save(Customer customer) {
		customerDao.save(customer);
	}

	/**
	 * 分页查询用户
	 */
	public PageBean findByPage(DetachedCriteria detachedCriteria, Integer currentPageNum, Integer pageSize) {
		
		//查询总记录数
		int totalRecords = customerDao.findCount(detachedCriteria);
		
		// 创建pageBean的对象
		PageBean pageBean = new PageBean(currentPageNum, totalRecords, pageSize);
		
		// 查询当前页的记录
		List<Customer> records = customerDao.findByPage(detachedCriteria, pageBean.getStartIndex(), pageSize);
		
		pageBean.setRecords(records);
		pageBean.setUrl("");
		
		return pageBean;
	}

	/**
	 * 删除客户
	 */
	public void delete(Customer customer) {

		customerDao.delete(customer);
	}

	/**
	 * 通过cust_id查找customer
	 */
	public Customer findById(Customer customer) {
		
		return customerDao.findById(customer.getCust_id());
	}

	/**
	 * 修改用户
	 */
	public void update(Customer customer) {

		customerDao.update(customer);
	}

	public List<Customer> findAll() {
	
		return customerDao.findAll();
	}

}
