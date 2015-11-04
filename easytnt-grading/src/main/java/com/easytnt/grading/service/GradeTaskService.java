/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.service;

import com.easytnt.commons.entity.service.EntityService;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.grade.CuttingsImageGradeRecord;
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
	
	CuttingsImageGradeRecord createImageGradeRecordBy(Long taskId,Referees referees) throws Exception;

	void itemScoring(Long taskId,Referees referees,Float[] scores) throws Exception;

	/**
	 * 恢复没有完成的任务
	 * @param task
	 * @return　boolean 有需要恢复的任务返回true，否则返回false
	 */
	boolean recoverUndo(GradeTask task);

	void itemBlank(Long taskId, Referees referees)
			throws Exception;

	void itemError(Long taskId, Referees referees)
			throws Exception;

}


