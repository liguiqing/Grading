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
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.service.ExamPaperService;
import com.easytnt.test.controller.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextHierarchy({
	@ContextConfiguration(classes = { ExamPaperController.class }),
	@ContextConfiguration(locations = { "classpath:servlet-context.xml" })

})
public class ExamPaperControllerTest extends AbstractControllerTest {
	private static Logger logger = LoggerFactory.getLogger(ExamPaperControllerTest.class);
	
	@Autowired
	@InjectMocks
	private ExamPaperController controller;
	
	@Mock
	private ExamPaperService examPaperService;
	
	@Test
	public void testOnCreate()throws Exception{
		assertNotNull(this.controller);
		//Exam exam = mock(Exam.class);
		ExamPaper examPaper = new ExamPaper();
		ObjectMapper mapper = new ObjectMapper();
		doNothing().when(examPaperService).create(examPaper);
		String content = mapper.writeValueAsString(examPaper);
		this.mvc.perform(post("/examPaper").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
	}
	
	@Test
	public void testOnView()throws Exception{
		assertNotNull(this.controller);
		
		this.mvc.perform(get("/examPaper/1"))
		.andExpect(view().name("/examPaper/editExamPaper"))
		.andExpect(content().string(startsWith("<div>")))
		.andExpect(content().string(containsString("</div>")));
	}
	
	@Test
	public void testOnUpdate()throws Exception{
		assertNotNull(this.controller);
		ExamPaper examPaper = new ExamPaper();
		ObjectMapper mapper = new ObjectMapper();
		doNothing().when(examPaperService).update(examPaper);
		String content = mapper.writeValueAsString(examPaper);
		this.mvc.perform(put("/examPaper").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
		
	}
	
	@Test
	public void testOnDelete()throws Exception{
		assertNotNull(this.controller);
		ExamPaper examPaper = new ExamPaper();
		examPaper.setPaperOid(1l);
		ObjectMapper mapper = new ObjectMapper();
		doNothing().when(examPaperService).delete(examPaper);
		String content = mapper.writeValueAsString(examPaper);
		this.mvc.perform(delete("/examPaper").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
		
	}
	
	@Test
	public void testOnQuery()throws Exception{
		assertNotNull(this.controller);
		
		doNothing().when(examPaperService).query(isA(Query.class));
		
		this.mvc.perform(get("/examPaper/query/1/10").param("name", "name"))
		.andExpect(view().name("/examPaper/listExamPaper"))
		.andExpect(content().string(startsWith("<!DOCTYPE html>")))
		.andExpect(content().string(containsString("</html>")));
		
	}
}
