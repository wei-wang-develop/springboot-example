package com.wei.springbootMongodbRedis.domain;

import java.io.Serializable;

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
 *  注意: 
 *  如果需要向redis内存储pojo对象,那么该对象必须要实现Serializable接口,
 *  因为在redis中存储pojo类仍然存储的是string,它会把数据转化成byte[]数组的形式,
 *  在存取的时候就要对数据格式进行转化,就涉及到了序列化与反序列化.
 * 
 * domain 对象 User/Email 必须实现序列化，因为需要将对象序列化后存储到 Redis。
 * 如果没实现 Serializable ，控制台会爆出以下异常：
 * Serializable
 * java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload b
 */
@Document(collection="user")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private ObjectId id;
	
	/*
	 * 用户名
	 * */
	@NotEmpty(message = "用户名不能为空")
	@Size(min=2, max=20, message = "用户名长度,必须大于 2 且小于 20 字")
	private String userName;
	
	/*
	 *密码
	 * */
	@NotEmpty(message = "密码不能为空")
	@Size(min=2, max=20, message = "密码长度,必须大于 2 且小于 20 字")
	private String passWord;
	
	/*
	 * 年龄
	 * */
	@NotNull(message="年龄不能为空")
	@Min(value=0, message="年龄大于 0")
	@Max(value=300, message = "年龄小于 300")
	private Integer age;
	
	/**
     * 出生时间
     */
    @NotEmpty(message = "出生时间不能为空")
	private String birthday;
	
	/*
	 * 邮箱
	 * */
	@Field("email")
	@Indexed
	private Email emailAddress;

	
	//--------------------------------
	// getter and setter method
	//--------------------------------
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Email getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(Email emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", age=" + age + ", birthday="
				+ birthday + ", emailAddress=" + emailAddress + "]";
	}

}