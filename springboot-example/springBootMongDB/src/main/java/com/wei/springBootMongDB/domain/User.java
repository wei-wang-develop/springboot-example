package com.wei.springBootMongDB.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * User entity class.
 * 创建用户数据实体
 *  
 * @author Wei WANG
 *
 */
@Document(collection="user")
public class User extends AbstractDocument implements Serializable{
	
	//private static final long serialVersionUID = 1L;

	
	//@Id
	//private ObjectId id;
	

	/*
	 * 用户名
	 * */
	@NotEmpty(message = "用户名不能为空")
	@Size(min=2, max=20, message = "用户名长度,必须大于 2 且小于 20 字")
	@NotNull
	private String userName;
	
	/*
	 *密码
	 * */
	@NotEmpty(message = "密码不能为空")
	@Size(min=2, max=20, message = "密码长度,必须大于 2 且小于 20 字")
	@NotNull
	private String passWord;
	
	/**
	 * 年龄
	 */
	@NotNull(message="年龄不能为空")
	@Min(value=0, message="年龄大于 0")
	@Max(value=300, message = "年龄小于 300")
	private Long age;
	
	/**
     * 出生时间
     */
    @NotEmpty(message = "出生时间不能为空")
	private LocalDate birthday;
    
    /**
     * 注册时间
     */
    @NotNull
    private LocalDate registrationDate;
	
	/*
	 * 邮箱
	 * */
	@Field("emailAddress")
	@Indexed
	@NotNull
	private Email emailAddress;
	
	private Department department;
	
	/**
	 * 用户的角色
	 */
	private Set<String> roles = new HashSet<>();

	public User() {
		
	}
	
	public User(String userName, String passWord, LocalDate birthday, 
			Email emailAddress, Department department,Set<String> roles) {
		
		this.userName = userName;
		this.passWord = passWord;
		this.birthday = birthday;
		this.age = ChronoUnit.YEARS.between(this.birthday, LocalDate.now());
		this.registrationDate = LocalDate.now();
		this.emailAddress = emailAddress;
		this.department = department;
		this.roles = roles;
	}
	
	
	//--------------------------------
	// getter and setter method
	//--------------------------------
	
	/*
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}*/

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Email getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(Email emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}


	@Override
	public String toString() {
		return "User ["+ super.toString() +", userName=" + userName + ", passWord=" + passWord + ", age=" + age + ", birthday=" + birthday
				+ ", registrationDate=" + registrationDate + ", emailAddress=" + emailAddress + ", department="
				+ department + ", roles=" + roles + "]";
	}
	
 
}
