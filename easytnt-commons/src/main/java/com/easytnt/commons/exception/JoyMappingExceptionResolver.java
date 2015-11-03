/**
 * <p><b>© 2015-2015</b></p>
 * 
 **/

package com.easytnt.commons.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.easytnt.commons.web.view.Responser;
import com.google.gson.Gson;

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
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.error("System Exception for ：{}", ex);
		String viewName = determineViewName(ex, request);
		if (!(request.getHeader("accept").indexOf("application/json") > -1
				|| (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1) || (request
				.getHeader("Content-Type") != null && request.getHeader("Content-Type").indexOf("multipart/form-data") > -1))) {

			Integer statusCode = determineStatusCode(request, viewName);

			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			} else if (ex instanceof SQLException)
				request.setAttribute("message", "服务器异常");
			else
				request.setAttribute("message", "很抱歉，系统在处理您的请求产生未知错误，请联系管理员");
			return getModelAndView(viewName, ex, request);
		} else {
			try {
				Responser rs = new Responser.Builder().failure().msg(ex.getMessage()).create();

				HashMap<String, Responser> jsonMap = new HashMap<String, Responser>();
				jsonMap.put(Responser.ModelName, rs);
				String json = new Gson().toJson(jsonMap);
				PrintWriter writer = response.getWriter();

				writer.write(json);
				writer.flush();
			} catch (IOException e) {
				logger.error(ThrowableParser.toString(e));
			} finally {

			}
		}
		return getModelAndView("/404", ex, request);
	}
}
