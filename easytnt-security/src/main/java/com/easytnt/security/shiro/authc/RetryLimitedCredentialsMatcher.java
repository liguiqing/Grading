/**
 * 
 * 
 **/

package com.easytnt.security.shiro.authc;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月27日
 * @version 1.0
 **/
public class RetryLimitedCredentialsMatcher extends HashedCredentialsMatcher {
	private  Logger logger = LoggerFactory.getLogger(RetryLimitedCredentialsMatcher.class);
	
	private Cache<String,AtomicInteger> retryCatche;
	
	private int retryLimited = 5;
	
	public RetryLimitedCredentialsMatcher(CacheManager cacheManager,String cacheName) {
		this.retryCatche = cacheManager.getCache(cacheName);
	}
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		if(retryLimited > 0) {
			AtomicInteger retryTimes = getRetryTimes(username);
			logger.debug("User {} try login {} times",username,retryTimes.get());
			if(retryTimes.incrementAndGet() > this.retryLimited) {
				throw new ExcessiveAttemptsException();
			}		
		}

		boolean matches = super.doCredentialsMatch(token, info);
		if(matches) {
			retryCatche.remove(username);
		}
		logger.debug("User {} try login {} ",matches);
		
		return matches;
	}
	
	private AtomicInteger getRetryTimes(String key) {
		AtomicInteger retryTimes = retryCatche.get(key);
		if(retryTimes == null) {
			retryTimes = new AtomicInteger(0);
			this.retryCatche.put(key, retryTimes);
		}
		
		return retryTimes;
	}

	public int getRetryLimited() {
		return retryLimited;
	}

	public void setRetryLimited(int retryLimited) {
		this.retryLimited = retryLimited;
	}
}

