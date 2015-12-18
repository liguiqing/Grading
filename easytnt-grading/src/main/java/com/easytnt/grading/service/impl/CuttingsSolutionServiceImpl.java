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
import com.easytnt.grading.domain.cuttings.CuttingBlock;
import com.easytnt.grading.domain.cuttings.CuttingDefine;
import com.easytnt.grading.domain.cuttings.CuttingSolution;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
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
	public void saveCuttingAreaes(ExamPaper paper, List<CuttingBlock> cuttingBlocks) {
		cuttingsAreaRepository.deleteCuttingAreaInPaper(paper.getPaperId());
		for (CuttingBlock cutBlock : cuttingBlocks) {
			CuttingsArea cutArea = cutBlock.toCuttingsArea();
			cutArea.setPaper(paper);
			cuttingsAreaRepository.saveOrUpdate(cutArea);
			Long id = cutArea.getId();
			cutBlock.setKey(id);
		}
	}

	@Override
	public List<CuttingsArea> getCuttingAreaes(Long paperId) {
		return cuttingsAreaRepository.listCuttingsAreaOfInPaper(paperId);
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

		// paper.setSubjectExam(null);
		// paper.setSections(null);
		// paper.setPaperCards(null);

		paper.setAnswerCardCuttingTemplates(answerCardCuttingTemplates);
		String cuttingRootPath = paper.getCuttingRootPath();
		if (!cuttingRootPath.endsWith("/") && !cuttingRootPath.endsWith("\\")) {
			cuttingRootPath += "/";
			paper.setCuttingRootPath(cuttingRootPath);
		}

		// ExamPaper newpaper = new ExamPaper();
		// newpaper.setPaperId(paperId);
		// newpaper.setName(paper.getName());
		// newpaper.setFullScore(paper.getFullScore());
		// newpaper.setObjectivityScore(paper.getObjectivityScore());
		// newpaper.setSubjectivityScore(paper.getSubjectivityScore());
		// newpaper.setAnswerCardImageNum(paper.getAnswerCardImageNum());
		// newpaper.setAnswerCardCuttingTemplates(paper.getAnswerCardCuttingTemplates());
		// newpaper.setCuttingRootPath(paper.getCuttingRootPath());
		// newpaper.setStudentAnserCardRootPath(paper.getStudentAnserCardRootPath());

		return paper;
	}

}
