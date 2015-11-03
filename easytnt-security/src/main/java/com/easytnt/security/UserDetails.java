/**
 * 
 * 
 **/

package com.easytnt.security;
/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月27日
 * @version 1.0
 **/
public interface UserDetails {

	String getUserName();
	
	String getAlias();
	
	String getRealName();
	
	Object getCredentials();
	
	<T> T getSource();
	
	<T> boolean sourceOf(T t);
}

