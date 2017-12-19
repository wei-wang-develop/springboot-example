package com.wei.springbootRedis.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

public class User implements Serializable{

	
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String name;
	@DateTimeFormat(pattern = "YYYY-MM-DD HH:MM:SS")
	private LocalDate createData;
	
	private Department department;
	
	private List<Role> roles;

	//getter and setter methods
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getCreateData() {
		return createData;
	}

	public void setCreateData(LocalDate createData) {
		this.createData = createData;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", createData=" + createData + ", departement=" + department
				+ ", roles=" + roles + "]";
	}
	
	
	
}
