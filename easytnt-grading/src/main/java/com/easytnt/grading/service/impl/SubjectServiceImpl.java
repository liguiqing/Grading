package com.easytnt.grading.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.domain.exam.Subject;
import com.easytnt.grading.repository.ExamRepository;
import com.easytnt.grading.repository.SubjectRepository;
import com.easytnt.grading.service.SubjectService;
/**
 * 科目的具体操作服务实现类
 * 
 * @author 钟水林 20151103
 *
 */
@Service
public class SubjectServiceImpl  extends AbstractEntityService<Subject, Long> implements SubjectService {
	@SuppressWarnings("unused")
	private SubjectRepository subjectRepository;
	
	public SubjectServiceImpl() {
		
	}
	
	@Autowired
	public void setRepository(SubjectRepository  repository) {
		this.subjectRepository = repository;
		super.setRepository(repository);
	}
	

	@Override
	public int getMaxCode() {
		return subjectRepository.getMaxCode();
	}

}
