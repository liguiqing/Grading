/**
 * 
 */
package com.easytnt.cutimage;

import java.util.ArrayList;
import java.util.List;

import com.easytnt.grading.domain.cuttings.AnswerCardCuttingTemplate;
import com.easytnt.grading.domain.cuttings.CuttingDefine;
import com.easytnt.grading.domain.cuttings.CuttingSolution;
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

	public static CuttingSolution cuttingsSolution() {
		CuttingSolution cuttingsSolution = new CuttingSolution();
		ExamPaper paper = ExamPaper();
		cuttingsSolution.setPaper(paper);

		cuttingsSolution.addCuttingDefine(cuttingDefine(1L, "1", 0, 132, 1178, 1049, 230));
		cuttingsSolution.addCuttingDefine(cuttingDefine(2L, "1", 0, 122, 1350, 1069, 722));
		cuttingsSolution.addCuttingDefine(cuttingDefine(3L, "1", 0, 1164, 191, 1025, 272));
		cuttingsSolution.addCuttingDefine(cuttingDefine(4L, "2", 0, 1173, 431, 1010, 735));
		cuttingsSolution.addCuttingDefine(cuttingDefine(5L, "3", 0, 1165, 1113, 1021, 1005));
		cuttingsSolution.addCuttingDefine(cuttingDefine(6L, "4", 0, 2194, 1035, 1023, 528));
		cuttingsSolution.addCuttingDefine(cuttingDefine(7L, "5", 0, 2190, 1592, 1018, 516));

		cuttingsSolution.addCuttingDefine(cuttingDefine(8L, "6", 1, 78, 193, 1033, 547));
		cuttingsSolution.addCuttingDefine(cuttingDefine(9L, "7", 1, 72, 715, 1054, 523));
		cuttingsSolution.addCuttingDefine(cuttingDefine(10L, "8", 1, 80, 1181, 1049, 526));
		cuttingsSolution.addCuttingDefine(cuttingDefine(11L, "9", 1, 79, 1641, 1037, 512));
		cuttingsSolution.addCuttingDefine(cuttingDefine(12L, "10", 1, 1125, 200, 1009, 771));
		cuttingsSolution.addCuttingDefine(cuttingDefine(13L, "11", 1, 1121, 940, 1027, 1219));
		cuttingsSolution.addCuttingDefine(cuttingDefine(14L, "12", 1, 2146, 185, 1035, 991));
		cuttingsSolution.addCuttingDefine(cuttingDefine(15L, "13", 1, 2151, 1153, 1029, 1012));
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

	private static CuttingDefine cuttingDefine(Long id, String name, int answerCardImageIdx, int left, int top,
			int width, int height) {
		CuttingDefine cutDef = new CuttingDefine();
		cutDef.setId(id);
		cutDef.setName(name);
		cutDef.setAnswerCardImageIdx(answerCardImageIdx);
		cutDef.setArea(new Area(left, top, width, height));
		return cutDef;
	}
}
