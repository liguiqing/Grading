/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.service.impl;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.dispatcher.DispathcerManager;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.grade.ItemGradeRecord;
import com.easytnt.grading.domain.grade.CuttingsImageGradeRecord;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.repository.GradeTaskRepository;
import com.easytnt.grading.service.GradeTaskService;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月17日
 * @version 1.0
 **/
@Service
public class GradeTaskServiceImpl extends AbstractEntityService<GradeTask, Long>implements GradeTaskService {

	@Autowired(required = false)
	private DispathcerManager dispathcerManager;

	@Autowired(required = false)
	private GradeTaskRepository taskRepository;

	@Override
	@Transactional(readOnly = true)
	public GradeTask getTaskOf(Long taskId, Referees referees) throws Exception {
		logger.debug("Task for {} with {}", referees, taskId);

		GradeTask task = taskRepository.load(taskId);

		if (task == null)
			throw new IllegalAccessException("无权访问此评卷任务");

		if (task.isFinished())
			throw new IllegalAccessException("评卷任务已经完成");

		Dispatcher dispatcher = dispathcerManager.getDispatcherFor(task.getArea());
		task.useDispatcher(dispatcher);
		return task;
	}

	@Override
	@Transactional
	public void itemScoring(Long taskId, Referees referees, Float[] scores) throws Exception {
		GradeTask task = taskRepository.load(taskId);
		referees = task.getAssignedTo();
		CuttingsImageGradeRecord gradeRecord  = referees.scoringForItems(scores);
		logger.debug("Scoring ", gradeRecord);
		task.increment();
		// 数据持久化处理
		// TODO
		//CuttingsImageGradeRecord gradeRecord = null;
	}

	@Override
	public CuttingsImageGradeRecord createPieceGradeRecordBy(Long taskId, Referees referees) throws Exception {
		GradeTask task = taskRepository.load(taskId);

		CuttingsImageGradeRecord pieceGradeRecord = task.getAssignedTo().fetchCuttings();
		// 数据持久化处理
		// TODO

		return pieceGradeRecord;
	}

}
