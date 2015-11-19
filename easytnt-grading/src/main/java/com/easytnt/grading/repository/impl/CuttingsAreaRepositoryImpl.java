/**
 * 
 */
package com.easytnt.grading.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.repository.CuttingsAreaRepository;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@Repository
public class CuttingsAreaRepositoryImpl extends HibernateRepository<CuttingsArea, Long>
		implements CuttingsAreaRepository {

	@Override
	protected Class<CuttingsArea> getEntityClass() {
		return CuttingsArea.class;
	}

	@Override
	public List<CuttingsArea> listCuttingsAreaOfInPaper(Long paperId) {
		String hql = "from CuttingsArea where paper.id=" + paperId;
		Query q = this.getCurrentSession().createQuery(hql);
		List<CuttingsArea> cuttingsAreas = q.list();
		return cuttingsAreas;
	}

	@Override
	public void deletePositionOfItemInAreas() {
		String hql = "delete from PositionOfItemInArea where cuttingsArea is null";
		Query q = this.getCurrentSession().createQuery(hql);
		q.executeUpdate();
	}

}
