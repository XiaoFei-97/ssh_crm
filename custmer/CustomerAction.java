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

	// 模型驱动
	private Customer customer = new Customer();
	
	public Customer getModel() {
		return customer;
	}
	
	// 属性注入
	private CustomerService customerService;

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	// Struts2的文件上传需要在Action中提供三个属性
	private String uploadFileName; // 上传的文件名称，而且注意"upload"必须与表单中的name一致
	private String uploadContentType; // 上传的文件类型
	private File upload; // 上传的文件

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setUpload(File upload) {
		this.upload = upload;
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

	/**
	 * 跳转保存客户页面
	 */
	public String saveUI(){
				
		return "saveUI";
	}

	/**
	 * 保存客户
	 * @throws IOException 
	 */
	public String save() throws IOException{
		
		// 上传图片
		if(upload != null) {
			// 设置文件上传路径
			String path = "D:/upload";
			
			// 为了避免上传文件名相同，所以采用随机文件名
			String uuidFileName = UploadUtils.getUuidFileName(uploadFileName);
			// 一个目录存放文件过多，目录分离
			String realPath = UploadUtils.getPath(uuidFileName);
			// 创建目录
			String dir = path + realPath;
			File file = new File(dir);
			if(!file.exists()) {
				file.mkdirs();
			}
			// 文件上传
			File destFile = new File(dir+"/"+uuidFileName);
			FileUtils.copyFile(upload, destFile);
			
			// 设置cust_image属性值
			customer.setCust_image(dir+"/"+uuidFileName);
		}
		
		// 保存客户
		customerService.save(customer);
			
		return SUCCESS; 
	}
	
	/**
	 * 删除客户
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
	 * 修改用户回显
	 */
	public String edit() {
		// 根据id查询，跳转页面，回显数据
		customer = customerService.findById(customer);
		
		// 将customer传递到页面：两种方式
		// 一：手动压栈,<s:property value="cust_name" />,<s:propetry name="cust_name" value="" />
		// 二：模型驱动对象，默认在栈里，<s:propetry value="model.cust_name" />
		ActionContext.getContext().getValueStack().push(customer);
		return "edit";
	}
	
	/**
	 * 修改用户
	 * @throws IOException 
	 */
	public String update() throws IOException {
		String cust_image = customer.getCust_image();
		// 文件项是否已经选择，如果选择了就删除原有文件，没选就使用原有文件
		if(upload != null && !"".equals(cust_image)) {
			File oldFile = new File(cust_image);
			if(oldFile.exists()) {
				oldFile.delete();
			}
			
			// 设置文件上传路径
			String path = "D:/upload";
			
			// 为了避免上传文件名相同，所以采用随机文件名
			String uuidFileName = UploadUtils.getUuidFileName(uploadFileName);
			// 一个目录存放文件过多，目录分离
			String realPath = UploadUtils.getPath(uuidFileName);
			// 创建目录
			String dir = path + realPath;
			File file = new File(dir);
			if(!file.exists()) {
				file.mkdirs();
			}
			// 文件上传
			File destFile = new File(dir+"/"+uuidFileName);
			FileUtils.copyFile(upload, destFile);
			
			// 设置cust_image属性值
			customer.setCust_image(dir+"/"+uuidFileName);
		}
		customerService.update(customer);
		
		return SUCCESS;
	}
	
	/**
	 * 客户查询
	 */
	public String findAll() {
		// 接收参数：分页参数
		// 最好使用DetachedCriteria对象（条件查询--带分页）
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
		// 设置条件：（在web层设置条件）
		if (customer.getCust_name() != null) {
			// 输入名称:
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
		// 调用业务层查询:
		PageBean pageBean = customerService.findByPage(detachedCriteria, currentPageNum, pageSize);
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}

	public String findAllCustomer() throws IOException {
		
		List<Customer> list = customerService.findAll();
		
		// 将list转成json
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] {"linkMans","visits","baseDictSource","baseDictLevel","baseDictIndustry"});
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		// 需要将json打印到页面
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8"); 
		ServletActionContext.getResponse().getWriter().println(jsonArray.toString()); 
		return NONE;
	}
	

}
