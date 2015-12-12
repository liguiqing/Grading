/**
 * 
 */
package com.easytnt.grading.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.cuttings.CuttingDefine;
import com.easytnt.grading.repository.CuttingDefineRepository;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@Repository
public class CuttingDefineRepositoryImpl extends HibernateRepository<CuttingDefine, Long>
		implements CuttingDefineRepository {

	@Override
	protected Class<CuttingDefine> getEntityClass() {
		return CuttingDefine.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.easytnt.grading.repository.CuttingDefineRepository#
	 * listCuttingsAreaOfInPaper(java.lang.Long)
	 */
	@Override
	public List<CuttingDefine> listCuttingDefinesWith(Long paperId) {
		String hql = "from CuttingDefine where paper.id=?";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setLong(0, paperId);
		List<CuttingDefine> cuttingDefines = q.list();
		return cuttingDefines;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.easytnt.grading.repository.CuttingDefineRepository#
	 * deleteCuttingAreaInPaper(java.lang.Long)
	 */
	@Override
	public void deleteCuttingDefinesWith(Long paperId) {
		String sql = "DELETE a FROM giveScorePoint a INNER JOIN cuttingDefine b ON a.cuttingDefineId=b.id WHERE b.paperid=?";
		SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
		q.setLong(0, paperId);
		q.executeUpdate();
		sql = "DELETE FROM cuttingDefine WHERE paperid=?";
		q = this.getCurrentSession().createSQLQuery(sql);
		q.setLong(0, paperId);
		q.executeUpdate();
	}

}
