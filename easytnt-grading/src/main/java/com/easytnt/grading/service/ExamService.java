package com.easytnt.grading.service;

import com.easytnt.commons.entity.service.EntityService;
import com.easytnt.grading.domain.exam.Exam;

import freemarker.template.Configuration;

public interface ExamService extends EntityService<Exam, Long> {

	void reportingFor(Long examId,String reportingPath,Configuration config);
	
}
