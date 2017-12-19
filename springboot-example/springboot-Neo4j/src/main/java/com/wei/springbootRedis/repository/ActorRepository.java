package com.wei.springbootRedis.repository;

import java.util.Collection;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wei.springbootRedis.domain.Actor;

/**
 * Actor Repository interface.
 * create the ActoryRepository interface to access {@link Actor}'s 
 * 
 * @author Wei WANG
 */
@Repository
public interface ActorRepository extends GraphRepository<Actor>{

	Actor findByName(@Param("name") String name);
	
	
}
