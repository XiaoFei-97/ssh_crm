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
	 * 保存用户
	 */
	public void save(LinkMan linkMan) {
		linkManDao.save(linkMan);
	}

	/**
	 * 分页查询用户
	 */
	public PageBean findByPage(DetachedCriteria detachedCriteria, Integer currentPageNum, Integer pageSize) {
		
		// 查询总记录数
		int totalRecords = linkManDao.findCount(detachedCriteria);
		
		// 创建pageBean的对象
		PageBean pageBean = new PageBean(currentPageNum, totalRecords, pageSize);
		
		// 查询当前页的记录
		List<LinkMan> records = linkManDao.findByPage(detachedCriteria, pageBean.getStartIndex(), pageSize);
		
		pageBean.setRecords(records);
		pageBean.setUrl("");
		
		return pageBean;
	}

	/**
	 * 删除客户
	 */
	public void delete(LinkMan linkMan) {

		linkManDao.delete(linkMan);
	}

	/**
	 * 通过cust_id查找customer
	 */
	public LinkMan findById(LinkMan linkMan) {
		
		return linkManDao.findById(linkMan.getLkm_id());
	}

	/**
	 * 修改用户
	 */
	public void update(LinkMan linkMan) {

		linkManDao.update(linkMan);
	}
	
}
