package com.jzfblog.crm.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.jzfblog.crm.dao.SaleVisitDao;
import com.jzfblog.crm.domain.PageBean;
import com.jzfblog.crm.domain.SaleVisit;
import com.jzfblog.crm.service.SaleVisitService;

/**
 * 客户拜访记录的service实现类
 * @author 蒋振飞
 *
 */
@Transactional
public class SaleVisitServiceImpl implements SaleVisitService {

	// dao层属性注入
	private SaleVisitDao saleVisitDao;

	public void setSaleVisitDao(SaleVisitDao saleVisitDao) {
		this.saleVisitDao = saleVisitDao;
	}

	/**
	 * 分页显示拜访记录
	 */
	public PageBean findByPage(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize) {
		
		// 获取总的客户访问记录数
		Integer totalRecords = saleVisitDao.findCount(detachedCriteria);
		
		PageBean pageBean = new PageBean(currPage, totalRecords, pageSize);
		
		
		// 查询当前页的记录
		List<SaleVisit> records = saleVisitDao.findByPage(detachedCriteria, pageBean.getStartIndex(), pageSize);
		
		pageBean.setRecords(records);
		pageBean.setUrl("");
		
		return pageBean;
	}

	/**
	 * 保存客户访问记录
	 */
	public void save(SaleVisit saleVisit) {

		saleVisitDao.save(saleVisit);
	}

	/**
	 * 修改客户保存记录
	 */
	public void update(SaleVisit saleVisit) {
		saleVisitDao.update(saleVisit);
	}

	public void delete(SaleVisit saleVisit) {

		saleVisitDao.delete(saleVisit);
	}

	public SaleVisit findById(SaleVisit saleVisit) {
		return saleVisitDao.findById(saleVisit.getVisit_id());
	}
	
	
}
