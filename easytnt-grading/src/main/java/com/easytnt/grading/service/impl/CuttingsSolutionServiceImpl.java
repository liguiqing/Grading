/**
 * 
 */
package com.easytnt.grading.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easytnt.grading.domain.cuttings.AnswerCardCuttingTemplate;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.CuttingsSolution;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.paper.PaperCard;
import com.easytnt.grading.repository.CuttingsAreaRepository;
import com.easytnt.grading.repository.ExamPaperRepository;
import com.easytnt.grading.service.CuttingsSolutionService;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@Service
public class CuttingsSolutionServiceImpl implements CuttingsSolutionService {
	@Autowired(required = false)
	private CuttingsAreaRepository cuttingsAreaRepository;
	@Autowired(required = false)
	private ExamPaperRepository examPaperRepository;

	@Override
	public void saveCuttingsSolution(CuttingsSolution cuttingsSolution) {
		ExamPaper paper = cuttingsSolution.getDesignFor();
		cuttingsAreaRepository.deleteCuttingAreaInPaper(paper.getPaperId());
		List<CuttingsArea> cuttingsAreas = cuttingsSolution.getCutTo();
		for (CuttingsArea cuttingsArea : cuttingsAreas) {
			cuttingsArea.setPaper(paper);
			cuttingsAreaRepository.saveOrUpdate(cuttingsArea);
		}
	}

	@Override
	public CuttingsSolution getCuttingsSolutionWithPaperId(Long paperId) {
		ExamPaper paper = getPaper(paperId);
		List<CuttingsArea> cuttingsAreas = getCuttingsAreas(paperId);
		CuttingsSolution cuttingsSolution = new CuttingsSolution();
		cuttingsSolution.setDesignFor(paper);
		cuttingsSolution.setCutTo(cuttingsAreas);
		return cuttingsSolution;
	}

	private List<CuttingsArea> getCuttingsAreas(Long paperId) {
		List<CuttingsArea> cuttingsAreas = cuttingsAreaRepository.listCuttingsAreaOfInPaper(paperId);

		return cuttingsAreas;
	}

	private ExamPaper getPaper(Long paperId) {
		ExamPaper paper = examPaperRepository.get(paperId);

		Set<PaperCard> paperCards = paper.getPaperCards();
		ArrayList<AnswerCardCuttingTemplate> answerCardCuttingTemplates = new ArrayList<>();
		int idx = 0;
		for (PaperCard paperCard : paperCards) {
			AnswerCardCuttingTemplate template = new AnswerCardCuttingTemplate();
			template.setUrl("examPaper/" + paperId + "/" + paperCard.getCardId());
			template.setIndex(paperCard.getCardSeq() - 1);
			// 转换角度设置
			template.setRotate(paperCard.getRotate());
			answerCardCuttingTemplates.add(template);
		}

		paper.setSubjectExam(null);
		paper.setSections(null);
		paper.setPaperCards(null);

		// 设置原图路径
		paper.setStudentAnserCardRootPath("D:/test/tif/lizong");
		// 设置切割路径
		paper.setAnswerCardCuttingTemplates(answerCardCuttingTemplates);
		paper.setCuttingRootPath("D:/test/cuttingImage");

		return paper;
	}

}
