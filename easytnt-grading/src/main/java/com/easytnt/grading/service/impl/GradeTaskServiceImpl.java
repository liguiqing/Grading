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
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.grade.ItemGradeRecord;
import com.easytnt.grading.domain.grade.PieceGradeRecord;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.domain.paper.Section;
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
public class GradeTaskServiceImpl extends AbstractEntityService<GradeTask,Long> implements GradeTaskService {

	@Autowired
	private DispathcerManager dispathcerManager;
	
	@Override
	@Transactional(readOnly=true)
	public GradeTask getTaskOf(Long taskId,Referees referees) throws Exception{
		logger.debug("Task for {} with {}",referees,taskId);
		//TODO
		CuttingsArea area = new CuttingsArea();
		Section section = new Section();
		section.setTitle("二、填空题");
		section.setCaption("二、填空题");
		GradeTask task = GradeTask.createOfficialGradeTask(referees, area);

		if(task == null)
			throw new IllegalAccessException("无权访问此评卷任务");
		
		if(task.isFinished())
			throw new IllegalAccessException("评卷任务已经完成");

		Dispatcher dispatcher = dispathcerManager.getDispatcherFor(task.getArea());
		task.useDispatcher(dispatcher);
		return task;
	}


	@Override
	@Transactional
	public void itemScoring(Long taskId,Referees referees, Float[] scores) throws Exception {
		GradeTask task = this.load(taskId);
		Collection<ItemGradeRecord> itemRecords = referees.scoringForItems(scores);
		logger.debug("Scoring ",itemRecords);
		task.increment();
		//数据持久化处理
		//TODO
		PieceGradeRecord gradeRecord = null;
	    Iterator<ItemGradeRecord> it = itemRecords.iterator();
	    while(it.hasNext()) {
	    	if(gradeRecord == null)
	    		gradeRecord = it.next().getSource();
	    }
	}


	@Override
	public PieceGradeRecord createPieceGradeRecordBy(Long taskId,Referees referees)
			throws Exception {
		GradeTask task = this.load(taskId);
		
		PieceGradeRecord pieceGradeRecord = referees.fetchCuttings();
		//数据持久化处理
		//TODO
		
		return pieceGradeRecord;
	}

}


