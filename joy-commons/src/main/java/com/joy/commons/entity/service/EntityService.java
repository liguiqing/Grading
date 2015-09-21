/**
 * <p><b>© 1997-2013 深圳市海云天教育测评有限公司 TEL: (86)755 - 86024188</b></p>
 * 
 **/

package com.joy.commons.entity.service;

import java.io.Serializable;
import java.util.List;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年9月21日
 * @version 1.0
 **/
public interface EntityService<T,PK extends Serializable> {

	T get(PK pk);
	
	T load(PK pk);
	
	void create(T t);
	
	void update(T t);
	
	void delete(T t);
	
	List<T> list();
}

