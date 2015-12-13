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
import com.easytnt.grading.domain.cuttings.CuttingDefine;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.CuttingSolution;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.paper.PaperCard;
import com.easytnt.grading.repository.CuttingDefineRepository;
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
	@Autowired(required = false)
	private CuttingDefineRepository cuttingDefineRepo;

	@Override
	public void saveCuttingDefines(CuttingSolution cuttingsSolution) {
		ExamPaper paper = cuttingsSolution.getPaper();
		cuttingDefineRepo.deleteCuttingDefinesWith(paper.getPaperId());
		List<CuttingDefine> cuttingDefines = cuttingsSolution.getCuttingDefines();
		for (CuttingDefine cuttingDefine : cuttingDefines) {
			cuttingDefine.setPaper(paper);
			cuttingDefineRepo.saveOrUpdate(cuttingDefine);
		}
	}

	@Override
	public CuttingSolution getCuttingDefines(Long paperId) {
		ExamPaper paper = getPaper(paperId);
		List<CuttingDefine> cuttingDefines = cuttingDefineRepo.listCuttingDefinesWith(paperId);
		CuttingSolution cuttingsSolution = new CuttingSolution();
		cuttingsSolution.setPaper(paper);
		cuttingsSolution.setCuttingDefines(cuttingDefines);
		return cuttingsSolution;
	}

	@Override
	public void saveCuttingAreaes(CuttingSolution cuttingsSolution) {
		// TODO Auto-generated method stub

	}

	@Override
	public CuttingSolution getCuttingAreaes(Long paperId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveCuttingsSolution1(CuttingSolution cuttingsSolution) {
		ExamPaper paper = cuttingsSolution.getPaper();
		paper = examPaperRepository.load(paper.getPaperId());
		cuttingsAreaRepository.deleteCuttingAreaInPaper(paper.getPaperId());
		// List<CuttingsArea> cuttingsAreas = cuttingsSolution.getCutTo();
		// for (CuttingsArea cuttingsArea : cuttingsAreas) {
		// cuttingsArea.setPaper(paper);
		// cuttingsAreaRepository.saveOrUpdate(cuttingsArea);
		// }
	}

	public CuttingSolution getCuttingsSolutionWithPaperId1(Long paperId) {
		ExamPaper paper = getPaper(paperId);
		CuttingSolution cuttingsSolution = new CuttingSolution();
		cuttingsSolution.setPaper(paper);
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

		paper.setAnswerCardCuttingTemplates(answerCardCuttingTemplates);
		String cuttingRootPath = paper.getCuttingRootPath();
		if (!cuttingRootPath.endsWith("/") && !cuttingRootPath.endsWith("\\")) {
			cuttingRootPath += "/";
			paper.setCuttingRootPath(cuttingRootPath);
		}
		// // 设置原图路径
		// paper.setStudentAnserCardRootPath("D:/test/tif/lizong");
		// // 设置切割路径
		// paper.setCuttingRootPath("D:/test/cuttingImage");

		return paper;
	}

}
