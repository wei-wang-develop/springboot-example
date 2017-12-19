package com.wei.springbootMongodbRedis.service.impl;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.wei.springbootMongodbRedis.domain.Email;
import com.wei.springbootMongodbRedis.domain.User;
import com.wei.springbootMongodbRedis.service.UserService;

import junit.framework.Assert;

/**
 * Test {@link UserService} implementation.
 * 
 * @author Wei WANG
 */
@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	
	@Test public void saveTest(){
		User user1 = new User();
		user1.setUserName("AnthonyWANG");
		user1.setPassWord("AnthonyWANG_PASSWORD");
		user1.setAge(50);
		user1.setBirthday("1997-12-21");
		user1.setEmailAddress(new Email("AnthonyWANG@gmail.com"));
		userService.save(user1);
	}
	
	@Test public void findByIdTest(){
		ObjectId id = new ObjectId("5a239d61312aa80ccc294562"); 
		User userRedis = userService.findById(id);
		System.out.println("findByIdTest: user:"+userRedis.toString());
	}
	
	
	@Test public void test() throws Exception{
		//创建 3个user对象
		User user1 = new User();
		user1.setUserName("AnthonyWANG-1");
		user1.setPassWord("AnthonyWANG_PASSWORD-1");
		user1.setAge(30);
		user1.setBirthday("1997-12-21");
		user1.setEmailAddress(new Email("AnthonyWANG-1@gmail.com"));
		
		User user2 = new User();
		user2.setUserName("AnthonyWANG-2");
		user2.setPassWord("AnthonyWANG_PASSWORD-2");
		user2.setAge(30);
		user2.setBirthday("1997-12-21");
		user2.setEmailAddress(new Email("AnthonyWANG-2@gmail.com"));
		
		User user3 = new User();
		user3.setUserName("AnthonyWANG-3");
		user3.setPassWord("AnthonyWANG_PASSWORD-3");
		user3.setAge(30);
		user3.setBirthday("1997-12-21");
		user3.setEmailAddress(new Email("AnthonyWANG-3@gmail.com"));
		
		//保存对象
		redisTemplate.opsForValue().set(user1.getUserName(), user1);
		redisTemplate.opsForValue().set(user2.getUserName(), user2);
		redisTemplate.opsForValue().set(user3.getUserName(), user3);
		
		Assert.assertEquals("AnthonyWANG-1@gmail.com", redisTemplate.opsForValue().get("AnthonyWANG-1").getEmailAddress());
		Assert.assertEquals("AnthonyWANG-2@gmail.com", redisTemplate.opsForValue().get("AnthonyWANG-2").getEmailAddress());
		Assert.assertEquals("AnthonyWANG-3@gmail.com", redisTemplate.opsForValue().get("AnthonyWANG-3").getEmailAddress());
	}
	
	
}
