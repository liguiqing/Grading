package com.easytnt.grading.service;

import com.easytnt.commons.entity.service.EntityService;
import com.easytnt.grading.domain.exam.Subject;
/**
 * 科目的具体操作服务类
 * 
 * @author 钟水林 20151103
 *
 */
public interface SubjectService extends EntityService<Subject, Long> {
	
	public int getMaxCode();
}
