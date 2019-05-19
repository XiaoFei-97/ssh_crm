package com.jzfblog.crm.dao;

import com.jzfblog.crm.domain.User;

/**
 * 鐢ㄦ埛绠＄悊鐨凞AO鐨勬帴鍙�
 * @author jt
 *
 */
public interface UserDao extends BaseDao<User>{

	User login(User user);

}
