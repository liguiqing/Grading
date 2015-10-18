/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.domain.paper.AnswerArea;
import com.easytnt.grading.domain.paper.ExamPaper;
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

	@Override
	@Transactional(readOnly=true)
	public GradeTask load(Long pk) {
		//TODO
		GradeTask task = new GradeTask();
		CuttingsArea area = new CuttingsArea();
		task.setArea(area);
		Section section = new Section();
		section.setTitle("二、填空题");
		section.setCaption("二、填空题");
		
		
		return task;
	}

	@Override
	public void create(GradeTask t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GradeTask t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(GradeTask t) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<GradeTask> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public GradeTask getRefereesTaskOf(Referees referees, Long taskId) {
		//TODO
		GradeTask task =  this.load(taskId);
		task.setReferees(referees);
		return task;
	}

}


