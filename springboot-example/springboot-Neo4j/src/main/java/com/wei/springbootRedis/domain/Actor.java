package com.wei.springbootRedis.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Actor Entity.
 * 
 * @author Wei WANG
 *
 */
@JsonIdentityInfo(generator = JSOGGenerator.class) //防止查询数据时引发递归访问效应
@NodeEntity    //@NodeEntity 标志这个类是一个节点实体
public class Actor {
	
	@GraphId    //注解@GraphId定义了节点的一个唯一性标
	private Long id;
	
	private String name;
	private int born;
	
	
	public Actor() {}
	
	public Actor(String name) {
		this.name = name;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBorn() {
		return born;
	}
	public void setBorn(int born) {
		this.born = born;
	}
	@Override
	public String toString() {
		return "Actor [id=" + id + ", name=" + name + ", born=" + born + "]";
	}
	
	

}
