package com.wei.springBootMongDB.service.impl;

import org.springframework.stereotype.Repository;

import com.wei.springBootMongDB.domain.User;
import com.wei.springBootMongDB.service.UserService;

/**
 * {@link UserService} implementation extends BaseRepository implementation.
 * @author lenovo
 *
 */
@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserService{

}
