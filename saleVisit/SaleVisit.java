package com.jzfblog.crm.domain;

import java.util.Date;

/**
 * 客户拜访记录管理
 * @author 蒋振飞
 *
 */
public class SaleVisit {
	
	private String visit_id;
	private Date visit_time;
	private String visit_interviewee;
	private String visit_addr;
	private String visit_detail;
	private Date visit_nexttime;
	
	private Customer customer;
	private User user;
	
	public SaleVisit() {}

	public SaleVisit(String visit_id, Date visit_time, String visit_interviewee, String visit_addr, String visit_detail,
			Date visit_nexttime, Customer customer, User user) {
		super();
		this.visit_id = visit_id;
		this.visit_time = visit_time;
		this.visit_interviewee = visit_interviewee;
		this.visit_addr = visit_addr;
		this.visit_detail = visit_detail;
		this.visit_nexttime = visit_nexttime;
		this.customer = customer;
		this.user = user;
	}

	public String getVisit_id() {
		return visit_id;
	}

	public void setVisit_id(String visit_id) {
		this.visit_id = visit_id;
	}

	public Date getVisit_time() {
		return visit_time;
	}

	public void setVisit_time(Date visit_time) {
		this.visit_time = visit_time;
	}

	public String getVisit_interviewee() {
		return visit_interviewee;
	}

	public void setVisit_interviewee(String visit_interviewee) {
		this.visit_interviewee = visit_interviewee;
	}

	public String getVisit_addr() {
		return visit_addr;
	}

	public void setVisit_addr(String visit_addr) {
		this.visit_addr = visit_addr;
	}

	public String getVisit_detail() {
		return visit_detail;
	}

	public void setVisit_detail(String visit_detail) {
		this.visit_detail = visit_detail;
	}

	public Date getVisit_nexttime() {
		return visit_nexttime;
	}

	public void setVisit_nexttime(Date visit_nexttime) {
		this.visit_nexttime = visit_nexttime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
