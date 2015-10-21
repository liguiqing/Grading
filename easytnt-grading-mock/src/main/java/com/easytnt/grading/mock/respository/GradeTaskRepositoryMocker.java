/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.mock.respository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.paper.Item;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.domain.share.Area;
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
	
	private static HashMap<Long,GradeTask> tasks = new HashMap<>();
	
	public void initMethod() {
		createTask();
	}
	
	private void createTask() {
		CuttingsArea area = new CuttingsArea();
		area.setId(1l);
		Section section = new Section();
		section.setTitle("二、填空题");
		section.setCaption("二、填空题");
		area.bindSection(section);
		for(int i=13;i<=16;i++) {
			section.addItem(new Item.Builder(i+"").caption(i+"").scoreDot("0,1,2")
					.fullScore(2f).answerArea(new Area(10,20,100,50)).create());
		}
		
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

}


