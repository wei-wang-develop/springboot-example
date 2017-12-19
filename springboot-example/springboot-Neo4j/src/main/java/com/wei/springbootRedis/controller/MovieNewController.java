package com.wei.springbootRedis.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wei.springbootRedis.domain.Actor;
import com.wei.springbootRedis.domain.Movie;
import com.wei.springbootRedis.domain.MovieNewEntity;
import com.wei.springbootRedis.repository.ActorRepository;
import com.wei.springbootRedis.repository.MovieNewRepository;
import com.wei.springbootRedis.service.PageService;

/**
 * Movie 控制层
 * @author Wei WANG
 *
 */
@Controller     //如果我们需要使用页面开发只要使用 @Controller
@RequestMapping("/movie")
public class MovieNewController {

	private static Logger logger = LoggerFactory.getLogger(MovieNewController.class);
	
	@Autowired
	private MovieNewRepository movieNewRepository;
	@Autowired
	private ActorRepository actorRepository;	
	@Autowired
	private PageService<MovieNewEntity> pageService;
	
	
	/**
	 * 显示Movie 初始页面.
	 * @return
	 */
	@RequestMapping(value="/index")
	public ModelAndView index() {
		return new ModelAndView("movie/index");
	}
	
	/**
	 * 显示Movie表单
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}")
	public ModelAndView show(ModelMap model, @PathVariable Long id) {
		Movie movie = movieNewRepository.findOne(id);
		model.addAttribute("movie", movie);
		return new ModelAndView("movie/show");
	}
	
	/**
	 * 新建Movie电影控制器.
	 * @param model
	 * @return
	 */
	@RequestMapping("/new")
	public ModelAndView create(ModelMap model) {
		String[] files = {"/images/movie/西游记.jpg","/images/movie/西游记续集.jpg"};
		model.addAttribute("files", files);
		return new ModelAndView("movie/new");
	}
	
	/**
	 * 存储  Movie 控制器
	 * @param movie
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value= "/save", method = RequestMethod.POST)
	public String save(MovieNewEntity movie) throws Exception {
		movieNewRepository.save(movie);
		logger.info("新增 Movie -> ID={}", movie.getId());
		return "1";
	}
	
	/**
	 * 更新控制器
	 * 更新之前，先显示.
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit/{id}")
	public ModelAndView update(ModelMap model, @PathVariable Long id) {
		// find the movie by ID
		Movie movie = movieNewRepository.findOne(id);
		String[] files = {"/images/movie/西游记.jpg", "/images/movie/西游记续集.jpg"};
		String[] roleList = new String[]{"唐僧","孙悟空","猪八戒","沙僧"};
		
		// find all actors
		Iterable<Actor> actors = actorRepository.findAll();
		
		model.addAttribute("files", files);
		model.addAttribute("rolelist", roleList);
		model.addAttribute("movie", movie);
		model.addAttribute("actors", actors);
		
		return new ModelAndView("movie/edit");
	}
	
	/**
	 * 更新控制器
	 * 更新显示之后，存储.
	 * @param movie
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(MovieNewEntity movie, HttpServletRequest request)throws Exception {
		// Actor
		String roleName = request.getParameter("rolename");
		String actorId = request.getParameter("actorid");
		
		// Movie: update new movie
		MovieNewEntity oldMovie = movieNewRepository.findOne(movie.getId());
		oldMovie.setMovieName(movie.getMovieName());
		oldMovie.setPhoto(movie.getPhoto());
		oldMovie.setCreateDate(movie.getCreateDate());
		
		if( StringUtils.isNotEmpty(roleName) && StringUtils.isNotEmpty(actorId)) {
			Actor actor = actorRepository.findOne(new Long(actorId));
			oldMovie.addRole(actor, roleName);
		}
		movieNewRepository.save(oldMovie);
		
		logger.info("修改 Movie ->ID=", oldMovie.getId());
		return "1";
	}
	
	/**
	 * 删除 Movie控制器.
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable Long id) throws Exception{
		//Movie movie = movieNewRepository.findOne(id);
		movieNewRepository.delete(id);
		logger.info("删除 Movie ->ID="+id);
		return "1";
	}
	
	/**
	 * Movie 电影分页查询控制器
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public Page<MovieNewEntity> list(HttpServletRequest request) throws Exception{
		String name = request.getParameter("name");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		
		Pageable pageable = new PageRequest(page == null ? 0 : Integer.parseInt(page),
											size == null ? 10 : Integer.parseInt(size),
											new Sort(Sort.Direction.DESC, "id"));
		// add filter conditions.
		// 添加过滤条件
		Filters filters = new Filters();
		if(StringUtils.isNotEmpty(name)) {
			Filter filter = new Filter("name", name);
			filters.add(filter);
		}
		
		return pageService.findAll(MovieNewEntity.class, pageable, filters);
	}
	
	
}
