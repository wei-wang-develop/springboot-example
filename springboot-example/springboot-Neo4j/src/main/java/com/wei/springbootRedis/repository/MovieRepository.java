package com.wei.springbootRedis.repository;

import java.util.Collection;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wei.springbootRedis.domain.Movie;
import com.wei.springbootRedis.domain.MovieEntity;

/**
 * MovieRepository interface.
 * Create MovieRepository interface to access {@link MovieEntity}'s
 * 
 * @author Wei WANG
 *
 */
@Repository
public interface MovieRepository extends GraphRepository<MovieEntity>{

	MovieEntity findByTitle(@Param("title") String title);
	Collection<MovieEntity> findByTitleLike(@Param("title") int limit);
}
