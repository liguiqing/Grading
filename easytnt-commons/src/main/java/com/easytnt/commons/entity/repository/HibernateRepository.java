package com.easytnt.commons.entity.repository;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.entity.cqrs.Query;

/** 
 * <pre>
 * 基于Hibernate的Repository
 * </pre>
 *  
 * @author 李贵庆2015年9月23日
 * @version 1.0
 **/
public abstract class HibernateRepository<T, PK extends Serializable> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	SessionFactory sessionFactory;

	@Resource(name = "sessionFactory")
	public void setSessionFactory01(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;

	}


	public void save(T t) {
		this.getCurrentSession().save(t);
	}

	public void saveOrUpdate(T t) {
		this.getCurrentSession().saveOrUpdate(t);
	}

	public void update(T t) {
		this.getCurrentSession().update(t);
	}

	public T load(PK id) {
		Class<T> cls = getEntityClass();
		return (T) this.getCurrentSession().load(cls, id);
	}

	public T get(PK id) {
		Class<T> cls = getEntityClass();
		return (T) this.getCurrentSession().get(cls, id);
	}

	public void delete(T t) {
		this.getCurrentSession().delete(t);
	}

	public List<T> list() {
		Criteria criteria = this.getCurrentSession().createCriteria(
						this.getEntityClass().getName());

		@SuppressWarnings("unchecked")
		List<T> list = criteria.list();
		return list;
	}
	
	public void query(Query<T> query) {
		//TODO in sub class 
	}

	protected Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	abstract protected Class<T> getEntityClass();
}
