/**
 * <p><b>© 2015-2015</b></p>
 * 
 **/

package com.easytnt.grading.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


import org.springframework.test.context.ContextHierarchy;

import com.easytnt.test.controller.AbstractControllerTest;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年7月31日
 * @version 1.0
 **/ 

@ContextHierarchy({
	@ContextConfiguration(classes= {MainController.class}),
	@ContextConfiguration(locations = {"classpath:servlet-context.xml"})
})
public class MaincontrollerTest extends AbstractControllerTest{
	private static Logger logger = LoggerFactory.getLogger(MaincontrollerTest.class);
	@Autowired
	@InjectMocks
	private MainController controller;
	
	@Test
	public void testOnIndex() throws Exception{
		assertNotNull(controller);
		this.mvc.perform(get("/index")).andExpect(view().name("/index"));
	}
}

