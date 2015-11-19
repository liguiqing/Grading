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
import com.easytnt.grading.domain.grade.Teacher;
import com.easytnt.grading.service.TeacherService;
import com.easytnt.test.controller.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextHierarchy({
	@ContextConfiguration(classes = { TeacherController.class }),
	@ContextConfiguration(locations = { "classpath:servlet-context.xml" })

})
public class TeacherControllerTest extends AbstractControllerTest {
	private static Logger logger = LoggerFactory.getLogger(TeacherControllerTest.class);
	
	@Autowired
	@InjectMocks
	private TeacherController controller;
	
	@Mock
	private TeacherService teacherService;
	
	@Test
	public void testOnCreate()throws Exception{
		assertNotNull(this.controller);
		//Teacher Teacher = mock(Teacher.class);
		Teacher Teacher = new Teacher();
		ObjectMapper mapper = new ObjectMapper();
		doNothing().when(teacherService).create(Teacher,1);
		String content = mapper.writeValueAsString(Teacher);
		this.mvc.perform(post("/teacher/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
	}
	
	@Test
	public void testOnView()throws Exception{
		assertNotNull(this.controller);
		
		this.mvc.perform(get("/teacher/1"))
		.andExpect(view().name("/teacher/editTeacher"))
		.andExpect(content().string(startsWith("<div>")))
		.andExpect(content().string(containsString("</div>")));
	}
	
	@Test
	public void testOnUpdate()throws Exception{
		assertNotNull(this.controller);
		Teacher Teacher = new Teacher();
		ObjectMapper mapper = new ObjectMapper();
		doNothing().when(teacherService).update(Teacher);
		String content = mapper.writeValueAsString(Teacher);
		this.mvc.perform(put("/teacher").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
		
	}
	
	@Test
	public void testOnDelete()throws Exception{
		assertNotNull(this.controller);
		Teacher teacher = new Teacher();
		teacher.setTeacherId(1l);
		ObjectMapper mapper = new ObjectMapper();
		doNothing().when(teacherService).delete(teacher);
		String content = mapper.writeValueAsString(teacher);
		this.mvc.perform(delete("/teacher").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
		
	}
	
	@Test
	public void testOnQuery()throws Exception{
		assertNotNull(this.controller);
		
		doNothing().when(teacherService).query(isA(Query.class));
		
		this.mvc.perform(get("/teacher/query/1/10").param("name", "name")
		.accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(view().name("/teacher/listTeacher"))
		.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
		
	}
}
