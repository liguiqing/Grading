/**
 * 
 */
package com.easytnt.cutimage;

import java.util.ArrayList;
import java.util.List;

import com.easytnt.grading.domain.cuttings.AnswerCardCuttingTemplate;
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
public class MockCuttingsSolution {

	public static CuttingsSolution cuttingsSolution() {
		CuttingsSolution cuttingsSolution = new CuttingsSolution();
		ExamPaper examPaper = ExamPaper();
		cuttingsSolution.setDesignFor(examPaper);

		cuttingsSolution.newCuttingsDefines(CuttingsArea(1L, 0, 132, 1178, 1049, 230));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(2L, 0, 122, 1350, 1069, 722));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(3L, 0, 1164, 191, 1025, 272));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(4L, 0, 1173, 431, 1010, 735));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(5L, 0, 1165, 1113, 1021, 1005));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(6L, 0, 2194, 1035, 1023, 528));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(7L, 0, 2190, 1592, 1018, 516));

		cuttingsSolution.newCuttingsDefines(CuttingsArea(8L, 1, 78, 193, 1033, 547));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(9L, 1, 72, 715, 1054, 523));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(10L, 1, 80, 1181, 1049, 526));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(11L, 1, 79, 1641, 1037, 512));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(12L, 1, 1125, 200, 1009, 771));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(13L, 1, 1121, 940, 1027, 1219));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(14L, 1, 2146, 185, 1035, 991));
		cuttingsSolution.newCuttingsDefines(CuttingsArea(15L, 1, 2151, 1153, 1029, 1012));
		return cuttingsSolution;
	}

	private static ExamPaper ExamPaper() {
		List<AnswerCardCuttingTemplate> answerCardCuttingTemplates = new ArrayList<>();
		AnswerCardCuttingTemplate template = new AnswerCardCuttingTemplate();
		template.setIndex(0).setRotate(-90);
		answerCardCuttingTemplates.add(template);
		template = new AnswerCardCuttingTemplate();
		template.setIndex(1).setRotate(90);
		answerCardCuttingTemplates.add(template);

		ExamPaper examPaper = new ExamPaper();
		examPaper.setAnswerCardCuttingTemplates(answerCardCuttingTemplates);
		examPaper.setAnswerCardImageNum(2);
		examPaper.setPaperId(1000L);
		examPaper.setCuttingRootPath("D:/test/cuttingImage");
		examPaper.setStudentAnserCardRootPath("D:/test/tif/lizong");

		return examPaper;
	}

	private static CuttingsArea CuttingsArea(Long id, int answerCardImageIdx, int left, int top, int width,
			int height) {
		CuttingsArea area = new CuttingsArea();
		area.setId(id);
		area.setAnswerCardImageIdx(answerCardImageIdx);
		area.setAreaInPaper(new Area(left, top, width, height));
		return area;
	}
}
