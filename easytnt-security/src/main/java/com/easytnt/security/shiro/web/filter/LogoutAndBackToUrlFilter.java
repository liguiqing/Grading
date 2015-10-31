/**
 * 
 * 
 **/

package com.easytnt.security.shiro.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
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
public class LogoutAndBackToUrlFilter extends LogoutFilter {
	private  Logger logger = LoggerFactory.getLogger(LogoutAndBackToUrlFilter.class);
	
	private String afterLogoutToUrlParamName = "afterlogouturl"; 

	
	@Override
	protected boolean preHandle(ServletRequest request,ServletResponse response) throws Exception {
		String redirectUrl = getRedirectUrl();
		HttpSession session = WebUtils.toHttp(request).getSession();
		if(session.getAttribute(afterLogoutToUrlParamName) != null) {
			redirectUrl = session.getAttribute(afterLogoutToUrlParamName) + "";
			session.removeAttribute(afterLogoutToUrlParamName);
		}
		
		Subject subject = getSubject(request,response);
		subject.logout();
		logger.debug("User {} Logout and back to {}",subject,redirectUrl);
		issueRedirect(request, response, redirectUrl);
		return false;
		
	}

	public String getAfterLogoutToUrlParamName() {
		return afterLogoutToUrlParamName;
	}

	public void setAfterLogoutToUrlParamName(String afterLogoutToUrlParamName) {
		this.afterLogoutToUrlParamName = afterLogoutToUrlParamName;
	}
	
	
	
}

