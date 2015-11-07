/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.repository.RefereesRepository;
import com.easytnt.grading.service.RefereesService;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/

@Service
public class RefereesServiceImpl extends AbstractEntityService<Referees, Long> implements
		RefereesService {

	
	private RefereesRepository refereesRepository;
	
	@Override
	public Referees getCurrentReferees() {
		// TODO Auto-generated method stub
		return  null;
	}

}


