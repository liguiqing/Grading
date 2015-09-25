package com.joy.grading.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joy.commons.entity.service.AbstractEntityService;
import com.joy.grading.domain.exam.Exam;
import com.joy.grading.repository.ExamRepository;
import com.joy.grading.service.ExamService;

@Service
public class ExamServiceImpl extends AbstractEntityService<Exam, Long>implements ExamService {

	private ExamRepository examRepository;
	
	public ExamServiceImpl() {
		
	}
	
	@Autowired(required=false)
	public void setRepository(ExamRepository  repository) {
		this.examRepository = repository;
		super.setRepository(repository);
	}
	
	@Override
	public Exam load(Long pk) {
		Exam exam =  new Exam();
		exam.setName("Exam");
		return exam;
	}

}