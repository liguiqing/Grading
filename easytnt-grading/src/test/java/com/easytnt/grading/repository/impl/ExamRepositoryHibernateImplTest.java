package com.easytnt.grading.repository.impl;


import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.test.repository.AbstractHibernateTest;

public class ExamRepositoryHibernateImplTest extends AbstractHibernateTest{

	private ExamRepositoryHibernateImpl repository;
	

	
	@Before
	public void before()throws Exception{
		initHibernate("hibernate/mapping/Exam.hbm.xml");
		repository =  new ExamRepositoryHibernateImpl();
		repository.setSessionFactory01(sessionFactory);
	}
	
	@Test
	public void testSave()throws Exception{
		Exam exam = new Exam();
		exam.genOid();
		this.beginTransaction();
		saveOrUpdate(exam);
		this.commit();
		clear(exam);
	}
}
