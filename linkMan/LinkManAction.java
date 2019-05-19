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

	// ģ������
	private LinkMan linkMan = new LinkMan();
	
	public LinkMan getModel() {
		return linkMan;
	}
	
	// ����ע��
	private CustomerService customerService;
	private LinkManService linkManService;
	
	public void setLinkManService(LinkManService linkManService) {
		this.linkManService = linkManService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	// �����������յ�ǰҳ
	private Integer currentPageNum = 1;

	public void setCurrentPageNum(Integer currentPageNum) {
		if(currentPageNum == null) {
			currentPageNum = 1;
		}
		this.currentPageNum = currentPageNum;
	}
	
	// ������������ÿҳ����¼��
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
	 * ��ҳ��ѯȫ����ϵ��
	 * @return
	 */
	public String findAll() {
		
		// ���ʹ��DetachedCriterial����������ѯ��
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LinkMan.class);
		
		// ��������
		if(linkMan.getLkm_name() != null) {
			// ���ð����Ʋ�ѯ����
			detachedCriteria.add(Restrictions.like("lkm_name", "%"+linkMan.getLkm_name()+"%"));
		}
		if(linkMan.getLkm_gender() != null && !"".equals(linkMan.getLkm_gender())) {
			// ���ð��Ա��ѯ
			detachedCriteria.add(Restrictions.eq("lkm_gender", linkMan.getLkm_gender()));
		}
		// ����ҵ����ѯ
		PageBean pageBean = linkManService.findByPage(detachedCriteria, currentPageNum, pageSize);
		
		// ��pageBean����ֵջ��
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}
	
	/*
	 * ����ͻ���ϵ��
	 */
	public String save() {
		
		linkManService.save(linkMan);
		return SUCCESS;
	}
	
	/**
	 * �޸���ϵ����Ϣ
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