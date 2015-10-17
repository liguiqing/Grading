package com.easytnt.grading.controller;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import com.easytnt.test.controller.AbstractControllerTest;

@ContextHierarchy({
	@ContextConfiguration(locations = {"classpath:servlet-context.xml"}),
	@ContextConfiguration(classes= {BlockViewController.class})
})
public class BlockViewControllerTest extends AbstractControllerTest {

	@Autowired
	BlockViewController controller;

	@Test
	public void testOnShowPoints() throws Exception {
		assertNotNull(controller);
		this.mvc.perform(get("/block/points/blockuuid"))
						.andExpect(view().name("/block/pointsPanel"))
						.andExpect(content().string(startsWith(" <div class='form-group  has-success has-feedback'>")));
	}
	
	@Test
	public void testOnShowImg() throws Exception {
		assertNotNull(controller);
		this.mvc.perform(get("/block/img/blockuuid"))
						.andExpect(view().name("/block/imgPanel"))
						.andExpect(content().string(startsWith("<div class=\"img-panel\">")));
	}
}
