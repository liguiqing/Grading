/**
 * 
 */
package com.easytnt.grading.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.CuttingsSolution;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.share.Area;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath*:applicationContext-*.xml" })
public class CuttingsSolutionServiceTest {

	@Autowired(required = false)
	private CuttingsSolutionService service;

	@Test
	public void saveCuttingsSolution() throws Exception {
		Assert.assertNotNull(service);
		// service.saveCuttingsSolution(null);
		System.out.println();
	}

	@Test
	public void saveCuttingArea() throws Exception {

		ExamPaper paper = new ExamPaper();
		paper.setPaperId(3L);

		Area area = new Area();
		area.setTop(10);
		area.setLeft(10);
		area.setWidth(10);
		area.setHeight(10);
		CuttingsArea cuttingArea = new CuttingsArea();
		cuttingArea.setAreaInPaper(area);
		cuttingArea.setName("abc");

		CuttingsSolution cuttingsSolution = new CuttingsSolution();
		cuttingsSolution.setDesignFor(paper);
		cuttingsSolution.newCuttingsDefines(cuttingArea);

		service.saveCuttingsSolution(cuttingsSolution);
	}
}
