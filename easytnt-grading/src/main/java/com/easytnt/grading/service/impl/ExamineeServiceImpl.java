package com.easytnt.grading.service.impl;

import org.springframework.stereotype.Service;

import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.commons.io.ListDataSourceMapper;
import com.easytnt.commons.io.ListDataSourceReader;
import com.easytnt.grading.domain.room.Examinee;
import com.easytnt.grading.service.ExamineeService;

@Service
public class ExamineeServiceImpl extends AbstractEntityService<Examinee, Long> implements ExamineeService{
	
	//@Autowired(required = false)
	//private ExamineeRepository examineeRepository;

	@Override
	public void imports(ListDataSourceMapper mapper, ListDataSourceReader reader) {
		//return examineeRepository.insertImports(mapper, reader);
	}


}
