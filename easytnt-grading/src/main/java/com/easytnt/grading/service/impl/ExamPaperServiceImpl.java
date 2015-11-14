package com.easytnt.grading.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.paper.PaperCard;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.repository.ExamPaperRepository;
import com.easytnt.grading.service.ExamPaperService;

@Service
public class ExamPaperServiceImpl extends AbstractEntityService<ExamPaper, Long>implements ExamPaperService {
	@Autowired(required = false)
	private ExamPaperRepository examPaperRepository;
	
	public ExamPaperServiceImpl() {
		
	}
	
	@Autowired(required=false)
	public void setRepository(ExamPaperRepository  repository) {
		this.examPaperRepository = repository;
		super.setRepository(repository);
	}
	
	@Transactional(readOnly=true)
	@Override
	public ExamPaper load(Long pk) {
		ExamPaper examPaper =  examPaperRepository.load(pk);
		return examPaper;
	}

	@Transactional(readOnly=true)
	@Override
	public void query(Query<ExamPaper> query) {
		// TODO Auto-generated method stub
	}

	@Transactional
	@Override
	public void deleteSectionFor(Long paperId, Section section) {
		ExamPaper examPaper = load(paperId);
		examPaper.removeSections(section);
		examPaperRepository.save(examPaper);
	}

	@Transactional
	@Override
	public void updateSectionFor(Long paperId, Section section,Integer position) {
		ExamPaper examPaper = load(paperId);
		examPaper.addSection(position, section);
		examPaperRepository.save(examPaper);
	}

	@Transactional
	@Override
	public void addSectionFor(Long paperId, Section section) {
		ExamPaper examPaper = load(paperId);
		examPaper.addSection(section);
		examPaperRepository.save(examPaper);
	}

	@Override
	public void addPaperCardFor(Long paperId, PaperCard paperCard) {
		ExamPaper examPaper = load(paperId);
		examPaper.addPaperCard(paperCard);
		examPaperRepository.save(examPaper);
	}

	@Override
	public void deletePaperCardFor(Long paperId, PaperCard paperCard) {
		ExamPaper examPaper = load(paperId);
		examPaper.removePaperCard(paperCard);
		examPaperRepository.save(examPaper);
	}
	

}
