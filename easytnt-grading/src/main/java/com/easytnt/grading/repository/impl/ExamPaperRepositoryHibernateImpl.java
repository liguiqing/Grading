package com.easytnt.grading.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.exam.SubjectExam;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.repository.ExamPaperRepository;

@Repository
public class ExamPaperRepositoryHibernateImpl extends HibernateRepository<ExamPaper,Long> implements ExamPaperRepository {

	@Override
	public ExamPaper load(Long id) {
		Query q = getCurrentSession().createQuery(" from ExamPaper where paperId = "+id);
		List list = q.list();
		if(list.size()>0){
			return (ExamPaper) list.get(0);
		}
		return null;
	}

	@Override
	protected Class<ExamPaper> getEntityClass() {
		return ExamPaper.class;
	}
	
	
}
