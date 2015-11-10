/**
 * 
 * 
 **/

package com.easytnt.security.shiro.web.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月28日
 * @version 1.0
 **/
public class SessionTimeoutFilter extends AdviceFilter {
	private  Logger logger = LoggerFactory.getLogger(SessionTimeoutFilter.class);
	
	private String defaultRedirectUrl;

	public String getDefaultRedirectUrl() {
		return defaultRedirectUrl;
	}

	public void setDefaultRedirectUrl(String defaultRedirectUrl) {
		this.defaultRedirectUrl = defaultRedirectUrl;
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response)
			throws Exception {

		Subject subject = SecurityUtils.getSubject();
		if (subject.getPrincipal() != null) {
			return true;
		}

		String url = defaultRedirectUrl;
		logger.info("After session timeout redirect to {}",url);
		WebUtils.issueRedirect(request, response, url);
		return false;
	}
}

