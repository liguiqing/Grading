package com.easytnt.grading.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.grading.domain.paper.Paper;
import com.easytnt.grading.repository.PaperRepository;
import com.easytnt.grading.service.PaperService;

@Service
public class PaperServiceImpl extends  AbstractEntityService<Paper,Long> implements PaperService {

	private PaperRepository paperRepository;
	
	@Autowired(required=false)
	public void setRepository(PaperRepository  repository) {
		this.paperRepository = repository;
		super.setRepository(repository);
	}
	
	@Override
	public Paper load(Long pk) {
		Paper paper =  new Paper();
		paper.setName("Exam");
		return paper;
	}
}
