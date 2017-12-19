package com.wei.springBootMongDB.dao.impl;

import org.springframework.stereotype.Repository;

import com.wei.springBootMongDB.dao.DepartmentDao;
import com.wei.springBootMongDB.domain.Department;

/**
 * {@link DepartmentDao} implementation extends BaseDao implementation.o
 * @author lenovo
 *
 */
@Repository("departmentDaoImpl")
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao{

}
