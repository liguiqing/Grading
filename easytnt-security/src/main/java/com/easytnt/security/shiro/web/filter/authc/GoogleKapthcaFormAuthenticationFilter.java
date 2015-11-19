/**
 * 
 * 
 **/

package com.easytnt.security.shiro.web.filter.authc;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
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
public class GoogleKapthcaFormAuthenticationFilter extends FormAuthenticationFilter {
	private static Logger logger = LoggerFactory.getLogger(GoogleKapthcaFormAuthenticationFilter.class);

	private boolean enabled = true;
	
	private String usernameParam = "username";
	
	private String passwordParam = "password";
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		if(!enabled)
			return false;
		if (request.getAttribute(getFailureKeyAttribute()) != null) {
			logger.info("验证码不匹配");
			return true;
		}

		boolean isDenied =  super.onAccessDenied(request, response, mappedValue);

		if(isDenied) {
			request.setAttribute(this.usernameParam, request.getParameter(this.usernameParam));
			request.setAttribute(this.passwordParam, request.getParameter(this.passwordParam));
			
			Subject subject = getSubject(request, response);
			if(subject.getPrincipal() == null) {
			     HttpServletResponse res = (HttpServletResponse)response;
			     res.setHeader("sessionstatus", "timeout");
			}
		}
		return isDenied;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		request.setAttribute(getFailureKeyAttribute(), "账号或者密码有误，请重新输入！");
		return true;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setUsernameParam(String usernameParam) {
		this.usernameParam = usernameParam;
	}

	public void setPasswordParam(String passwordParam) {
		this.passwordParam = passwordParam;
	}
	
}

