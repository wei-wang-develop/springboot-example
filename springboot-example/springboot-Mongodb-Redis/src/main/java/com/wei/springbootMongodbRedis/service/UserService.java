package com.wei.springbootMongodbRedis.service;

import java.util.List;

import org.bson.types.ObjectId;

import com.wei.springbootMongodbRedis.domain.User;

/**
 * User 业务逻辑接口类.
 * 
 * @author Wei WANG
 */
public interface UserService {

	User findById(ObjectId id);
	
	List<User> findAll();
	
	void save(User user);
	
	void update(User user);
	
	void remove(ObjectId id);
}
