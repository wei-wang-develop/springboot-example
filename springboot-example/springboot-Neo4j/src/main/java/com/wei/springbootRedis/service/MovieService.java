package com.wei.springbootRedis.service;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wei.springbootRedis.domain.MovieEntity;
import com.wei.springbootRedis.repository.MovieRepository;

/**
 * Movie service.
 * 
 * @author Wei WANG
 *
 */
public class MovieService {

	@Autowired 
	private MovieRepository movieRepository;
	
	private Map<String, Object> toD3Format(Collection<MovieEntity> movieEntities){
		
		return null;
	}
}
