package com.easytnt.commons.entity.repository;

import java.io.Serializable;
import java.util.List;

import com.easytnt.commons.entity.cqrs.Query;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年9月23日
 * @version 1.0
 **/
public interface Repository<T, PK extends Serializable> {
	public void save(T t);

	public void saveOrUpdate(T t);

	public void update(T t);

	public T load(PK pk);

	public T get(PK pk);
	
	public void delete(T t);
	
	public List<T> list();
	
	public void query(Query<T> query);
}
