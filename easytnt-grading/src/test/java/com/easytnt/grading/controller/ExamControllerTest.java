package com.easytnt.grading.controller;

import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.service.ExamService;
import com.easytnt.test.controller.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextHierarchy({
	@ContextConfiguration(classes = { ExamController.class }),
	@ContextConfiguration(locations = { "classpath:servlet-context.xml" })

})
public class ExamControllerTest extends AbstractControllerTest {
	private static Logger logger = LoggerFactory.getLogger(ExamControllerTest.class);
	
	@Autowired
	@InjectMocks
	private ExamController controller;
	
	@Mock
	private ExamService examService;
	
	@Test
	public void testOnCreate()throws Exception{
		assertNotNull(this.controller);
		//Exam exam = mock(Exam.class);
		Exam exam = new Exam();
		ObjectMapper mapper = new ObjectMapper();
		doNothing().when(examService).create(exam);
		String content = mapper.writeValueAsString(exam);
		this.mvc.perform(post("/exam").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
	}
	
	@Test
	public void testOnView()throws Exception{
		assertNotNull(this.controller);
		
		this.mvc.perform(get("/exam/1"))
		.andExpect(view().name("/exam/editExam"))
		.andExpect(content().string(startsWith("<div>")))
		.andExpect(content().string(containsString("</div>")));
	}
	
	@Test
	public void testOnUpdate()throws Exception{
		assertNotNull(this.controller);
		Exam exam = new Exam();
		ObjectMapper mapper = new ObjectMapper();
		doNothing().when(examService).update(exam);
		String content = mapper.writeValueAsString(exam);
		this.mvc.perform(put("/exam").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
		
	}
	
	@Test
	public void testOnDelete()throws Exception{
		assertNotNull(this.controller);
		Exam exam = new Exam();
		exam.setOid(1l);
		ObjectMapper mapper = new ObjectMapper();
		doNothing().when(examService).delete(exam);
		String content = mapper.writeValueAsString(exam);
		this.mvc.perform(delete("/exam").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
		
	}
	
	@Test
	public void testOnQuery()throws Exception{
		assertNotNull(this.controller);
		
		doNothing().when(examService).query(isA(Query.class));
		
		this.mvc.perform(get("/exam/query/1/10").param("name", "name"))
		.andExpect(view().name("/exam/listExam"))
		.andExpect(content().string(startsWith("<!DOCTYPE html>")))
		.andExpect(content().string(containsString("</html>")));
		
	}
}
