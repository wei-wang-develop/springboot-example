package com.wei.springbootMongodbRedis.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.wei.springbootMongodbRedis.dao.impl.UserDaoImpl;

import redis.clients.jedis.JedisPoolConfig;


/**
 * Redis Configuration class.
 * 
 * @author Wei WANG
 */
@Configuration
@EnableCaching
public class RedisConfig {

	private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

	
	@Value("${spring.redis.database}")
	private int databaseIndex;
	@Value("${spring.redis.host}")      //获取application.yml文件中名为host的value值
	private String host;
	@Value("${spring.redis.port}")      //获取application.yml文件中名为port的value值，并且自动完成数据类型转换
	private int port;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.timeout}")
	private int timeout;
	
	// Redis pool
	@Value("${spring.redis.pool.max-active}")
	private int poolMaxActive;
	@Value("${spring.redis.pool.max-wait}")
	private int poolMaxWait;
	@Value("${spring.redis.pool.max-idle}")
	private int poolMaxIdle;
	@Value("${spring.redis.pool.min-idle}")
	private int poolMinIdle;
	
	
	
	/**
	 * Redis Connection.
	 * 
	 * @return
	 */
	@Bean    
	public JedisConnectionFactory redisConnectionFactory() {    
	        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();    
	        connectionFactory.setDatabase(databaseIndex);
	        connectionFactory.setHostName(host);    
	        connectionFactory.setPort(port);   
	        connectionFactory.setPassword(password);  
	        connectionFactory.setTimeout(timeout); //设置连接超时时间  
	        connectionFactory.setPoolConfig(poolConfig(poolMaxIdle,poolMinIdle,poolMaxActive,poolMaxWait,true));
	        System.out.println("Redis Configurtion ============ " + host+":"+port);
	        return connectionFactory;    
	} 
	
	/**
	 * Spring Redis pool configuration.
	 * @param poolMaxIdle
	 * @param poolMinIdle
	 * @param maxWaitMillis
	 * @param poolMaxWait
	 * @param testOnBorrow
	 * @return
	 */
	private JedisPoolConfig poolConfig(int poolMaxIdle, int poolMinIdle,int maxWaitMillis,long poolMaxWait,boolean testOnBorrow){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(poolMaxIdle);
		poolConfig.setMinIdle(poolMinIdle);
		poolConfig.setMaxWaitMillis(maxWaitMillis);
		poolConfig.setTestOnBorrow(testOnBorrow);
		return poolConfig;
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
		JedisConnectionFactory connectionFactory = redisConnectionFactory();
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		if(connectionFactory == null){
			logger.info("ERROR: Redis Template Service is not available -> -> redisConnectionFactory is null");
			return null;
		}
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new RedisObjectSerializer());
		template.afterPropertiesSet();
		return template;
	}
	
	@Bean
	public StringRedisTemplate redisTemplate(){
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory());
		return template;
	}

	
	
	
	/*
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(){
		return new JedisConnectionFactory();
	}
	
	@Bean
	public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory factory){
		RedisTemplate<String, User> template = new RedisTemplate<String, User>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new RedisObjectSerializer());
		return template;
	}
	*/
}
