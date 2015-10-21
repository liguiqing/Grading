/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.controller;

import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.service.GradeTaskService;
import com.easytnt.grading.service.RefereesService;
import com.easytnt.test.controller.AbstractControllerTest;

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
	private static Logger logger = LoggerFactory
			.getLogger(GradingTaskControllerTest.class);

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

		Referees r1 = new Referees("test1");
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

		this.mvc.perform(get("/task/1"))
				.andExpect(view().name("/task/gradingTask"))
				.andExpect(content().string(startsWith("<!DOCTYPE html>")))
				.andExpect(content().string(containsString("</html>")));

	}

}
