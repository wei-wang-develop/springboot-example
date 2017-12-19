package com.wei.springBootMongDB.controller;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wei.springBootMongDB.dao.UserDao;
import com.wei.springBootMongDB.domain.User;

/**
 * 用户控制层
 * @author Wei WANG
 *
 */
@Controller     //如果我们需要使用页面开发只要使用 @Controller
public class UserController {

	@Autowired
	private UserDao userDao;
	
	//--------------------------
	// Server --> Client
	//--------------------------
	
	/**
     *  获取用户列表
     *    处理 "/users" 的 GET 请求，用来获取用户列表
     *    通过 @RequestParam 传递参数，进一步实现条件查询或者分页查询
     */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getUserList(ModelMap map){
		// 加入一个属性，用来在模板中读取
		map.addAttribute("userList", userDao.findAll());
		// return模板文件的名称，对应 src/main/resources/templates/userList.html
		return "/userList";
		//return "/userList_with_header_navbar_footer";
	}
	
	 /**
     * 显示创建用户表单
     *
     * @param map
     * @return
     */
	@RequestMapping(value="/users/create", method=RequestMethod.GET)
	public String createUserForm(ModelMap map){
		map.addAttribute("user", new User());
		map.addAttribute("action", "create");
		return "userForm";
	}
	
	   
    /**
     * 显示需要更新用户表单
     *    处理 "/users/{id}" 的 GET 请求，通过 URL 中的 id 值获取 User 信息
     *    URL 中的 id ，通过 @PathVariable 绑定参数
     */
    @RequestMapping(value="/users/update/{id}", method=RequestMethod.GET)
    public String getUser(@PathVariable String id , ModelMap map){
    	User user = userDao.findById(new ObjectId(id), "user");
    	map.addAttribute("user", user);
    	map.addAttribute("action", "update");
    	return "userForm";
    }
    
    
    
	//--------------------------
	// Client --> Server
	//--------------------------
    
    /**
     *  创建用户
     *    处理 "/users" 的 POST 请求，用来获取用户列表
     *    通过 @ModelAttribute 绑定参数，也通过 @RequestParam 从页面中传递参数
     *    通过 BindingResult 获得错误信息
     */
    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public String createUser(ModelMap map,
    						@ModelAttribute @Valid User user,
    						BindingResult bindingResult){
    	//if the form has errors informations.
    	if(bindingResult.hasErrors()){
    		map.addAttribute("action", "create");
    		return "userForm";
    	}    	
    	userDao.save(user);
    	return "redirect:/users/";
    }
    
    /**
     * 更新 User 信息:
     * 处理 "/users/{id}" 的 PUT 请求，用来更新 User 信息
     */
    @RequestMapping(value="/users/update", method=RequestMethod.POST)
    public String modifyUser(ModelMap map,
    					  @ModelAttribute @Valid User user,
    					  BindingResult bindingResult){
    	/*
    	if(bindingResult.hasErrors()){
    		map.addAttribute("action", "update");
    		return "userForm";
    	}*/
    	userDao.update(user);
    	return "redirect:/users/";
    }
    
    /**
     * 处理 "/users/{id}" 的 GET 请求，用来删除 User 信息
     */
    @RequestMapping(value="/users/delete/{id}", method=RequestMethod.GET)
    public String deleteUser(@PathVariable String id){
    	userDao.remove(new ObjectId(id));
    	return "redirect:/users/";
    }
}
