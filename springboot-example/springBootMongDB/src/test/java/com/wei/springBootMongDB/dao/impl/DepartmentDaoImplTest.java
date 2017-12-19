package com.wei.springBootMongDB.dao.impl;

import java.util.Optional;

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

import com.wei.springBootMongDB.dao.BaseDao;
import com.wei.springBootMongDB.dao.DepartmentDao;
import com.wei.springBootMongDB.domain.Department;

import junit.framework.Assert;

/**
 * Test {@link DepartmentDao} implementation using extends {@link BaseDao} implementation.
 * 
 * @author Wei WANG
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentDaoImplTest {

	private static Logger logger = LoggerFactory.getLogger(DepartmentDaoImplTest.class);
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Before
	public void setup() {
		departmentDao.removeAll(); // clean DB data before the Test.
		Department department = new Department("开发部");
		departmentDao.save(department);
	}
	
	@Test
	public void findAllTest(){
		departmentDao.findAll().stream()
					.forEach(department -> System.out.println(department.toString()));
	}
	
	@Test 
	public void findOneTest() {
		//传递基础类型(int, String, ...)进行查询
		Query query = new Query(Criteria.where("name").is("开发部")); 
		Optional<Department> actual = Optional.ofNullable(departmentDao.findOne(query));
		if(actual.isPresent()) {
			logger.info("=====findOneTest==== department:-> "
					+ "department_id:{},department_name:{}",
					actual.get().getId(),actual.get().getName());
		}
		String expected = "开发部";
		Assert.assertEquals(expected, actual.get().getName());
	}
	
	@Test
	public void modifyTest(){
		String old_department = "开发部";
		String new_department = "维护部";
		//expected object:
		Query query1 = new Query(Criteria.where("name").is(old_department));
		Optional<Department> expected = Optional.ofNullable(departmentDao.findOne(query1));
		if(expected.isPresent()) {
			expected.get().setName(new_department);
			departmentDao.update(expected.get());
			
		}else {
			logger.info("===== findOneTest ==== user expected:-> isn't present!!");
		}
		//actual object:
		Query query2 = new Query(Criteria.where("name").is(new_department));
		Optional<Department> actual = Optional.ofNullable(departmentDao.findOne(query2));
		Assert.assertEquals(new_department, actual.get().getName());
	}
}
