package com.easytnt.grading.controller;

import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.service.ExamService;
import com.easytnt.grading.service.ExamPaperService;
import com.easytnt.test.controller.AbstractControllerTest;

@ContextHierarchy({
	@ContextConfiguration(classes= {MakingController.class}),
	@ContextConfiguration(locations = {"classpath:servlet-context.xml"})
})
public class MakingControllerTest  extends AbstractControllerTest{
	private static Logger logger = LoggerFactory.getLogger(MakingControllerTest.class);
	
	@Autowired
	@InjectMocks
	private MakingController controller;
	
	@Mock
	private ExamService examService;
	
	@Mock
	private ExamPaperService paperService;
	
	@Test
	public void testOnView()throws Exception{
		assertNotNull(controller);
//		ExamPaper paper  = new Paper.Builder("Test Paper").create();
//		Exam exam = new Exam.Builder("test exam").create();
//		//examService.load(1l);
//		when(examService.load(isA(Long.class))).thenReturn(exam);
//		when(paperService.load(isA(Long.class))).thenReturn(paper);

		this.mvc.perform(get("/marking/1/1/blockuuid"))
		.andExpect(view().name("/marking/index"))
		.andExpect(content().string(startsWith("<!DOCTYPE html>")))
		.andExpect(content().string(containsString("</html>")))
		.andExpect(content().string(endsWith("")));
	}
	
	@Test
	public void testOnMonitor()throws Exception{
		assertNotNull(controller);
//		Paper paper  = new Paper.Builder("Test Paper").create();
//		Exam exam = new Exam.Builder("test exam").create();
		//examService.load(1l);
//		when(examService.load(isA(Long.class))).thenReturn(exam);
//		when(paperService.load(isA(Long.class))).thenReturn(paper);

		this.mvc.perform(get("/marking/monitor/1/1/blockuuid"))
		.andExpect(view().name("/marking/monitorIndex"))
		.andExpect(content().string(startsWith("<!DOCTYPE html>")))
		.andExpect(content().string(containsString("</html>")))
		.andExpect(content().string(endsWith("")));
	}
}
