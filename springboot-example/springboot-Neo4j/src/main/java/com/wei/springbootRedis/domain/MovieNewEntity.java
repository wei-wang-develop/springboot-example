package com.wei.springbootRedis.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * new Movie entity.
 * 
 * @author Wei WANG
 *
 */
@JsonIdentityInfo(generator = JSOGGenerator.class) //防止查询数据时引发递归访问效应
@NodeEntity    //@NodeEntity 标志这个类是一个节点实体
public class MovieNewEntity implements Movie{
	
	@GraphId
	private Long id;
	private String movieName;
	private String photo;  // 存放剧照
	
	//Neo4j还没有日期格式的数据类型，所以
	//在读取日期类型的数据时，使用注解@DateTimeFormat 进行格式转换，
	//而在保存时，使用注解@DateLong 将它转换成Long 类型的数据进行存储。
	@DateLong
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	//注解 @Relationship 表示List<Role>是一个关系列表，
	//其中type 设定了节点的关系的类型， direction 设定这个关系的方向，
	//Relationship.INCOMING 表示以这个节点为终点。
	@Relationship(type="扮演", direction =Relationship.INCOMING )
	private List<Role> roles = new ArrayList<>(); //关系列表，泛型 指只能存放所有角色的集合
	
	//---------------------
	// Constructor
	//---------------------
	public MovieNewEntity() {}

	public MovieNewEntity(String movieName, Date createDate) {
		this.movieName = movieName;
		this.createDate = createDate;
	}
	
	
	
	@Override
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
	 //增加关系的方法
	public Role addRole(Actor actor, String roleName) {
		Role role = new Role(actor, this, roleName);
		this.roles.add(role);
		return role;
	}

	
	
	//----------------------------------
	// getter and setter methods
	//----------------------------------
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "MovieNewEntity [id=" + id + ", movieName=" + movieName + ", photo=" + photo + ", createDate="
				+ createDate + ", roles=" + roles + "]";
	}
	
}
