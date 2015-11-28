package com.easytnt.grading.repository;

import com.easytnt.commons.entity.repository.Repository;
import com.easytnt.grading.domain.paper.ExamPaper;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/
public interface ExamPaperRepository extends Repository<ExamPaper, Long> {

	int countPapersFor(Long examPaperId);

}