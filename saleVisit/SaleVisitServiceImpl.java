package com.jzfblog.crm.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.jzfblog.crm.dao.SaleVisitDao;
import com.jzfblog.crm.domain.PageBean;
import com.jzfblog.crm.domain.SaleVisit;
import com.jzfblog.crm.service.SaleVisitService;

/**
 * �ͻ��ݷü�¼��serviceʵ����
 * @author �����
 *
 */
@Transactional
public class SaleVisitServiceImpl implements SaleVisitService {

	// dao������ע��
	private SaleVisitDao saleVisitDao;

	public void setSaleVisitDao(SaleVisitDao saleVisitDao) {
		this.saleVisitDao = saleVisitDao;
	}

	/**
	 * ��ҳ��ʾ�ݷü�¼
	 */
	public PageBean findByPage(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize) {
		
		// ��ȡ�ܵĿͻ����ʼ�¼��
		Integer totalRecords = saleVisitDao.findCount(detachedCriteria);
		
		PageBean pageBean = new PageBean(currPage, totalRecords, pageSize);
		
		
		// ��ѯ��ǰҳ�ļ�¼
		List<SaleVisit> records = saleVisitDao.findByPage(detachedCriteria, pageBean.getStartIndex(), pageSize);
		
		pageBean.setRecords(records);
		pageBean.setUrl("");
		
		return pageBean;
	}

	/**
	 * ����ͻ����ʼ�¼
	 */
	public void save(SaleVisit saleVisit) {

		saleVisitDao.save(saleVisit);
	}

	/**
	 * �޸Ŀͻ������¼
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
