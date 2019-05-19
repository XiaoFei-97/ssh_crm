package com.jzfblog.crm.web.interceptor;

import com.jzfblog.crm.domain.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class PrivilegeInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// 1.判断sesion中是否存在登录该用户的信息
		User existUser = (User) ActionContext.getContext().getSession().get("existUser");
		if(existUser == null) {
			// 没有登录，返回错误信息，跳转到登录页面
			ActionSupport actionSupport = (ActionSupport) invocation.getAction();
			actionSupport.addActionError("您尚未登录！没有访问权限");
			return actionSupport.LOGIN;
		}else {
			// 已经登录,则跳到下一个拦截器即可
			return invocation.invoke();
		}
	}
	
}
