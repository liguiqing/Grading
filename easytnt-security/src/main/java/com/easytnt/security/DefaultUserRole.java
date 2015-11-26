/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.security;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月26日
 * @version 1.0
 **/
public class DefaultUserRole implements UserRole {
	
	private String roleName;
	
	public DefaultUserRole(String roleName) {
		this.roleName = roleName;
	}
	
	public boolean sameRole(String roleName) {
		return this.roleName.equalsIgnoreCase(roleName);
	}
}


