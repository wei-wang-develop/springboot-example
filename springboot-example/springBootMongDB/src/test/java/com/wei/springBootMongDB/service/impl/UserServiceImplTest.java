package com.wei.springBootMongDB.service.impl;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import com.wei.springBootMongDB.domain.Email;
import com.wei.springBootMongDB.domain.User;
import com.wei.springBootMongDB.service.UserService;

import junit.framework.Assert;

/**
 * Test {@link UserService} implementation using extends {@link BaseDao} implementation.
 * @author Wei WANG
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void saveTest(){
		User user1 = new User();
		user1.setUserName("AnthonyWANG");
		user1.setPassWord("AnthonyWANG_PASSWORD");
		user1.setAge(20);
		user1.setBirthday("1997-12-21");
		user1.setEmailAddress(new Email("AnthonyWANG@gmail.com"));
		userService.save(user1);
	}
	
	@Test
	public void findAllTest(){
		userService.findAll().stream()
					.forEach(user -> System.out.println(user.toString()));
	}
	
	@Test
	public void findOneTest(){
		Query query = new Query(Criteria.where("userName").is("AnthonyWANG"));
		Optional<User> actual = Optional.ofNullable(userService.findOne(query));
		
		String expected = "AnthonyWANG";
		Assert.assertEquals(expected, actual.get().getUserName());
	}
	
	@SuppressWarnings("deprecation")
	@Test public void findOneByEmailTest(){
		Query query = new Query(Criteria.where("email").is("user@gmail.com"));
		Optional<User> actual = Optional.ofNullable(userService.findOne(query));
		
		String expected = "user@gmail.com";
		System.out.println(userService.findOne(query).getUserName());
		Assert.assertEquals(expected, actual.get().getEmailAddress().getEmail());
	}
	
	@Test
	public void modifyTest(){
		//expected object:
		Query query1 = new Query(Criteria.where("userName").is("AnthonyWANG"));
		Optional<User> expected_obj = Optional.ofNullable(userService.findOne(query1));
		expected_obj.get().setPassWord("password_new");
		userService.update(expected_obj.get());
		
		//actual object:
		Query query2 = new Query(Criteria.where("userName").is("AnthonyWANG"));
		Optional<User> actual_obj = Optional.ofNullable(userService.findOne(query2));
		
		Assert.assertEquals(expected_obj.get().getPassWord(), actual_obj.get().getPassWord());
	}
	
	@Test
	public void deleteTest(){
		Query query = new Query(Criteria.where("userName").is("AnthonyWANG"));
		userService.remove(query);
	}
	
	@Test
	public void deleteAllTest(){
		userService.removeAll();
	}

}
