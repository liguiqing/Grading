/**
 * <p></p>
 * 
 **/

package com.easytnt.commons.entity.service;

import java.io.Serializable;
import java.util.List;

import com.easytnt.commons.entity.cqrs.Query;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年9月21日
 * @version 1.0
 **/
public interface EntityService<T,PK extends Serializable> {

	T load(PK pk);
	
	void create(T t);
	
	void update(T t);
	
	void delete(T t);
	
	List<T> list();
	
	void query(Query<T> query);
}

