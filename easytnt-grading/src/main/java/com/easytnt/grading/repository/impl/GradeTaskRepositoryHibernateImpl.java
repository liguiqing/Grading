/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.repository.GradeTaskRepository;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月24日
 * @version 1.0
 **/
@Repository
public class GradeTaskRepositoryHibernateImpl extends
		HibernateRepository<GradeTask, Long> implements GradeTaskRepository {

	@Override
	public GradeTask findRefereesTask(Long cuttoId, Long refereesId) {
		Criteria criteria = this.getCurrentSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("genBy.id", cuttoId));
		criteria.add(Restrictions.eq("assignedTo.id", refereesId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (GradeTask) criteria.uniqueResult();
	}

	@Override
	protected Class<GradeTask> getEntityClass() {
		return GradeTask.class;
	}

	@Override
	public List<GradeTask> findGenTasks(Long cuttoId) {
		Criteria criteria = this.getCurrentSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("genBy.id", cuttoId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public List<GradeTask> findRefereesTasks(Referees referees) {
		Criteria criteria = this.getCurrentSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.eq("assignedTo.id", referees.getId()));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public int countTaskTotal(Long taskId) {
		String sql = "SELECT COUNT(a.imagepath) total FROM paperimport a INNER JOIN grade_task b ON a.itemid=b.item_id WHERE b.task_id=?";
		Query query = getCurrentSession().createSQLQuery(sql);
		query.setLong(0, taskId);
		BigInteger o = (BigInteger)query.uniqueResult();
		return o.intValue();
	}

	@Override
	public int countAssignedTotal(Long taskId, Long refereesId) {
		String sql = "SELECT COUNT(a.itemid) FROM scoreinfolog a INNER JOIN grade_task b ON a.itemid=b.item_id  WHERE b.task_id=? AND teacheroid=?";
		Query query = getCurrentSession().createSQLQuery(sql);
		query.setLong(0, taskId);
		query.setLong(1, refereesId);
		BigInteger o = (BigInteger)query.uniqueResult();
		return o.intValue();
	}

}


