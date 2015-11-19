/**
 * 
 * 
 **/

package com.easytnt.security;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年11月19日
 * @version 1.0
 **/
public class PasswordEncryptor {
	
	@Value("${shiro.password.algorithmName}")
	private String algorithmName ;
	
	@Value("${shiro.password.hashIterations}")
	private int hashIterations;
	
	public String encrypt(String password,String salt) {
		return new SimpleHash(this.algorithmName,password,ByteSource.Util.bytes(salt),this.hashIterations).toHex();
	}
}

