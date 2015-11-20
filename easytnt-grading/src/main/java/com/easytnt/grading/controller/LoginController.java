/**
 * 
 * 
 **/

package com.easytnt.grading.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.web.view.ModelAndViewFactory;

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

	@RequestMapping(value = "/login")
	public ModelAndView onLogin() throws Exception {
		logger.debug("URL /login Method Get");

		return ModelAndViewFactory.newModelAndViewFor("/login").build();
	}
	
	@RequestMapping(value = "/login/success",method=RequestMethod.GET)
	public ModelAndView onLoginSuccess() throws Exception {
		logger.debug("URL /login/success Method Get");

		return ModelAndViewFactory.newModelAndViewFor("/config?page=subject").build();
	}
	
	@RequestMapping(value = "/locked",method=RequestMethod.PUT)
	public ModelAndView onLocked() throws Exception {
		logger.debug("URL /locked Method Get");

		return ModelAndViewFactory.newModelAndViewFor("/config?page=subject").build();
	}
	
	@RequestMapping(value = "/logout",method=RequestMethod.PUT)
	public ModelAndView onLogout() throws Exception {
		logger.debug("URL /logout Method Get");

		return ModelAndViewFactory.newModelAndViewFor("/config?page=subject").build();
	}
}