/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.controller;

import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.CuttingsImage;
import com.easytnt.grading.domain.grade.CuttingsImageGradeRecord;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.service.GradeTaskService;
import com.easytnt.grading.service.RefereesService;
import com.easytnt.test.controller.AbstractControllerTest;

import com.google.gson.Gson;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/

@ContextHierarchy({
		@ContextConfiguration(classes = { GradingTaskController.class }),
		@ContextConfiguration(locations = { "classpath:servlet-context.xml" })

})
public class GradingTaskControllerTest extends AbstractControllerTest {
	private static Logger logger = LoggerFactory.getLogger(GradingTaskControllerTest.class);

	@Autowired
	@InjectMocks
	private GradingTaskController controller;

	@Mock
	private RefereesService refereesService;

	@Mock
	private GradeTaskService taskService;

	@Test
	public void testOnGetTask() throws Exception {
		assertNotNull(controller);

		Referees r1 = mock(Referees.class);
		CuttingsImageGradeRecord pieceGradeRecord = mock(CuttingsImageGradeRecord.class);
		CuttingsImage ci = mock(CuttingsImage.class);
		
		CuttingsArea area = new CuttingsArea();
		area.setId(1l);
		Section section = new Section();
		section.setTitle("二、填空题");
		section.setCaption("二、填空题");
		area.bindSection(section);
		GradeTask task = GradeTask.createOfficialGradeTask(r1, area);
		
		when(refereesService.getCurrentReferees()).thenReturn(r1);
		when(taskService.getTaskOf(isA(Long.class), isA(Referees.class)))
				.thenReturn(task);
		when(r1.fetchCuttings()).thenReturn(pieceGradeRecord);
		when(pieceGradeRecord.getRecordFor()).thenReturn(ci);
		when(ci.getSections()).thenReturn(new ArrayList());
		this.mvc.perform(get("/task/1"))
				.andExpect(view().name("/task/gradingTask"))
				.andExpect(content().string(startsWith("<!DOCTYPE html>")))
				.andExpect(content().string(containsString("</html>")));

	}
	
	@Test
	public void testGetCuttings() throws Exception {
		assertNotNull(controller);

		Referees r1 = mock(Referees.class);
		CuttingsImageGradeRecord pieceGradeRecord = mock(CuttingsImageGradeRecord.class);
		CuttingsImage cuttings	= mock(CuttingsImage.class);

		when(refereesService.getCurrentReferees()).thenReturn(r1);
		when(taskService.createImageGradeRecordBy(isA(Long.class), isA(Referees.class))).thenReturn(pieceGradeRecord);
		when(pieceGradeRecord.getRecordFor()).thenReturn(cuttings);
		when(cuttings.getImgPath()).thenReturn("http://localhost/1.jpg");
		when(cuttings.getImageId()).thenReturn(1l);
		this.mvc.perform(get("/task/1/cuttings").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
				.andExpect(jsonPath("$.imgPath", is("http://localhost/1.jpg")))
				.andExpect(jsonPath("$.imageId", is(1)));

	}
	
	@Test
	public void testOnScoring()throws Exception{
		assertNotNull(controller);
		Referees r1 = mock(Referees.class);
		when(refereesService.getCurrentReferees()).thenReturn(r1);
		doNothing().when(taskService).itemScoring(isA(Long.class), isA(Referees.class),isA(Float[].class));
		Float[] scores= new Float[] {1.0f,1.0f,1.0f,1.0f};
		Gson gson =  new Gson();
		String content = gson.toJson(scores, Float[].class);
		logger.debug(content);
		this.mvc.perform(post("/task/1/itemscoring").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(content))
				.andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));
	}

}
