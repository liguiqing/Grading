/**
 * <p><b>© 2015-2015</b></p>
 * 
 **/

package com.joy.grading.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


import com.joy.test.controller.AbstractControllerTest;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年7月31日
 * @version 1.0
 **/ 

@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:servlet-context.xml","main.xml"})
public class MaincontrollerTest extends AbstractControllerTest{

	@Autowired
	private MainController controller;
	
	@Test
	public void testOnIndex() throws Exception{
		assertNotNull(controller);
		this.mvc.perform(get("/index")).andExpect(view().name("/index"));
	}
}

