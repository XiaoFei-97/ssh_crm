package com.jzfblog.crm.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.jzfblog.crm.dao.UserDao;
import com.jzfblog.crm.domain.User;

/**
 * 鐢ㄦ埛绠＄悊鐨凞AO鐨勫疄鐜扮被
 * 
 * @author jt
 *
 */
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	
	// DAO涓牴鎹敤鎴峰悕鍜屽瘑鐮佽繘琛屾煡璇㈢殑鏂规硶:
	public User login(User user) {
		List<User> list = (List<User>) this.getHibernateTemplate().find(
				"from User where user_code=? and user_password = ?", user.getUser_code(), user.getUser_password());
		// 鍒ゆ柇涓�涓�:
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
