/**
 * <p><b>© 2015-2015</b></p>
 * 
 **/

package com.joy.commons.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年7月31日
 * @version 1.0
 **/
public class SpringContextUtil implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static <T> T getBean(String name){
		return (T)applicationContext.getBean(name);
	}
	
	public static <T> T getBean(Class clazz){
		return (T)applicationContext.getBean(clazz);
	}
	
}

