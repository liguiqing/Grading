/**
 * Joy Anything (优异安心)
 * 
 * <p><b>© 2015-2015 </b></p>
 * 
 **/

package com.joy.grading.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.joy.commons.web.view.ModelAndViewFactory;

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
	
	@RequestMapping(value="/index")
	public ModelAndView onIndex()throws Exception{
		logger.debug("URL / Method Get");
		
		return ModelAndViewFactory.newModelAndViewFor("/index").build();
	}
}

