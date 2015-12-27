package com.easytnt.grading.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.grading.dispatcher.DispathcerManager;
import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.repository.ExamRepository;
import com.easytnt.grading.repository.impl.ExamineeFinalScoreCalculator;
import com.easytnt.grading.service.ExamService;
import com.easytnt.reporting.out.FreemarkerHtmlOutput;

import freemarker.template.Configuration;

@Service
public class ExamServiceImpl extends AbstractEntityService<Exam, Long>implements ExamService {

	private ExamRepository examRepository;
	
	@Autowired(required = false)
	private DispathcerManager dispathcerManager;

	public ExamServiceImpl() {
		
	}
	
	@Autowired(required=false)
	public void setRepository(ExamRepository  repository) {
		this.examRepository = repository;
		super.setRepository(repository);
	}
	
	@Override
	public Exam load(Long pk) {
		Exam exam =  this.examRepository.load(pk);
		return exam;
	}

	@Transactional(readOnly=true)
	@Override
	public void query(Query<Exam> query) {
		// TODO Auto-generated method stub
	}

	@Override
	@Transactional
	public void reportingFor(Long examId,String reportingPath,Configuration config) {
		ExamineeFinalScoreCalculator calculator =  this.examRepository.createFinalScoreCalculator(examId);
		calculator.ranking();
		calculator.reportingOutput(reportingPath,new FreemarkerHtmlOutput(config,"/report.ftl"));
		calculator.scoreListOutput(reportingPath,new FreemarkerHtmlOutput(config,"/scoreList.ftl"));
		Exam exam = this.examRepository.load(examId);
		exam.finished();
		dispathcerManager.destroy();
	}

}
