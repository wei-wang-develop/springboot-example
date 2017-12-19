package com.wei.springbootRedis.service;

import java.util.List;

import com.wei.springbootRedis.domain.User;

public interface UserService {

	void add(String key, Long timeOut, User user);
	void add(String key, Long timeOut, List<User> uers);
	
	User get(String key);
	List<User> getList(String key);
	
	void delete(String key);
}
