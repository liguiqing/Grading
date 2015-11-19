/**
 * 
 * 
 **/

package com.easytnt.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年11月19日
 * @version 1.0
 **/
public class ShiroService {

	public UserDetails getUser()throws IllegalAccessException{
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()) {
			return (UserDetails)subject.getPrincipal();
		}
		throw new IllegalAccessException("非法访问");
	}
	
	public void logout() {
		SecurityUtils.getSubject().logout();
	}
}

