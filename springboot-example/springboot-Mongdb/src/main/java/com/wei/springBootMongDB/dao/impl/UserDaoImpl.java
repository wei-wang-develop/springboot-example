package com.wei.springBootMongDB.dao.impl;

import org.springframework.stereotype.Repository;

import com.wei.springBootMongDB.dao.UserDao;
import com.wei.springBootMongDB.domain.User;

/**
 * {@link UserDao} implementation extends BaseDao implementation.
 * @author lenovo
 *
 */
@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

}
