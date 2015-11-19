package com.easytnt.grading.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.grading.domain.exam.SubjectExam;
import com.easytnt.grading.repository.SubjectExamRepository;
import com.easytnt.grading.service.SubjectExamService;

@Service
public class SubjectExamServiceImpl extends AbstractEntityService<SubjectExam, Long>implements SubjectExamService {
	@Autowired(required = false)
	private SubjectExamRepository subjectExamRepository;
	
	public SubjectExamServiceImpl() {
		
	}
	
	@Autowired(required=false)
	public void setRepository(SubjectExamRepository  subjectExamRepository) {
		this.subjectExamRepository = subjectExamRepository;
		super.setRepository(repository);
	}
	
	@Override
	public SubjectExam load(Long pk) {
		return subjectExamRepository.load(pk);
	}

	@Transactional(readOnly=true)
	@Override
	public void query(Query<SubjectExam> query) {
		// TODO Auto-generated method stub
	}

	@Override
	public void create(SubjectExam t) {
		subjectExamRepository.save(t);
	}

	
	
	@Override
	public void update(SubjectExam t) {
		subjectExamRepository.update(t);
	}

	@Override
	public void delete(SubjectExam t) {
		subjectExamRepository.delete(t);
	}

	@Override
	public List<SubjectExam> list() {
		return subjectExamRepository.list();
	}
	
	
}
