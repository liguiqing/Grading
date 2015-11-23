package com.easytnt.grading.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.entity.repository.Repository;
import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.commons.io.ListDataSourceMapper;
import com.easytnt.commons.io.ListDataSourceReader;
import com.easytnt.grading.domain.room.Examinee;
import com.easytnt.grading.repository.ExamineeRepository;
import com.easytnt.grading.service.ExamineeService;

@Service
public class ExamineeServiceImpl extends AbstractEntityService<Examinee, Long> implements ExamineeService{
	
	
	private ExamineeRepository examineeRepository;

	@Transactional
	@Override
	public void imports(ListDataSourceMapper mapper, ListDataSourceReader reader) {
		try {
			examineeRepository.insertImports(mapper, reader);
		}catch(Exception e) {
			logger.error(ThrowableParser.toString(e));
		}
	}
	
	@Autowired
	public void setRepository(ExamineeRepository examineeRepository) {
		this.examineeRepository = examineeRepository;
		super.setRepository(examineeRepository);
	}

}