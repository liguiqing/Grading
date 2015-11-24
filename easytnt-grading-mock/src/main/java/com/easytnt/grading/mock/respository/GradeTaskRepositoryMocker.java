/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.mock.respository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.paper.Item;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.domain.share.Area;
import com.easytnt.grading.mock.dispatcher.DispatcherConcreator;
import com.easytnt.grading.repository.GradeTaskRepository;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/
public class GradeTaskRepositoryMocker  implements GradeTaskRepository{
	@Autowired
	RefereesRepositoryMocker refereesRepository;
	
	@Autowired
	DispatcherConcreator dispatcherConcreator;
	
	private static HashMap<Long,GradeTask> tasks = new HashMap<>();
	
	public void initMethod() {
		createTask();
	}
	
	private void createTask() {
		CuttingsArea area = dispatcherConcreator.getCuttingsDefineds().get(0);
		
		for(Long id=1l;id<=10;id++) {
			GradeTask task = GradeTask.createOfficialGradeTask(refereesRepository.load(id), area);
			
			tasks.put(id, task);
		}
	}
	
	@Override
	public void save(GradeTask t) {

	}

	@Override
	public void saveOrUpdate(GradeTask t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GradeTask t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GradeTask load(java.lang.Long pk) {
		return tasks.get(pk);
	}

	@Override
	public GradeTask get(java.lang.Long pk) {
		// TODO Auto-generated method stub
		return null;
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
	public void query(Query<GradeTask> query) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GradeTask findRefereesTask(Long cuttoId, Long refereesId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GradeTask> findGenTasks(Long l) {
		// TODO Auto-generated method stub
		return null;
	}

}


