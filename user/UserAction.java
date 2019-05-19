package com.jzfblog.crm.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.jzfblog.crm.domain.User;
import com.jzfblog.crm.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * �û�����Action��
 * @author �����
 *
 */
public class UserAction extends ActionSupport implements ModelDriven<User> {
	
	// ģ������
	private User user = new User();
	
	public User getModel() {
		return user;
	}
	
	// ����ע��
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * �û�ע��
	 */
	public String register() {
		
		userService.register(user);
		return LOGIN;
	}
	
	/**
	 * �û���¼
	 */
	public String login() {

		User existUser = userService.login(user);
		
		if(existUser == null) {
			// ��¼ʧ��
			// ��Ӵ�����Ϣ
			this.addActionError("�û������������");
			return LOGIN;
		}else {
			// ��¼�ɹ�����existUser����session��
			// ����һ��ʹ��ServletActionContext
			// ServletActionContext.getRequest().getSession().setAttribute("existUser", existUser);
			// ��������ʹ��ActionContext
			ActionContext.getContext().getSession().put("existUser", existUser);
			return SUCCESS;
		}
	}
	
	public String findAllUser() throws IOException {
		
		List<User> list = userService.findAllUser();
		
		// ��listת����json
		JSONArray jsonArray = JSONArray.fromObject(list);
		
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
		return NONE;
	}
}
