package com.wei.springbootRedis.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wei.springbootRedis.domain.MovieNewEntity;

/**
 * MovieRepository interface.
 * Create MovieRepository interface to access {@link MovieNewEntity}'s
 * 
 * @author Wei WANG
 *
 */
@Repository
public interface MovieNewRepository extends GraphRepository<MovieNewEntity>{

	MovieNewEntity findByMovieName(@Param("movieName") String movieName);
	Collection<MovieNewEntity> findByMovieNameLike(@Param("movieName") int limit);
	
	Page<MovieNewEntity> findAll(Pageable pageable);
}