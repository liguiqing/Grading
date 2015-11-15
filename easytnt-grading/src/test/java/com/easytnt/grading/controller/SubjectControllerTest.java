package com.easytnt.grading.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import com.easytnt.grading.domain.exam.Subject;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.service.ExamPaperService;
import com.easytnt.grading.service.SubjectService;
import com.easytnt.test.controller.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextHierarchy({
	@ContextConfiguration(classes = { SubjectController.class }),
	@ContextConfiguration(locations = { "classpath:servlet-context.xml" })

})
public class SubjectControllerTest extends AbstractControllerTest {
	private static Logger logger = LoggerFactory.getLogger(SubjectControllerTest.class);
	
	@Autowired
	@InjectMocks
	private SubjectController controller;
	
	@Mock
	private SubjectService SubjectService;
	
	@Test
	public void testOnCreate()throws Exception{
		assertNotNull(this.controller);
		//Exam exam = mock(Exam.class);
		Subject subject = new Subject();
		ObjectMapper mapper = new ObjectMapper();
		doNothing().when(SubjectService).create(subject);
		String content = mapper.writeValueAsString(subject);
		this.mvc.perform(post("/subject").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
	}
	
	@Test
	public void testOnView()throws Exception{
		assertNotNull(this.controller);
		
		this.mvc.perform(get("/subject/1"))
		.andExpect(view().name("/subject/editSubjectPaper"))
		.andExpect(content().string(startsWith("<div>")))
		.andExpect(content().string(containsString("</div>")));
	}
	
	@Test
	public void testOnUpdate()throws Exception{
		assertNotNull(this.controller);
		Subject subject = new Subject();
		ObjectMapper mapper = new ObjectMapper();
		doNothing().when(SubjectService).update(subject);
		String content = mapper.writeValueAsString(subject);
		this.mvc.perform(put("/subject").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
		
	}
	
	@Test
	public void testOnDelete()throws Exception{
		assertNotNull(this.controller);
		Subject subject = new Subject();
		subject.setId(1l);
		ObjectMapper mapper = new ObjectMapper();
		doNothing().when(SubjectService).delete(subject);
		String content = mapper.writeValueAsString(subject);
		this.mvc.perform(delete("/subject").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
		
	}
	
	@Test
	public void testOnQuery()throws Exception{
		assertNotNull(this.controller);
		
		doNothing().when(SubjectService).query(isA(Query.class));
		
		this.mvc.perform(get("/subject/query/1/10").param("name", "name"))
		.andExpect(view().name("/subject/listSubjectPaper"))
		.andExpect(content().string(startsWith("<!DOCTYPE html>")))
		.andExpect(content().string(containsString("</html>")));
		
	}
}
