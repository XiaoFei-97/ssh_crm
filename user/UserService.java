package com.jzfblog.crm.service;

import java.util.List;

import com.jzfblog.crm.domain.User;

public interface UserService {

	void register(User user);

	User login(User user);

	List<User> findAllUser();

}
