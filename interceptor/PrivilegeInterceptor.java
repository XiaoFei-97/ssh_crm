package com.jzfblog.crm.web.interceptor;

import com.jzfblog.crm.domain.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class PrivilegeInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// 1.�ж�sesion���Ƿ���ڵ�¼���û�����Ϣ
		User existUser = (User) ActionContext.getContext().getSession().get("existUser");
		if(existUser == null) {
			// û�е�¼�����ش�����Ϣ����ת����¼ҳ��
			ActionSupport actionSupport = (ActionSupport) invocation.getAction();
			actionSupport.addActionError("����δ��¼��û�з���Ȩ��");
			return actionSupport.LOGIN;
		}else {
			// �Ѿ���¼,��������һ������������
			return invocation.invoke();
		}
	}
	
}
