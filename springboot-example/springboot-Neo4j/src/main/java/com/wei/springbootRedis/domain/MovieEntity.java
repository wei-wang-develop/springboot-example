package com.wei.springbootRedis.domain;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Movie entity.
 * 
 * @author Wei WANG
 *
 */
@JsonIdentityInfo(generator = JSOGGenerator.class) //防止查询数据时引发递归访问效应
@NodeEntity    //@NodeEntity 标志这个类是一个节点实体
public class MovieEntity implements Movie{
	
	@GraphId
	private Long id;
	
	private String title;
	private String releasedYear;
	private String tagline;
	
	//注解 @Relationship 表示List<Role>是一个关系列表，
	//其中type 设定了关系的类型， direction 设定这个关系的方向，
	//Relationship.INCOMING 表示以这个节点为终点。
	@Relationship(type="扮演", direction =Relationship.INCOMING )
	private List<Role> roles = new ArrayList<>(); //关系列表，泛型 指只能存放所有角色的集合
	
	//---------------------
	// Constructor
	//---------------------
	public MovieEntity() {}

	public MovieEntity(String title, String releasedYear) {
		this.title = title;
		this.releasedYear = releasedYear;
	}

	
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReleasedYear() {
		return releasedYear;
	}

	public void setReleasedYear(String releasedYear) {
		this.releasedYear = releasedYear;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", releasedYear=" + releasedYear + ", tagline=" + tagline
				+ ", roles=" + roles + "]";
	}

}
