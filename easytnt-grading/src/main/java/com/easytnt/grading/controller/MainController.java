/**
 * Joy Anything (卓越安心)
 * 
 * <p><b>© 2015-2015 </b></p>
 * 
 **/

package com.easytnt.grading.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.ui.Menu;
import com.easytnt.commons.ui.MenuGroup;
import com.easytnt.commons.web.view.ModelAndViewFactory;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年7月31日
 * @version 1.0
 **/
@Controller
public class MainController {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView onIndex()throws Exception{
		logger.debug("URL /index Method Get");
		
		return ModelAndViewFactory.newModelAndViewFor("/index").build();
	}

	@RequestMapping(value="/config",method=RequestMethod.GET)
	public ModelAndView onConfig(@RequestParam String page)throws Exception{
		logger.debug("URL /config Method Get");
		return ModelAndViewFactory.newModelAndViewFor("redirect:/"+page).build();
	}
	
	@RequestMapping(value="/monitor",method=RequestMethod.GET)
	public ModelAndView onMonitor(@RequestParam String page)throws Exception{
		logger.debug("URL /monitor Method Get");
		return ModelAndViewFactory.newModelAndViewFor("redirect:/monitor/"+page).build();

	}
}