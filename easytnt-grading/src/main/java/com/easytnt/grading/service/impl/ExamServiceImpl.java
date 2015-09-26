package com.easytnt.grading.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.repository.ExamRepository;
import com.easytnt.grading.service.ExamService;

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
