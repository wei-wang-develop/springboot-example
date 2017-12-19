package com.wei.springbootMongodbRedis.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.wei.springbootMongodbRedis.dao.UserDao;
import com.wei.springbootMongodbRedis.dao.impl.UserDaoImpl;
import com.wei.springbootMongodbRedis.domain.User;
import com.wei.springbootMongodbRedis.service.UserService;

/**
 * User 业务逻辑实现类
 * @author Wei WANG
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	/**
	 * Redis
	 */
	@Autowired
	private RedisTemplate<String, User> redisTemplate;

	@Autowired
	private UserDao userDao;

	/**
	 * 获取User逻辑： 如果缓存存在，从缓存中获取User信息 . 如果缓存不存在，从 DB 中获取User信息，然后插入缓存.
	 */
	@Override
	public User findById(ObjectId id) {
		String collectionName = "user";
		// 从缓存中获取User信息
		String key = collectionName + "_" + id.toString();
		ValueOperations<String, User> valueOperations = redisTemplate.opsForValue();

		// 如果缓存存在
		if (redisTemplate.hasKey(key)) {
			User user = valueOperations.get(key);
			logger.info("UserServiceImpl.findById():从缓存中获取User信息 >> " + user.toString());
			return user;
		}

		// 从 DB 中获取User信息
		User user = userDao.findById(id, collectionName);

		// 插入缓存
		valueOperations.set(key, user, 10, TimeUnit.SECONDS);
		logger.info("UserServiceImpl.findById(): User 插入缓存 >>  " + user.toString());

		return user;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(User user) {
		userDao.save(user);
	}

	/**
	 * 更新User逻辑： 如果缓存存在，删除. 如果缓存不存在，不操作
	 */
	@Override
	public void update(User user) {
		// 更新 DB 中的 User
		userDao.update(user);

		// 如果缓存存在，则删除缓存
		String key = "user_" + user.getId().toString();
		if (redisTemplate.hasKey(key)) {
			redisTemplate.delete(key);
			logger.info("UserServiceImpl.update(User user) : 从缓存中删除User >>" + user.toString());
		}

	}

	@Override
	public void remove(ObjectId id) {
		// 从DB中删除 User
		userDao.remove(id);

		// 如果缓存存在，则删除缓存
		String key = "user_" + id.toString();
		if (redisTemplate.hasKey(key)) {
			redisTemplate.delete(key);
			logger.info("UserServiceImpl.remove() :  通过User ID, 从缓存中删除User  >>" + id.toString());
		}
	}
}
