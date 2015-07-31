/**
 * <p><b>© 2015-2015</b></p>
 * 
 **/

package com.joy.commons.exception;

import java.io.ObjectInputStream.GetField;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆2015年7月31日
 * @version 1.0
 **/
public class JoyMappingExceptionResolver extends SimpleMappingExceptionResolver {
	private static Logger logger = LoggerFactory.getLogger(JoyMappingExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex) {
		logger.error("系统异常：{}",ex);
		String viewName = determineViewName(ex, request);
		return getModelAndView(viewName,ex,request);
	}
}
