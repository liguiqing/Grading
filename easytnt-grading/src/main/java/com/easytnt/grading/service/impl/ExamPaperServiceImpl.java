package com.easytnt.grading.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.repository.ExamPaperRepository;
import com.easytnt.grading.service.ExamPaperService;

@Service
public class ExamPaperServiceImpl extends AbstractEntityService<ExamPaper,Long> implements ExamPaperService {

	private ExamPaperRepository paperRepository;
	
	@Autowired(required=false)
	public void setRepository(ExamPaperRepository  repository) {
		this.paperRepository = repository;
		super.setRepository(repository);
	}
	
	@Override
	public ExamPaper load(Long pk) {
		ExamPaper paper =  new ExamPaper();
		//paper.setName("Exam");
		return paper;
	}
}
