package com.easytnt.commons.entity.repository;

import java.io.Serializable;
import java.util.List;

public interface Repository<T, PK extends Serializable> {
	public void save(T t);

	public void saveOrUpdate(T t);

	public void update(T t);

	public T load(PK pk);

	public T get(PK pk);
	
	public void delete(T t);
	
	public List<T> list();
}
