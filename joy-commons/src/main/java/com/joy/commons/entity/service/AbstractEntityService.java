package com.joy.commons.entity.service;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.joy.commons.entity.repository.Repository;

public abstract class AbstractEntityService <T,PK extends Serializable> {
	protected  Logger logger = LoggerFactory.getLogger(getClass());
	
	protected Repository<T, PK> repository;
	
	public AbstractEntityService(Repository<T, PK> repository) {
		this.repository = repository;
	}

	public AbstractEntityService() {
		
	}
	
	@Transactional
	public void create(T t) {
		logger.debug("Create {} ....",t);
		repository.save(t);
		logger.debug("Create {} success!",t);
	}
	
	@Transactional(readOnly=true)
	public T load(PK pk) {
		if(pk.toString().equals("-1"))
			return null;
		T t = repository.load(pk);
		if(t == null)
			t = repository.get(pk);
		return t;
	}
	
	@Transactional
	public void update(T t) {
		logger.debug("Update {} ....",t);
		repository.update(t);
		logger.debug("Update {} success!",t);
	}
	
	@Transactional
	public void delete(T t) {
		logger.debug("Remove {} ....",t);
		repository.delete(t);
		logger.debug("Remove {} success!",t);
	}
	
	@Transactional
	public void remove(T... ts) {
		if(ts != null && ts.length >0 ) {
			for(T t:ts) {
				logger.debug("Remove {} ....",t);
				repository.delete(t);
				logger.debug("Remove {} success!",t);
			}			
		}
	}

	@Transactional(readOnly=true)
	public List<T> list(){
		return repository.list();
	}
	
	
	public void setRepository(Repository<T, PK> repository) {
		this.repository = repository;
	}
	
	
}
