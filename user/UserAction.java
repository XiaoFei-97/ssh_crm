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
 * 用户管理Action类
 * @author 蒋振飞
 *
 */
public class UserAction extends ActionSupport implements ModelDriven<User> {
	
	// 模型驱动
	private User user = new User();
	
	public User getModel() {
		return user;
	}
	
	// 属性注入
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * 用户注册
	 */
	public String register() {
		
		userService.register(user);
		return LOGIN;
	}
	
	/**
	 * 用户登录
	 */
	public String login() {

		User existUser = userService.login(user);
		
		if(existUser == null) {
			// 登录失败
			// 添加错误信息
			this.addActionError("用户名或密码错误");
			return LOGIN;
		}else {
			// 登录成功，将existUser放入session中
			// 方法一：使用ServletActionContext
			// ServletActionContext.getRequest().getSession().setAttribute("existUser", existUser);
			// 方法二：使用ActionContext
			ActionContext.getContext().getSession().put("existUser", existUser);
			return SUCCESS;
		}
	}
	
	public String findAllUser() throws IOException {
		
		List<User> list = userService.findAllUser();
		
		// 将list转化成json
		JSONArray jsonArray = JSONArray.fromObject(list);
		
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
		return NONE;
	}
}
