package com.wei.springbootRedis.domain;

public interface Movie {
	
	void addRole(Role role);
	Role addRole (Actor actor, String roleName);
	
}
