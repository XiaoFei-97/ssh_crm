package com.jzfblog.crm.web.action;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.jzfblog.crm.domain.Customer;
import com.jzfblog.crm.domain.LinkMan;
import com.jzfblog.crm.domain.PageBean;
import com.jzfblog.crm.service.CustomerService;
import com.jzfblog.crm.service.LinkManService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LinkManAction extends ActionSupport implements ModelDriven<LinkMan>{

	// 模型驱动
	private LinkMan linkMan = new LinkMan();
	
	public LinkMan getModel() {
		return linkMan;
	}
	
	// 属性注入
	private CustomerService customerService;
	private LinkManService linkManService;
	
	public void setLinkManService(LinkManService linkManService) {
		this.linkManService = linkManService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	// 属性驱动接收当前页
	private Integer currentPageNum = 1;

	public void setCurrentPageNum(Integer currentPageNum) {
		if(currentPageNum == null) {
			currentPageNum = 1;
		}
		this.currentPageNum = currentPageNum;
	}
	
	// 属性驱动接收每页最大记录数
	private Integer pageSize = 3;

	public void setPageSize(Integer pageSize) {
		if(pageSize == null) {
			pageSize = 5;
		}
		this.pageSize = pageSize;
	}
	
	public String saveUI() {
		
		List<Customer> list = customerService.findAll();
		ActionContext.getContext().getValueStack().set("list", list);;
		return "saveUI";
	}
	
	/**
	 * 分页查询全部联系人
	 * @return
	 */
	public String findAll() {
		
		// 最好使用DetachedCriterial对象（条件查询）
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LinkMan.class);
		
		// 设置条件
		if(linkMan.getLkm_name() != null) {
			// 设置按名称查询条件
			detachedCriteria.add(Restrictions.like("lkm_name", "%"+linkMan.getLkm_name()+"%"));
		}
		if(linkMan.getLkm_gender() != null && !"".equals(linkMan.getLkm_gender())) {
			// 设置按性别查询
			detachedCriteria.add(Restrictions.eq("lkm_gender", linkMan.getLkm_gender()));
		}
		// 调用业务层查询
		PageBean pageBean = linkManService.findByPage(detachedCriteria, currentPageNum, pageSize);
		
		// 将pageBean放入值栈中
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}
	
	/*
	 * 保存客户联系人
	 */
	public String save() {
		
		linkManService.save(linkMan);
		return SUCCESS;
	}
	
	/**
	 * 修改联系人信息
	 */
	public String edit() {
		
		linkMan = linkManService.findById(linkMan);
		List<Customer> list = customerService.findAll();
		ActionContext.getContext().getValueStack().set("list", list);
		
		return "edit";
	}
	
	public String update() {
		
		linkManService.update(linkMan);
		return SUCCESS;
	}
	 
	public String delete() {
		linkManService.delete(linkMan);
		return SUCCESS;
	}
}