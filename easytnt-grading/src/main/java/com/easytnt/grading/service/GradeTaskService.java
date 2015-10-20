/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.service;

import com.easytnt.commons.entity.service.EntityService;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.grade.PieceGradeRecord;
import com.easytnt.grading.domain.grade.Referees;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月17日
 * @version 1.0
 **/
public interface GradeTaskService extends EntityService<GradeTask, Long> {

	GradeTask getTaskOf(Long taskId,Referees referees) throws Exception;
	
	PieceGradeRecord createPieceGradeRecordBy(Long taskId,Referees referees) throws Exception;

	void itemScoring(Long taskId,Referees referees,Float[] scores) throws Exception;
}


