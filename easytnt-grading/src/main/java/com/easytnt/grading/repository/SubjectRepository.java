package com.easytnt.grading.repository;

import com.easytnt.commons.entity.repository.Repository;
import com.easytnt.grading.domain.exam.Subject;

/**
 * 科目的具体操作接口类
 * 
 * @author 钟水林 20151103
 *
 */
public interface SubjectRepository extends Repository<Subject, Long> {
	public int getMaxCode();
}
