package com.easytnt.grading.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import com.easytnt.test.controller.AbstractControllerTest;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年11月10日
 * @version 1.0
 **/

@ContextHierarchy({
	@ContextConfiguration(classes = { ExamineeController.class }),
	@ContextConfiguration(locations = { "classpath:servlet-context.xml" })
})
public class ExamineeControllerTest extends AbstractControllerTest{

	@Autowired
	private ExamineeController controller;
	
	@Test
	public void testGet()throws Exception{
		assertNotNull(controller);
		this.mvc.perform(get("/examinee"))
		.andExpect(view().name("/config"))
		.andExpect(content().string(startsWith("<!DOCTYPE html>")))
		.andExpect(content().string(containsString("</html>")));
	}
}
