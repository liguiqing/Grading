package com.easytnt.grading.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.entity.cqrs.Query;
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
		//exam.setName("Exam");
		return exam;
	}

	@Transactional(readOnly=true)
	@Override
	public void query(Query<Exam> query) {
		// TODO Auto-generated method stub
	}

}
