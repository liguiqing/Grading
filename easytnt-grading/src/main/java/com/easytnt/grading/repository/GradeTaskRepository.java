/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository;

import java.util.List;

import com.easytnt.commons.entity.repository.Repository;
import com.easytnt.grading.domain.grade.GradeTask;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/
public interface GradeTaskRepository extends Repository<GradeTask, Long> {

	public GradeTask findRefereesTask(Long cuttoId, Long refereesId);

	public List<GradeTask> findGenTasks(Long cuttoId);
}