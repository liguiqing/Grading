/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository.impl;

import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.repository.RefereesRepository;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月24日
 * @version 1.0
 **/
@Repository
public class RefereesRepositoryHibernateImpl extends HibernateRepository<Referees, Long>
		implements RefereesRepository {

	@Override
	protected Class<Referees> getEntityClass() {
		return Referees.class;
	}

}


