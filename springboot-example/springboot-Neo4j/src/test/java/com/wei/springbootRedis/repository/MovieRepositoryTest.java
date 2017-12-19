package com.wei.springbootRedis.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.wei.springbootRedis.domain.Actor;
import com.wei.springbootRedis.domain.MovieEntity;
import com.wei.springbootRedis.domain.Role;


/**
 * 
 * @author Wei WANG
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieRepositoryTest {

	private static Logger logger = LoggerFactory.getLogger(MovieRepositoryTest.class);
	
	@Autowired
	private Session session;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ActorRepository actorRepository;
	
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		movieRepository.deleteAll();  // clean DB
		//--------------------
		// Movies
		//--------------------
		// movie 1:
		MovieEntity movie1 = new MovieEntity("The Matrix", "1999-03-31");
		// movie 2:
		MovieEntity movie2 = new MovieEntity("The Matrix Reloaded", "2003-05-07");
		// movie 3:
		MovieEntity movie3 = new MovieEntity("The Matrix Revolutions", "2003-10-27");
		
		//--------------------
		// Actors
		//--------------------
		// actor 1:
		Actor actor1 = new Actor("Kean Reeves");
		// actor 2:
		Actor actor2 = new Actor("Laurence Fishburne");
		// actor 3:
		Actor actor3 = new Actor("Carrie-Anne Moss");
		
		//--------------------
		// Roles
		//--------------------
		//Role role1 = new Role(actor1, movie1, "Neo");
		// Roles for Movie 1:
		Role role1 = movie1.addRole(actor1, "Neo");
		Role role2 = movie1.addRole(actor2, "Morpheus");
		Role role3 = movie1.addRole(actor3, "Trinity");			
		// Roles for Movie2:
		Role role4 = movie2.addRole(actor1, "Neo");
		Role role5 = movie2.addRole(actor2, "Morpheus");
		Role role6 = movie2.addRole(actor3, "Trinity");			
		// Roles for Movie3:
		Role role7 = movie3.addRole(actor1, "Neo");
		Role role8 = movie3.addRole(actor2, "Morpheus");
		Role role9 = movie3.addRole(actor3, "Trinity");
		
				
		//---------------------------------
		// save Movies Entity in Neo4j DB
		//---------------------------------
		movieRepository.save(movie1);
		movieRepository.save(movie2);
		movieRepository.save(movie3);
		
		//----------------------------
		// Tests
		//----------------------------
		Assert.notNull(movie1.getId());
		Assert.notNull(movie2.getId());
		Assert.notNull(movie3.getId());
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	public void getOneTest() {
		String movieTitle = "The Matrix";
		MovieEntity movieEntity = (MovieEntity) movieRepository.findByTitle(movieTitle);
		Assert.notNull(movieEntity);
		logger.info("======= movie ======= movie:{}, {}", movieEntity.getTitle(), movieEntity.getReleasedYear());
		movieEntity.getRoles().stream()
				.forEach(role -> {
					logger.info("======= actor ======= actor:{}, role:{}",role.getActor(), role.getRoleName());
				});
	}
	
}
