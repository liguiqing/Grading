/**
 * 
 */
package com.easytnt.grading.repository.impl;

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

}
