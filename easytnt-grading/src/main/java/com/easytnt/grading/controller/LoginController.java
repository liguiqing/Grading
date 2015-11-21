/**
 * 
 * 
 **/

package com.easytnt.grading.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.security.ShiroService;
import com.easytnt.security.UserDetails;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆2015年11月19日
 * @version 1.0
 **/
@Controller
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired(required=false)
	private ShiroService shiroService;
	
	@RequestMapping(value = "/login")
	public ModelAndView onLogin() throws Exception {
		logger.debug("URL /login Method Get");

		return ModelAndViewFactory.newModelAndViewFor("/login").build();
	}
	
	@RequestMapping(value = "/login/success",method=RequestMethod.GET)
	public ModelAndView onLoginSuccess() throws Exception {
		logger.debug("URL /login/success Method Get");
		UserDetails user = shiroService.getUser();
		return ModelAndViewFactory.newModelAndViewFor("redirect:/config?page=subject").build();
	}
	
	@RequestMapping(value = "/locked",method=RequestMethod.PUT)
	public ModelAndView onLocked() throws Exception {
		logger.debug("URL /locked Method Get");

		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	@RequestMapping(value = "/logout")
	public ModelAndView onLogout() throws Exception {
		logger.debug("URL /logout Method Get");
        shiroService.logout();
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
}
