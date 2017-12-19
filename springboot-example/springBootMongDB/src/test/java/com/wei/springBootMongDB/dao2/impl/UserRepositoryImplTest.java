package com.wei.springBootMongDB.dao2.impl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.sound.midi.MidiDevice.Info;

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

import com.wei.springBootMongDB.dao.impl.UserDaoImplTest;
import com.wei.springBootMongDB.dao2.UserRepository;
import com.wei.springBootMongDB.domain.Department;
import com.wei.springBootMongDB.domain.Email;
import com.wei.springBootMongDB.domain.User;

import junit.framework.Assert;

/**
 * Test {@link UserRepository} implementation.
 * 
 * @author Wei WANG
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryImplTest {
	
	private static Logger logger = LoggerFactory.getLogger(UserDaoImplTest.class);

	@Autowired
	UserRepository userRepository;
	
	@Before
	public void setup() {
		userRepository.deleteAll();  // clean DB data.
		
		Department department = new Department("开发部");
		Set<String> roles = new HashSet<>();
		roles.add("manage");
		User user = new User("userName", "passWord", LocalDate.of(1988, 12, 12), 
								new Email("user@mail.com"), department,roles);
		userRepository.save(user);
			
	}
	
	public void printLogger(Optional<User> actual) {
		
		// method 1:
		if(actual.isPresent()) {
			logger.info("=====findTest==== user:-> "
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
		}else {
			logger.info("===== findTest ==== user :-> isn't present!!");
		}
		
		/*
		// method 2:
		actual.ifPresent(user ->  logger.info("=====findTest==== user:-> "
				+ "use_id:{}, user_name:{}, user_password:{}, "
				+ "user_age:{}, user_birthday:{}, "
				+ "user_registrationDate:{}, user_email:{}, "
				+ "user_department:{}, "
				+ "user_roles:{}", 
				user.getId(), 
				user.getUserName(),
				user.getPassWord(),
				user.getAge(),
				user.getBirthday(),
				user.getRegistrationDate(),
				user.getEmailAddress(),
				user.getDepartment(),
				user.getRoles()));
				*/
	}
	
	@Test
	public void findAllTest() {
		// method 1:
		userRepository.findAll().stream()
					.forEach(user -> System.out.println(user.toString()));
		// method 2:
		Optional<List<User>> actuals = Optional.ofNullable(userRepository.findAll());
		actuals.ifPresent((users) -> {
			users.forEach(user -> System.out.println(user.toString()));
		});
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void findAgeTest(){
		//传递基础类型(int, String, ...)进行查询
		//Query query = new Query(Criteria.where("age").is(28)); 
		Optional<User> actual = Optional.ofNullable(userRepository.findByAge((long) 28));
		
		printLogger(actual);  // print logger
		
		Long expected = (long) 28;
		Assert.assertEquals(expected, actual.get().getAge());
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	public void findByEmailAddressTest(){
		//传递负责类型(Object: Email, LocalDate, ...)进行查询
		Email expected = new Email("user@mail.com");
		Optional<User> actual = Optional.ofNullable(userRepository.findByEmailAddress(expected));
	
		printLogger(actual); // print logger
		
		Assert.assertEquals(expected, actual.get().getEmailAddress());
	}
	
	@Test 
	public void findByBirthdayTet(){
		//传递负责类型(Object: Email, LocalDate, ...)进行查询
		LocalDate expected = LocalDate.of(1988, 12, 12);
		Optional<User> actual = Optional.ofNullable(userRepository.findByBirthday(expected));
		
		printLogger(actual); // print logger
		
		Assert.assertEquals(expected, actual.get().getBirthday());
	}
	
	@Test
	public void modifyPassWordTest(){
		//String user = "userName";
		//expected object:
		// find the user by email
		Email email = new Email("user@mail.com");
		Optional<User> expected = Optional.ofNullable(userRepository.findByEmailAddress(email));
		if(expected.isPresent()) {
			printLogger(expected); // print old info
			
			expected.get().setPassWord("password_new");
			// MongoRepository 的 save()兼具插入和更新的功能
			userRepository.save(expected.get());
						
		}else {
			logger.info("===== findOneTest ==== user expected:-> isn't present!!");
		}
		
		//actual object:
		Optional<User> actual = Optional.ofNullable(userRepository.findByEmailAddress(email));
				
		printLogger(actual); // print  new info to console.
				
		Assert.assertEquals(expected.get().getPassWord(), actual.get().getPassWord());
	}
	
	
	@Test
	public void deleteAllTest() {
		userRepository.deleteAll();
	}
}
