package com.jzfblog.crm.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.jzfblog.crm.dao.LinkManDao;
import com.jzfblog.crm.domain.LinkMan;
import com.jzfblog.crm.domain.PageBean;
import com.jzfblog.crm.service.LinkManService;

@Transactional
public class LinkManServiceImpl implements LinkManService {

	private LinkManDao linkManDao;

	public void setLinkManDao(LinkManDao linkManDao) {
		this.linkManDao = linkManDao;
	}

	/**
	 * �����û�
	 */
	public void save(LinkMan linkMan) {
		linkManDao.save(linkMan);
	}

	/**
	 * ��ҳ��ѯ�û�
	 */
	public PageBean findByPage(DetachedCriteria detachedCriteria, Integer currentPageNum, Integer pageSize) {
		
		// ��ѯ�ܼ�¼��
		int totalRecords = linkManDao.findCount(detachedCriteria);
		
		// ����pageBean�Ķ���
		PageBean pageBean = new PageBean(currentPageNum, totalRecords, pageSize);
		
		// ��ѯ��ǰҳ�ļ�¼
		List<LinkMan> records = linkManDao.findByPage(detachedCriteria, pageBean.getStartIndex(), pageSize);
		
		pageBean.setRecords(records);
		pageBean.setUrl("");
		
		return pageBean;
	}

	/**
	 * ɾ���ͻ�
	 */
	public void delete(LinkMan linkMan) {

		linkManDao.delete(linkMan);
	}

	/**
	 * ͨ��cust_id����customer
	 */
	public LinkMan findById(LinkMan linkMan) {
		
		return linkManDao.findById(linkMan.getLkm_id());
	}

	/**
	 * �޸��û�
	 */
	public void update(LinkMan linkMan) {

		linkManDao.update(linkMan);
	}
	
}
