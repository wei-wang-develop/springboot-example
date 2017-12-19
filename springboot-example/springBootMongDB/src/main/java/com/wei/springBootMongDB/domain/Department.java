package com.wei.springBootMongDB.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Department entity class.
 * 
 * @author Wei WANG
 *
 */
@Document(collection="department")
public class Department extends AbstractDocument implements Serializable{

	//private static final long serialVersionUID = 1L;
	
	@NotNull
	private String name;
	
	public Department(String name) {
		this.name = name;
	}
	
	// getter and setter methods
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "Departement [" + " name=" + name + "]";
	}
	
	
}
