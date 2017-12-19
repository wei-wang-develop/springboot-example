package com.wei.springBootMongDB.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;

import com.wei.springBootMongDB.dao.UserDao;
import com.wei.springBootMongDB.domain.User;

/**
 * User Controller 实现 Restful HTTP 服务
 * @author Wei WANG
 *
 */
@RestController
public class UserRestController {

	@Autowired
	private UserDao userDao;
	
	//get user(s) methods
	@RequestMapping(value = "/api/user", method = RequestMethod.GET)
	public List<User> findAllUser(){
		return userDao.findAll();
	}
	
	//当使用@RequestMapping URI template 样式映射时， 即 someUrl/{paramId}, 
	//这时的paramId可通过 @Pathvariable注解绑定它传过来的值到方法的参数上
	@RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
	public User findOneById(@PathVariable("id") String id){
		return userDao.findById(new ObjectId(id), "user");
	}
	
	/*
	@RequestMapping(value = "/api/user/{userName}", method = RequestMethod.GET)
	public User findOneByUserName(@RequestParam(value="userName", required=true) String userName){
		Query query = new Query(Criteria.where("userName").is(userName));
		return userDao.findOne(query);
	}

	@RequestMapping(value = "/api/user/{email}", method = RequestMethod.GET)
	public User findOneByEmail(@RequestParam(value="email") String email){
		Query query = new Query(Criteria.where("email").is(email));
		return userDao.findOne(query);
	}*/
	
	
	
	// create user method
	@RequestMapping(value = "/api/user", method = RequestMethod.POST)
	public void createUser(@RequestBody User user){
		userDao.save(user);
	}
	
	// update(modify) user method
	@RequestMapping(value = "/api/user", method = RequestMethod.PUT)
	public void modifyUser(@RequestBody User user){
		userDao.update(user);
	}
	
	// delete user methods
	@RequestMapping(value = "/api/user/{id}", method = RequestMethod.DELETE)
	public void deleteById(@PathVariable("id") String id){
		userDao.remove(new ObjectId(id));
	}

	@RequestMapping(value = "/api/user/deleteAll", method = RequestMethod.DELETE)
	public void deleteAll(){
		userDao.removeAll();
	}
}
