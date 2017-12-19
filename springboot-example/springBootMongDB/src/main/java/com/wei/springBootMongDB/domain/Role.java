package com.wei.springBootMongDB.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Role entity class.
 * 
 * @author Wei WANG
 *
 */
@Document(collection="role")
public class Role extends AbstractDocument implements Serializable{

	//private static final long serialVersionUID = 1L;

	@NotNull
	private String name;
	
	// getter and setter methods

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "Role [" + ", name=" + name + "]";
	}
	
	
}
