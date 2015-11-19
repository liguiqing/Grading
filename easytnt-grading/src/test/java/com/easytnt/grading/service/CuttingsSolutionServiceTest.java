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
import com.easytnt.grading.domain.cuttings.PositionOfItemInArea;
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
		CuttingsSolution cuttingsSolution = new CuttingsSolution();
		cuttingsSolution.setDesignFor(paper);
		cuttingsSolution.newCuttingsDefines(createCuttingsArea(null, "name1", 1));
		service.saveCuttingsSolution(cuttingsSolution);
	}

	private CuttingsArea createCuttingsArea(Long id, String name, int idx) {
		Area area = new Area();
		area.setTop(10);
		area.setLeft(10);
		area.setWidth(10);
		area.setHeight(10);

		CuttingsArea cuttingArea = new CuttingsArea();
		if (id != null) {
			cuttingArea.setId(id);
		}
		cuttingArea.setAreaInPaper(area);
		cuttingArea.setName(name);
		cuttingArea.setFullScore(10f);
		cuttingArea.setMaxerror(3f);

		cuttingArea.addItemDefinition(createPositionOfItemInArea(name + "-" + idx));
		cuttingArea.addItemDefinition(createPositionOfItemInArea(name + "-1" + (idx + 1)));
		cuttingArea.addItemDefinition(createPositionOfItemInArea(name + "-1" + (idx + 2)));

		return cuttingArea;
	}

	private PositionOfItemInArea createPositionOfItemInArea(String name) {
		PositionOfItemInArea item = new PositionOfItemInArea();
		item.setName(name);
		item.setFullScore(10f);
		item.setValidValues(new Float[] { 0f, 5f, 10f });
		item.setSeriesScore(false);
		item.setInterval(2f);
		return item;
	}

	@Test
	public void getCuttingsSolutionWithPaperIdTest() throws Exception {
		service.getCuttingsSolutionWithPaperId(3L);
	}
}
