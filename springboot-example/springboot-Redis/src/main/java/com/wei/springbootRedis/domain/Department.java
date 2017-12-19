package com.wei.springbootRedis.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class Department implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String name;
	
	
	// getter and setter methods
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
	
	
	@Override
	public String toString() {
		return "Departement [id=" + id + ", name=" + name + "]";
	}
	
	
}
