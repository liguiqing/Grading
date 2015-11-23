package com.easytnt.grading.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.grade.Teacher;
import com.easytnt.grading.repository.TeacherRepository;

@Repository
public class TeacherRepositoryHibernateImpl extends HibernateRepository<Teacher,Long> implements TeacherRepository {
	
	@Override
	public int selectMaxSeqOf(Long subjectId,int leader) {
		String sql = "select max(teacher_account + 0) seq  from  teacher_info where subject_id=? and leader=?";
		Query query = this.getCurrentSession().createSQLQuery(sql);
		query.setLong(0, subjectId);
		query.setInteger(1, leader);
		Object o = query.uniqueResult();
		if(o == null)
			return 0;
		return ((Double)o).intValue();
	}
	
	@Override
	public List<Teacher> selectTeachersOfSubject(Long subjectId) {
		Criteria criteria = this.getCurrentSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("subject.id", subjectId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.addOrder(Order.desc("leader"));
		return criteria.list();
	}
	
	@Override
	public void query(com.easytnt.commons.entity.cqrs.Query<Teacher> query) {

		Criteria criteria = this.getCurrentSession().createCriteria(getEntityClass());
		if(query.parameterOf("subjectid") != null) {
			Long subjectId = Long.valueOf(query.parameterOf("subjectid"));
			if(subjectId>0) {
				criteria.add(Restrictions.eq("subject.id", subjectId));			
			}
		}

		
		ProjectionList ps = Projections.projectionList();
		ps.add(Projections.rowCount());
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		Object o = criteria.setProjection(ps).uniqueResult();
		Long rowsTotal = (Long)o;
		query.rows(rowsTotal.intValue());
		criteria.setProjection(null);
		
		criteria.setFirstResult(query.getStartRow());
		criteria.setMaxResults(query.getPageSize());
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		query.result(criteria.list());
	}
	
	@Override
	protected Class<Teacher> getEntityClass() {
		return Teacher.class;
	}

	@Override
	public Teacher selectTeacherAnHisTask(String teacherAccount) {
		Criteria criteria = this.getCurrentSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("teacherAccount", teacherAccount));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		Teacher t = (Teacher)criteria.uniqueResult();
		return t;
	}
}
