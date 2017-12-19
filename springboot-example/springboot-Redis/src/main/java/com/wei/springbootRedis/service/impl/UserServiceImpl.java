package com.wei.springbootRedis.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wei.springbootRedis.domain.User;
import com.wei.springbootRedis.service.UserService;

@Repository
public class UserServiceImpl implements UserService{

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public void add(String key, Long timeout, User user) {
		Gson gson = new Gson();
		redisTemplate.opsForValue().set(key, gson.toJson(user), timeout, TimeUnit.MINUTES);
		
	}

	@Override
	public void add(String key, Long timeout, List<User> uers) {
		Gson gson = new Gson();
		redisTemplate.opsForValue().set(key, gson.toJson(uers), timeout, TimeUnit.MINUTES);
	}

	@Override
	public User get(String key) {
		Gson gson = new Gson();
		User user = null;
		String userJson = redisTemplate.opsForValue().get(key);
		if(!StringUtils.isEmpty(userJson)){
			user = gson.fromJson(userJson, User.class);
		}
		return user;
	}

	@Override
	public List<User> getList(String key) {
		Gson gson = new Gson();
		List<User> userList = null;
		String userListJson = redisTemplate.opsForValue().get(key);
		if(!StringUtils.isEmpty(userListJson)){
			//userList = gson.fromJson(userListJson, User.class);
			userList = gson.fromJson(userListJson, new TypeToken<List<User>>() {}.getType());
		}
		return userList;
	}

	@Override
	public void delete(String key) {
		redisTemplate.opsForValue().getOperations().delete(key);
	}

}
