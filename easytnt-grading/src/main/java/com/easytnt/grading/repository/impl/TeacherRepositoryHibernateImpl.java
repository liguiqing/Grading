package com.easytnt.grading.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
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
	
	//根据科目查询教师信息
	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> getTeacherSname(Long subject_id) {
		String hql = " from Teacher t where t.subject.id= "+subject_id;
		Query q = this.getCurrentSession().createQuery(hql);
		List<Teacher> tlist = q.list();
		if(tlist.size()>0){
			return tlist;
		}
		return null;
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
}
