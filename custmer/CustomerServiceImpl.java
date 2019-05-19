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
	 * �����û�
	 */
	public void save(Customer customer) {
		customerDao.save(customer);
	}

	/**
	 * ��ҳ��ѯ�û�
	 */
	public PageBean findByPage(DetachedCriteria detachedCriteria, Integer currentPageNum, Integer pageSize) {
		
		//��ѯ�ܼ�¼��
		int totalRecords = customerDao.findCount(detachedCriteria);
		
		// ����pageBean�Ķ���
		PageBean pageBean = new PageBean(currentPageNum, totalRecords, pageSize);
		
		// ��ѯ��ǰҳ�ļ�¼
		List<Customer> records = customerDao.findByPage(detachedCriteria, pageBean.getStartIndex(), pageSize);
		
		pageBean.setRecords(records);
		pageBean.setUrl("");
		
		return pageBean;
	}

	/**
	 * ɾ���ͻ�
	 */
	public void delete(Customer customer) {

		customerDao.delete(customer);
	}

	/**
	 * ͨ��cust_id����customer
	 */
	public Customer findById(Customer customer) {
		
		return customerDao.findById(customer.getCust_id());
	}

	/**
	 * �޸��û�
	 */
	public void update(Customer customer) {

		customerDao.update(customer);
	}

	public List<Customer> findAll() {
	
		return customerDao.findAll();
	}

}
