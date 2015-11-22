package com.easytnt.grading.repository;
import java.util.List;

import com.easytnt.commons.entity.repository.Repository;
import com.easytnt.grading.domain.grade.Teacher;

/**
 * 科目的具体操作接口类
 * 
 * @author 钟水林 20151109
 *
 */
public interface TeacherRepository extends Repository<Teacher, Long> {

	int selectMaxSeqOf(Long subjectId, int leader);

	public Teacher selectTeacherAnHisTask(String account);

	public List<Teacher> selectTeachersOfSubject(Long id);
	
}
