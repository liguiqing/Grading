package com.easytnt.grading.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.commons.entity.service.AbstractEntityService;
import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.domain.grade.Teacher;
import com.easytnt.grading.repository.ExamRepository;
import com.easytnt.grading.repository.TeacherRepository;
import com.easytnt.grading.service.ExamService;
import com.easytnt.grading.service.TeacherService;

/**
 * 科目的具体操作服务实现类
 * 
 * @author 钟水林 20151109
 *
 */
@Service
public class TeacherServiceImpl extends AbstractEntityService<Teacher, Long>
		implements TeacherService {

	private TeacherRepository teacherRepository;

	public TeacherServiceImpl() {

	}

	@Autowired
	public void setRepository(TeacherRepository repository) {
		this.teacherRepository = repository;
		super.setRepository(repository);
	}


	
	@Transactional(readOnly=true)
	public void query(Query<Teacher> query){
		this.teacherRepository.query(query);
	}
	
	@Override
	@Transactional
	public void update(Teacher teacher) {
		if(teacher.getTeacherId() == null)
			throw new UnsupportedOperationException("组长主唯一标识不能为空");
		Teacher t = this.load(teacher.getTeacherId());
		t.copyNameFrom(teacher);
	}

	@Override
	@Transactional
	public void create(Teacher teacher, int amount) {
		int seq = this.teacherRepository.selectMaxSeqOf(teacher.getSubject().getId(),teacher.getLeader());
		teacher.genAccount(seq); 
		logger.debug("科目{} 最新账号是：{}",teacher.getSubject().getSubjectCode(),teacher.getTeacherAccount());
		List<Teacher> teachers = teacher.cloneTimes(amount);
		for(Teacher t:teachers) {
			this.create(t);
		}
	}

	@Transactional
	@Override
	public void resetPassword(Teacher teacher) {
		teacher.resetPassord();
	}
}
