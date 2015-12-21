/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.dispatcher.DispathcerManager;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.CuttingsImage;
import com.easytnt.grading.domain.grade.CuttingsImageGradeRecord;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.domain.grade.Teacher;
import com.easytnt.grading.repository.CuttingsAreaRepository;
import com.easytnt.grading.repository.CuttingsImageGradeRecordRepository;
import com.easytnt.grading.repository.GradeTaskRepository;
import com.easytnt.grading.repository.RefereesRepository;
import com.easytnt.grading.repository.TeacherRepository;
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

	@Autowired(required = false)
	private CuttingsImageGradeRecordRepository gradeRecordRepository;
	
	@Autowired
	private RefereesRepository refereesRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private	CuttingsAreaRepository cuttingsAreaRepository;

	@Override
	@Transactional(readOnly = true)
	public GradeTask getTaskOf(Long taskId, Referees referees) throws Exception {
		logger.debug("Task for {} with {}", referees, taskId);

		GradeTask task = taskRepository.load(taskId);

		if (task == null)
			throw new IllegalAccessException("无权访问此评卷任务");

		if (task.isFinished())
			throw new IllegalAccessException("评卷任务已经完成");
		task.setAssignedTo(referees);
		int total = taskRepository.countTaskTotal(taskId);
		int assignedToTotal = taskRepository.countAssignedTotal(taskId,referees.getId());
		task.setTaskTotal(total);
		task.setAssignedToTotal(assignedToTotal);
		Dispatcher dispatcher = dispathcerManager.getDispatcherFor(task.getArea());
		task.useDispatcher(dispatcher);
		return task;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Map<String,Map<String,String>> getMustRepeat(Long taskId, Referees referees){
		Teacher teacher = teacherRepository.load(referees.getId());
		if(teacher != null && teacher.isManager()) {
			return taskRepository.selectItemRepeat(taskId,referees.getId());
		}
		return null;
	}

	@Override
	@Transactional
	public void itemScoring(Long taskId, Referees referees, Float[] scores) throws Exception {
		GradeTask task = this.taskRepository.load(taskId);
		CuttingsImageGradeRecord imageGradeRecord = referees.scoringForItems(scores);
		logger.debug("Scoring ", imageGradeRecord);
		task.increment();
		// 数据持久化处理
		gradeRecordRepository.saveForScoring(imageGradeRecord);
		
		CuttingsImage cuttings = imageGradeRecord.getRecordFor();
		if(cuttings.isFinish()) {
			Float fScore = cuttings.calScore();
			if(fScore>=0) {
				gradeRecordRepository.saveLastScore(imageGradeRecord,fScore);
			}else {
				logger.info("add {} to leader queue ",cuttings);
				//dispathcerManager.
				gradeRecordRepository.saveRepeat(imageGradeRecord);
			}
		}
		//imageGradeRecord.
	}
	
	@Override
	@Transactional
	public void itemDirectScoringForValide(Referees referees,String uuid, Float[] scores) throws Exception {
		logger.debug("itemDirectScoring ", uuid);
		gradeRecordRepository.saveDirectScored(referees.getId(), uuid, scores);
	}

	@Override
	@Transactional
	public void itemBlank(Long taskId, Referees referees) throws Exception {
		GradeTask task = this.taskRepository.load(taskId);
		referees = task.getAssignedTo();
		CuttingsImageGradeRecord imageGradeRecord = referees.dealBlank();
		logger.debug("Scoring ", imageGradeRecord);
		task.increment();
		// 数据持久化处理
		gradeRecordRepository.saveForBlank(imageGradeRecord);
	}

	@Override
	@Transactional
	public void itemError(Long taskId, Referees referees,String reason) throws Exception {
		GradeTask task = taskRepository.load(taskId);
		//referees = task.getAssignedTo();

		CuttingsImageGradeRecord imageGradeRecord  = referees.dealError(reason);
		logger.debug("Scoring ", imageGradeRecord);
		task.increment();
		// 数据持久化处理
		gradeRecordRepository.saveForError(imageGradeRecord);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public CuttingsImageGradeRecord createImageGradeRecordBy(Long taskId, Referees referees) throws Exception {
		//GradeTask task = this.getTaskOf(taskId, referees);
		//task.setAssignedTo(referees);
		CuttingsImageGradeRecord imageGradeRecord = referees.fetchCuttings();
		// 数据持久化处理
		gradeRecordRepository.saveForFetching(imageGradeRecord);

		return imageGradeRecord;
	}

	@Override
	@Transactional
	public boolean recoverUndo(GradeTask task) {
		Referees referees = task.getAssignedTo();
		CuttingsImageGradeRecord gradeRecord = gradeRecordRepository.findUndoRecordOf(task);
		if (gradeRecord != null) {
			referees.recoverRecord(gradeRecord);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public void newTasckFor(Long cuttoId, Long refereesId) {
		Referees referees = this.refereesRepository.load(refereesId);
		CuttingsArea genBy = this.cuttingsAreaRepository.load(cuttoId);
		GradeTask task = GradeTask.createOfficialGradeTask(referees, genBy);
		this.taskRepository.save(task);
	}

	@Override
	@Transactional
	public void removeTasckFor(Long cuttoId, Long refereesId) {
		GradeTask task = this.taskRepository.findRefereesTask(cuttoId, refereesId);
		this.taskRepository.delete(task);
	}

	@Override
	@Transactional(readOnly=true)
	public List<GradeTask> getTaskOf(Long cuttoId) {
		return this.taskRepository.findGenTasks(cuttoId);
	}

	@Override
	public List<GradeTask> getTaskOf(Referees referees) {
		return this.taskRepository.findRefereesTasks(referees);
	}

}
