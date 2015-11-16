package com.easytnt.grading.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.exam.Subject;
import com.easytnt.grading.repository.SubjectRepository;
/**
 * 科目的具体操作实现接口类
 * 
 * @author 钟水林 20151103
 *
 */
@Repository
public class SubjectRepositoryHibernateImpl extends HibernateRepository<Subject,Long> implements SubjectRepository {

	@Override
	public int getMaxCode() {
		Query q = getCurrentSession().createQuery("select max(subjectCode)+1 from Subject");
		List resultList = q.list();
		if(resultList.size()!=0){
			if(resultList.get(0) == null)
				return 100;
			return (Integer)resultList.get(0);
		}
		return 100;
	}

	@Override
	protected Class<Subject> getEntityClass() {
		return Subject.class;
	}
}
