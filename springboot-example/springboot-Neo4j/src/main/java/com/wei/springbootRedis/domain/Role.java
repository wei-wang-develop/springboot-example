package com.wei.springbootRedis.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.security.RolesAllowed;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

/**
 * Role Relation Entity
 * @author Wei WANG
 *
 */
@JsonIdentityInfo(generator = JSOGGenerator.class)
@RelationshipEntity(type = "扮演") //，注解@RelationshipEntity 表明这个类是一个关系实体，并用type 指定了关系的类型
public class Role {

	@GraphId
	private Long id;
	private String roleName;
	
	//one Actor multi-movie
	private Collection<String> roles = new ArrayList<>();
	
	@StartNode
	private Actor actor;
	@EndNode
	private Movie movie;
	
	//---------------------
	// Constructor
	//---------------------
	
	public Role() {}
	
	public Role(Actor actor, Movie movie, String roleName) {
		this.actor = actor;
		this.movie = movie;
		this.roleName = roleName;
	}
	
	
	public void addRoleName(String name) {
		this.roles.add(name);
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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Collection<String> getRoles() {
		return roles;
	}

	public void setRoles(Collection<String> roles) {
		this.roles = roles;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", roles=" + roles + ", actor=" + actor + ", movie="
				+ movie + "]";
	}
	

	
}
