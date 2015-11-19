package com.easytnt.grading.repository.impl;


import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.easytnt.grading.domain.exam.ExamDesc;
import com.easytnt.grading.domain.exam.Subject;
import com.easytnt.grading.domain.exam.SubjectExam;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.test.repository.AbstractHibernateTest;

public class SubjectExamRepositoryHibernateImplTest extends AbstractHibernateTest{

	private SubjectExamRepositoryHibernateImpl repository;
	

	
	@Before
	public void before()throws Exception{
		initHibernate("hibernate/mapping/paper/ExamPaper.hbm.xml","hibernate/mapping/paper/Item.hbm.xml","hibernate/mapping/paper/PaperType.hbm.xml","hibernate/mapping/paper/Section.hbm.xml","hibernate/mapping/exam/Subject.hbm.xml","hibernate/mapping/exam/SubjectExam.hbm.xml");
		repository =  new SubjectExamRepositoryHibernateImpl();
		repository.setSessionFactory01(sessionFactory);
	}
	
	@Test
	public void testSave()throws Exception{
		SubjectExam subjectExam = new SubjectExam();
		subjectExam.setDesc(new ExamDesc("数学"));
		subjectExam.setOid(100l);
		Subject sub = new Subject("数学", 100);
		subjectExam.setSubject(sub);
		
//		
		ExamPaper examPaper = new ExamPaper("数学", 120f);
		
		subjectExam.addExamPapers(examPaper);
		examPaper.addSubjectExams(subjectExam);
		
		this.beginTransaction();
		saveOrUpdate(subjectExam);
		this.commit();
//		clear(exam);
	}
}
