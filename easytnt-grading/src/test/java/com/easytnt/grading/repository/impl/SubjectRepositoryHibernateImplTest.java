package com.easytnt.grading.repository.impl;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;

import com.easytnt.grading.domain.exam.Subject;
import com.easytnt.test.repository.AbstractHibernateTest;

public class SubjectRepositoryHibernateImplTest extends AbstractHibernateTest{

	private SubjectRepositoryHibernateImpl repository;
	

	@Before
	public void before()throws Exception{
		initHibernate("hibernate/mapping/exam/Subject.hbm.xml");
		repository =  new SubjectRepositoryHibernateImpl();
		repository.setSessionFactory01(sessionFactory);
	}
	
	@Test
	public void testSave()throws Exception{
		Subject subject = new Subject("语文",100);
		this.beginTransaction();
		this.saveOrUpdate(subject);
		this.commit();
		
		Subject s = (Subject) this.getSession().load(Subject.class, subject.getId());
		assertEquals(s,subject);

		clear(subject);
	}
}
