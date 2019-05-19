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
 * 客户拜访记录的action类
 * @author 蒋振飞
 *
 */
public class SaleVisitAction extends ActionSupport implements ModelDriven<SaleVisit> {

	// 模型驱动
	private SaleVisit saleVisit = new SaleVisit();
	
	public SaleVisit getModel() {
		return saleVisit;
	}
	
	// service层属性注入
	private SaleVisitService saleVisitService;

	public void setSaleVisitService(SaleVisitService saleVisitService) {
		this.saleVisitService = saleVisitService;
	}
	
	// 接收分页数据
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
	
	// 接收数据
	private Date visit_end_time;

	public void setVisit_end_time(Date visit_end_time) {
		this.visit_end_time = visit_end_time;
	}
	
	public Date getVisit_end_time() {
		return visit_end_time;
	}

	/**
	 * 查询拜访记录列表
	 * @return
	 */
	public String findAll() {
		
		// 创建离线条件查询
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SaleVisit.class);
		
		// 设置条件：
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
	 * 跳转保存客户访问管理记录
	 * @return
	 */
	public String saveUI() {
		
		return "saveUI";
	}
	
	/**
	 * 保存客户访问记录
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
