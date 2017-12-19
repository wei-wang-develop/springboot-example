package com.wei.springbootRedis.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.wei.springbootRedis.domain.Department;
import com.wei.springbootRedis.domain.Role;
import com.wei.springbootRedis.domain.User;
import com.wei.springbootRedis.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
	
	private static Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);
	
	@Autowired
	private UserService userService;
	
	@Before
	public void setup(){
		// Departement:
		Department department = new Department();
		department.setName("开发部");
		
		// Role
		Role role = new Role();
		role.setName("admin");
		
		// User
		User user = new User();
		user.setName("user");
		user.setCreateData(LocalDate.now());
		user.setDepartment(department);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		user.setRoles(roles);
		
		String key = this.getClass().getName()+"userByName:"+user.getName();
		userService.delete(key);
		userService.add(key, 10L, user);
	}
	
	@Test 
	public void get(){
		String key = this.getClass().getName()+"userByName:user";
		User user = userService.get(key);
		Assert.notNull(user);
		
		System.out.println("user name: "+user.getId());
		//System.out.println("user name: "+user.getName());
		//System.out.println("deparment: "+user.getDeparment().getName());
		//System.out.println("user role: "+user.getRoles().get(0).getName());
		
		logger.info("======== user ======= name:{}, deparment:{}, role:{}",
				user.getName(), user.getDepartment().getName(), user.getRoles().get(0).getName());
			
	}

}
