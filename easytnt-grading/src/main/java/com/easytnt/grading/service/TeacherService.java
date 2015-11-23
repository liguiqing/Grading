package com.easytnt.grading.service;

import java.util.List;

import com.easytnt.commons.entity.service.EntityService;
import com.easytnt.grading.domain.exam.Subject;
import com.easytnt.grading.domain.grade.Teacher;
/**
 * 科目的具体操作服务类
 * 
 * @author 钟水林 20151109
 *
 */
public interface TeacherService extends EntityService<Teacher, Long> {
	
	public void create(Teacher teacher,int amount);

	public void resetPassword(Teacher teacher);

	public Teacher findTeacher(String account);

	public List<Teacher> findSubjectTeachers(Subject subject);
}

