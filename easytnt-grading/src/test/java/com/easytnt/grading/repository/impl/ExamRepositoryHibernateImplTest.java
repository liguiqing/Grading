package com.easytnt.grading.repository.impl;


import java.util.Date;

import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.domain.exam.ExamDesc;
import com.easytnt.test.repository.AbstractHibernateTest;

public class ExamRepositoryHibernateImplTest extends AbstractHibernateTest{

	private ExamRepositoryHibernateImpl repository;
	

	
	@Before
	public void before()throws Exception{
		initHibernate("hibernate/mapping/exam/Exam.hbm.xml");
		repository =  new ExamRepositoryHibernateImpl();
		repository.setSessionFactory01(sessionFactory);
	}
	
	@Test
	public void testSave()throws Exception{
		Exam exam = new Exam();
		ExamDesc desc = new ExamDesc();
		
		//新增
	/*	exam.setId(1l);
		exam.setOid(1l);
		
		exam.getDesc().setName("小花");
		desc.setFrom(new Date());
		desc.setTo(new Date());
		
		*/
		//修改
		exam = (Exam) session.load(Exam.class,2l);
		
		System.out.println(exam);
		
		exam.setId(2l);
		exam.setOid(2l);
		
		exam.getDesc().setName("小花二");
		desc.setFrom(new Date());
		desc.setTo(new Date());
		
		//exam.genOid();
		this.beginTransaction();
		saveOrUpdate(exam);
		this.commit();
		clear(exam);
	}
}
