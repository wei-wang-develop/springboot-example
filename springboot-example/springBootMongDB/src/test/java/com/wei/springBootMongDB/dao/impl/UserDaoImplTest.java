package com.wei.springBootMongDB.dao.impl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import com.wei.springBootMongDB.dao.UserDao;
import com.wei.springBootMongDB.domain.Department;
import com.wei.springBootMongDB.domain.Email;
import com.wei.springBootMongDB.domain.User;

import junit.framework.Assert;

/**
 * Test {@link UserDao} implementation using extends {@link BaseDao} implementation.
 * 
 * @author Wei WANG
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoImplTest {
	
	private static Logger logger = LoggerFactory.getLogger(UserDaoImplTest.class);
	
	@Autowired
	private UserDao userDao;
	
	
	@Before
	public void setup() {
		userDao.removeAll();  // clean DB data.
		
		Department department = new Department("开发部");
		Set<String> roles = new HashSet<>();
		roles.add("manage");
		User user = new User("userName", "passWord", LocalDate.of(1988, 12, 12), 
								new Email("user@mail.com"), department,roles);
		userDao.save(user);
	}
	
	/*
	@Test
	public void saveTest(){
		User user1 = new User();
		user1.setUserName("AnthonyWANG");
		user1.setPassWord("AnthonyWANG_PASSWORD");
		user1.setBirthday(LocalDate.of(1988, 12, 12));
		user1.setEmailAddress(new Email("AnthonyWANG@gmail.com"));
		userDao.save(user1);
	}
	*/
	
	
	@Test
	public void findAllTest(){
		userDao.findAll().stream()
					.forEach(user -> System.out.println(user.toString()));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void findOneTest(){
		//传递基础类型(int, String, ...)进行查询
		Query query = new Query(Criteria.where("age").is(28)); 
		Optional<User> actual = Optional.ofNullable(userDao.findOne(query));
		
		if(actual.isPresent()) {
			logger.info("=====findOneTest==== user:-> "
					+ "use_id:{}, user_name:{}, user_password:{}, "
					+ "user_age:{}, user_birthday:{}, "
					+ "user_registrationDate:{}, user_email:{}, "
					+ "user_department:{}, "
					+ "user_roles:{}", 
					actual.get().getId(), 
					actual.get().getUserName(),
					actual.get().getPassWord(),
					actual.get().getAge(),
					actual.get().getBirthday(),
					actual.get().getRegistrationDate(),
					actual.get().getEmailAddress(),
					actual.get().getDepartment(),
					actual.get().getRoles());
		}
		
		String expected = "userName";
		Assert.assertEquals(expected, actual.get().getUserName());
	}
	
	@SuppressWarnings("deprecation")
	@Test public void findOneByEmailTest(){
		//传递负责类型(Object: Email, LocalDate, ...)进行查询
		Query query = new Query(Criteria.where("emailAddress").is(new Email("user@mail.com")));
		Optional<User> actual = Optional.ofNullable(userDao.findOne(query));
		
		if(actual.isPresent()) {
			logger.info("===== findOneTest ==== user:-> "
					+ "use_id:{}, user_name:{}, user_password:{}, "
					+ "user_age:{}, user_birthday:{}, "
					+ "user_registrationDate:{}, user_email:{}, "
					+ "user_roles:{}", 
					actual.get().getId(), 
					actual.get().getUserName(),
					actual.get().getPassWord(),
					actual.get().getAge(),
					actual.get().getBirthday(),
					actual.get().getRegistrationDate(),
					actual.get().getEmailAddress(),
					actual.get().getRoles());
		}
		
		String expected = "user@mail.com";
		//System.out.println(userDao.findOne(query).getEmailAddress().getEmail());
		Assert.assertEquals(expected, actual.get().getEmailAddress().getEmail());
	}
	
	@SuppressWarnings("deprecation")
	@Test public void findOneByBirthdayTest(){
		//传递负责类型(Object: Email, LocalDate, ...)进行查询
		Query query = new Query(Criteria.where("birthday").is(LocalDate.of(1988, 12, 12)));
		Optional<User> actual = Optional.ofNullable(userDao.findOne(query));
		
		if(actual.isPresent()) {
			logger.info("===== findOneTest ==== user:-> "
					+ "use_id:{}, user_name:{}, user_password:{}, "
					+ "user_age:{}, user_birthday:{}, "
					+ "user_registrationDate:{}, user_email:{}, "
					+ "user_roles:{}", 
					actual.get().getId(), 
					actual.get().getUserName(),
					actual.get().getPassWord(),
					actual.get().getAge(),
					actual.get().getBirthday(),
					actual.get().getRegistrationDate(),
					actual.get().getEmailAddress(),
					actual.get().getRoles());
		}
		
		LocalDate expected = LocalDate.of(1988, 12, 12);
		//System.out.println(userDao.findOne(query).getEmailAddress().getEmail());
		Assert.assertEquals(expected, actual.get().getBirthday());
	}
	
	@Test
	public void modifyTest(){
		String user = "userName";
		//expected object:
		Query query1 = new Query(Criteria.where("userName").is(user));
		Optional<User> expected = Optional.ofNullable(userDao.findOne(query1));
		if(expected.isPresent()) {
			expected.get().setPassWord("password_new");
			userDao.update(expected.get());
			
		}else {
			logger.info("===== findOneTest ==== user expected:-> isn't present!!");
		}
		
		
		
		//actual object:
		Query query2 = new Query(Criteria.where("userName").is(user));
		Optional<User> actual = Optional.ofNullable(userDao.findOne(query2));
		
		if(actual.isPresent()) {// print info message to console.
			logger.info("===== findOneTest ==== user actual:-> "
					+ "use_id:{}, user_name:{}, user_password:{}, "
					+ "user_age:{}, user_birthday:{}, "
					+ "user_registrationDate:{}, user_email:{}, "
					+ "user_roles:{}", 
					actual.get().getId(), 
					actual.get().getUserName(),
					actual.get().getPassWord(),
					actual.get().getAge(),
					actual.get().getBirthday(),
					actual.get().getRegistrationDate(),
					actual.get().getEmailAddress(),
					actual.get().getRoles());
		}
		
		Assert.assertEquals(expected.get().getPassWord(), actual.get().getPassWord());
	}
	
	@Test
	public void deleteTest(){
		Query query = new Query(Criteria.where("userName").is("userName"));
		userDao.remove(query);
	}
	
	@Test
	public void deleteAllTest(){
		userDao.removeAll();
	}

}
