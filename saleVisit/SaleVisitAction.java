package com.jzfblog.crm.web.action;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.jzfblog.crm.domain.PageBean;
import com.jzfblog.crm.domain.SaleVisit;
import com.jzfblog.crm.service.SaleVisitService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * �ͻ��ݷü�¼��action��
 * @author �����
 *
 */
public class SaleVisitAction extends ActionSupport implements ModelDriven<SaleVisit> {

	// ģ������
	private SaleVisit saleVisit = new SaleVisit();
	
	public SaleVisit getModel() {
		return saleVisit;
	}
	
	// service������ע��
	private SaleVisitService saleVisitService;

	public void setSaleVisitService(SaleVisitService saleVisitService) {
		this.saleVisitService = saleVisitService;
	}
	
	// ���շ�ҳ����
	private Integer currPage = 1;
	private Integer pageSize = 3;
	
	public void setCurrPage(Integer currPage) {
		if(currPage == null) {
			currPage = 1;
		}
		this.currPage = currPage;
	}

	public void setPageSize(Integer pageSize) {
		if(pageSize == null) {
			pageSize = 3;
		}
		this.pageSize = pageSize;
	}
	
	// ��������
	private Date visit_end_time;

	public void setVisit_end_time(Date visit_end_time) {
		this.visit_end_time = visit_end_time;
	}
	
	public Date getVisit_end_time() {
		return visit_end_time;
	}

	/**
	 * ��ѯ�ݷü�¼�б�
	 * @return
	 */
	public String findAll() {
		
		// ��������������ѯ
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SaleVisit.class);
		
		// ����������
		if(saleVisit.getVisit_time() != null) {
			detachedCriteria.add(Restrictions.ge("visit_time", saleVisit.getVisit_time()));
		}
		if(visit_end_time != null) {
			detachedCriteria.add(Restrictions.le("visit_end_time", visit_end_time));
		}
		PageBean pageBean = saleVisitService.findByPage(detachedCriteria, currPage, pageSize);
		
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}
	
	/**
	 * ��ת����ͻ����ʹ����¼
	 * @return
	 */
	public String saveUI() {
		
		return "saveUI";
	}
	
	/**
	 * ����ͻ����ʼ�¼
	 * @return
	 */
	public String save() {
		
		saleVisitService.save(saleVisit);
		return SUCCESS;
	}
	
	public String edit() {
		saleVisit = saleVisitService.findById(saleVisit);
		ActionContext.getContext().getValueStack().push(saleVisit);
		return "edit";
	}
	
	public String delete() {
		
		saleVisit = saleVisitService.findById(saleVisit);
		saleVisitService.delete(saleVisit);
		return SUCCESS;
	}
	
	public String update() {
		
		saleVisitService.update(saleVisit);
		return SUCCESS;
	}
}
