/**
 * 
 */
package com.easytnt.grading.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.easytnt.grading.domain.cuttings.CuttingDefine;
import com.easytnt.grading.domain.cuttings.CuttingsSolution;
import com.easytnt.grading.domain.cuttings.GiveScorePoint;
import com.easytnt.grading.domain.paper.ExamPaper;

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
	public void saveCuttingDefines() throws Exception {
		ExamPaper paper = new ExamPaper();
		paper.setPaperId(3L);

		CuttingDefine cuttingDefine = new CuttingDefine();
		cuttingDefine.setName("第一题");
		cuttingDefine.addGiveScorePoint(createGiveScorePoint("1", 5, "0,1,2,3,4,5"));
		cuttingDefine.addGiveScorePoint(createGiveScorePoint("2", 5, "0,1,2,3,4,5"));

		CuttingsSolution cuttingsSolution = new CuttingsSolution();
		cuttingsSolution.setPaper(paper);
		cuttingsSolution.addCuttingDefine(cuttingDefine);

		service.saveCuttingDefines(cuttingsSolution);
	}

	private GiveScorePoint createGiveScorePoint(String name, float fullScore, String validscoredot) {
		GiveScorePoint gsp = new GiveScorePoint();
		gsp.setName(name).setFullScore(fullScore).setValidscoredot(validscoredot);
		return gsp;
	}

	@Test
	public void getCuttingDefines() throws Exception {
		CuttingsSolution cuttingsSolution = service.getCuttingDefines(3L);
		System.out.println();
	}
}
