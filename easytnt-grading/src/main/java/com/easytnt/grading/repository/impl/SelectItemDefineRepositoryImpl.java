/**
 * 
 */
package com.easytnt.grading.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.cuttings.SelectItemDefine;
import com.easytnt.grading.repository.SelectItemDefineRepository;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@Repository
public class SelectItemDefineRepositoryImpl extends HibernateRepository<SelectItemDefine, Long>
		implements SelectItemDefineRepository {

	@Override
	protected Class<SelectItemDefine> getEntityClass() {
		return SelectItemDefine.class;
	}

	@Override
	public List<SelectItemDefine> listSelectItemDefines(Long paperId) {
		String hql = "from SelectItemDefine where paper.id=?";
		Query q = this.getCurrentSession().createQuery(hql);
		q.setLong(0, paperId);
		List<SelectItemDefine> selectItemDefines = q.list();
		return selectItemDefines;
	}

}
