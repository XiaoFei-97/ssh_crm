package com.jzfblog.crm.web.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.jzfblog.crm.domain.Customer;
import com.jzfblog.crm.domain.PageBean;
import com.jzfblog.crm.service.CustomerService;
import com.jzfblog.crm.utils.UploadUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{

	// ģ������
	private Customer customer = new Customer();
	
	public Customer getModel() {
		return customer;
	}
	
	// ����ע��
	private CustomerService customerService;

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	// Struts2���ļ��ϴ���Ҫ��Action���ṩ��������
	private String uploadFileName; // �ϴ����ļ����ƣ�����ע��"upload"��������е�nameһ��
	private String uploadContentType; // �ϴ����ļ�����
	private File upload; // �ϴ����ļ�

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setUpload(File upload) {
		this.upload = upload;
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

	/**
	 * ��ת����ͻ�ҳ��
	 */
	public String saveUI(){
				
		return "saveUI";
	}

	/**
	 * ����ͻ�
	 * @throws IOException 
	 */
	public String save() throws IOException{
		
		// �ϴ�ͼƬ
		if(upload != null) {
			// �����ļ��ϴ�·��
			String path = "D:/upload";
			
			// Ϊ�˱����ϴ��ļ�����ͬ�����Բ�������ļ���
			String uuidFileName = UploadUtils.getUuidFileName(uploadFileName);
			// һ��Ŀ¼����ļ����࣬Ŀ¼����
			String realPath = UploadUtils.getPath(uuidFileName);
			// ����Ŀ¼
			String dir = path + realPath;
			File file = new File(dir);
			if(!file.exists()) {
				file.mkdirs();
			}
			// �ļ��ϴ�
			File destFile = new File(dir+"/"+uuidFileName);
			FileUtils.copyFile(upload, destFile);
			
			// ����cust_image����ֵ
			customer.setCust_image(dir+"/"+uuidFileName);
		}
		
		// ����ͻ�
		customerService.save(customer);
			
		return SUCCESS; 
	}
	
	/**
	 * ɾ���ͻ�
	 */
	public String delete() {
		
		customer = customerService.findById(customer);
		
		String cust_image = customer.getCust_image();
		if(cust_image != null && !"".equals(cust_image)) {
			File file = new File(cust_image);
			if(file.exists()) {
				file.delete();
			}
		}
		customerService.delete(customer);
		return SUCCESS;
	}
	
	/**
	 * �޸��û�����
	 */
	public String edit() {
		// ����id��ѯ����תҳ�棬��������
		customer = customerService.findById(customer);
		
		// ��customer���ݵ�ҳ�棺���ַ�ʽ
		// һ���ֶ�ѹջ,<s:property value="cust_name" />,<s:propetry name="cust_name" value="" />
		// ����ģ����������Ĭ����ջ�<s:propetry value="model.cust_name" />
		ActionContext.getContext().getValueStack().push(customer);
		return "edit";
	}
	
	/**
	 * �޸��û�
	 * @throws IOException 
	 */
	public String update() throws IOException {
		String cust_image = customer.getCust_image();
		// �ļ����Ƿ��Ѿ�ѡ�����ѡ���˾�ɾ��ԭ���ļ���ûѡ��ʹ��ԭ���ļ�
		if(upload != null && !"".equals(cust_image)) {
			File oldFile = new File(cust_image);
			if(oldFile.exists()) {
				oldFile.delete();
			}
			
			// �����ļ��ϴ�·��
			String path = "D:/upload";
			
			// Ϊ�˱����ϴ��ļ�����ͬ�����Բ�������ļ���
			String uuidFileName = UploadUtils.getUuidFileName(uploadFileName);
			// һ��Ŀ¼����ļ����࣬Ŀ¼����
			String realPath = UploadUtils.getPath(uuidFileName);
			// ����Ŀ¼
			String dir = path + realPath;
			File file = new File(dir);
			if(!file.exists()) {
				file.mkdirs();
			}
			// �ļ��ϴ�
			File destFile = new File(dir+"/"+uuidFileName);
			FileUtils.copyFile(upload, destFile);
			
			// ����cust_image����ֵ
			customer.setCust_image(dir+"/"+uuidFileName);
		}
		customerService.update(customer);
		
		return SUCCESS;
	}
	
	/**
	 * �ͻ���ѯ
	 */
	public String findAll() {
		// ���ղ�������ҳ����
		// ���ʹ��DetachedCriteria����������ѯ--����ҳ��
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
		// ��������������web������������
		if (customer.getCust_name() != null) {
			// ��������:
			detachedCriteria.add(Restrictions.like("cust_name", "%" + customer.getCust_name() + "%"));
		}
		if (customer.getBaseDictSource() != null) {
			if (customer.getBaseDictSource().getDict_id() != null
					&& !"".equals(customer.getBaseDictSource().getDict_id())) {
				detachedCriteria
						.add(Restrictions.eq("baseDictSource.dict_id", customer.getBaseDictSource().getDict_id()));
			}

		}
		if (customer.getBaseDictLevel() != null) {
			if (customer.getBaseDictLevel().getDict_id() != null
					&& !"".equals(customer.getBaseDictLevel().getDict_id())) {
				detachedCriteria
						.add(Restrictions.eq("baseDictLevel.dict_id", customer.getBaseDictLevel().getDict_id()));
			}
		}
		if (customer.getBaseDictIndustry() != null && customer.getBaseDictIndustry().getDict_id() != null) {
			if (customer.getBaseDictIndustry().getDict_id() != null
					&& !"".equals(customer.getBaseDictIndustry().getDict_id())) {
				detachedCriteria
						.add(Restrictions.eq("baseDictIndustry.dict_id", customer.getBaseDictIndustry().getDict_id()));
			}
		}
		// ����ҵ����ѯ:
		PageBean pageBean = customerService.findByPage(detachedCriteria, currentPageNum, pageSize);
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}

	public String findAllCustomer() throws IOException {
		
		List<Customer> list = customerService.findAll();
		
		// ��listת��json
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] {"linkMans","visits","baseDictSource","baseDictLevel","baseDictIndustry"});
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		// ��Ҫ��json��ӡ��ҳ��
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8"); 
		ServletActionContext.getResponse().getWriter().println(jsonArray.toString()); 
		return NONE;
	}
	

}
