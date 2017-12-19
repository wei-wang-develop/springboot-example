package com.wei.springbootMongodbRedis.dao.impl;

import org.springframework.stereotype.Repository;

import com.wei.springbootMongodbRedis.dao.UserDao;
import com.wei.springbootMongodbRedis.domain.User;



/**
 * {@link UserDao} implementation extends BaseDao implementation.
 * 
 * @author Wei WANG
 *
 */
@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

}
