package com.easytnt.grading.repository.impl;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.easytnt.grading.domain.room.District;

@ContextConfiguration(locations= {"classpath:applicationContext.xml","classpath*:applicationContext-*.xml"})
public class HiberConfiguationTest  extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("sessionFactoryMock")
	SessionFactory sessionFactoryMock;
	
	@Test
	public void testMappings() throws Exception{
		assertNotNull(sessionFactory);
		logger.debug(sessionFactory.getAllClassMetadata());
	}
	
	@Test
	public void testSaveUpdate()throws Exception{
		assertNotNull(sessionFactoryMock);
		Session session = sessionFactoryMock.openSession();
		assertNotNull(session);
		District district = new District(100);
		session.beginTransaction();
		session.save(district);
		assertNotNull(district.getId());
		assertTrue(district.getId() > 0);
		session.delete(district);
		session.getTransaction().commit();
	}

}
