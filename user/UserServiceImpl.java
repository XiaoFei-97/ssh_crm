package com.jzfblog.crm.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jzfblog.crm.dao.UserDao;
import com.jzfblog.crm.domain.User;
import com.jzfblog.crm.service.UserService;
import com.jzfblog.crm.utils.MD5Utils;

@Transactional
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * ҵ���ע���û�
	 */
	public void register(User user) {
		// ���û�������м���
		String user_password = MD5Utils.md5(user.getUser_password()); 
		user.setUser_password(user_password);
		
		// �޸��û�״̬
		user.setUser_state("1");
		userDao.save(user);
	}

	/**
	 * ҵ����û���¼
	 */
	public User login(User user) {
		
		// ���ͻ����ύ������������м��ܣ��������ݿ�ȶ�
		user.setUser_password(MD5Utils.md5(user.getUser_password()));
		return userDao.findById(user.getUser_id());
	}

	public List<User> findAllUser() {

		return userDao.findAll();
	}

}
