package com.easytnt.grading.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.exam.SubjectExam;
import com.easytnt.grading.repository.SubjectExamRepository;

@Repository
public class SubjectExamRepositoryHibernateImpl extends HibernateRepository<SubjectExam,Long> implements SubjectExamRepository {

	
	
	@Override
	public SubjectExam load(Long id) {
		Query q = getCurrentSession().createQuery(" from SubjectExam where testId = "+id);
		List list = q.list();
		if(list.size()>0){
			return (SubjectExam) list.get(0);
		}
		return null;
	}

	@Override
	public List<SubjectExam> list() {
		Query q = getCurrentSession().createQuery(" from SubjectExam ");
		return  q.list();
	}

	@Override
	protected Class<SubjectExam> getEntityClass() {
		return SubjectExam.class;
	}
	
}
