package com.easytnt.grading.repository;

import com.easytnt.commons.entity.repository.Repository;
import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.repository.impl.ExamineeFinalScoreCalculator;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/
public interface ExamRepository extends Repository<Exam, Long> {

	ExamineeFinalScoreCalculator createFinalScoreCalculator(Long examId);

}