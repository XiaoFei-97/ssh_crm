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
	 * 业务层注册用户
	 */
	public void register(User user) {
		// 对用户密码进行加密
		String user_password = MD5Utils.md5(user.getUser_password()); 
		user.setUser_password(user_password);
		
		// 修改用户状态
		user.setUser_state("1");
		userDao.save(user);
	}

	/**
	 * 业务层用户登录
	 */
	public User login(User user) {
		
		// 将客户端提交上来的密码进行加密，再与数据库比对
		user.setUser_password(MD5Utils.md5(user.getUser_password()));
		return userDao.findById(user.getUser_id());
	}

	public List<User> findAllUser() {

		return userDao.findAll();
	}

}
