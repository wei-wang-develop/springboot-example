package com.wei.springbootMongodbRedis.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * RedisClient 工具类.
 * 本类中获取JedisPool调用的是 JedisConnectionFactory 中
 * protected修饰的方法fetchJedisConnector()
 * 所以该类需要与JedisConnectionFactory在同一个package中
 * 
 * @author Wei WANG
 */
public class RedisClient {

	private static Logger logger = LoggerFactory.getLogger(RedisClient.class);
	
	private JedisConnectionFactory  factory;
}
