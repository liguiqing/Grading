/**
 * 
 */
package com.easytnt.grading.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.commons.web.view.Progress;

/**
 * <Pre>
 * 进度条控制类
 * </Pre>
 *
 * @author liuyu
 * @data 2015年10月22日 下午3:26:40
 */
@Controller
@RequestMapping("/progress")
public class ProgressController {

	@RequestMapping("/{entry}")
	public ModelAndView progress(@PathVariable String entry, @RequestBody String uuid, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// ProgressListener progressListener = SpringContextUtil.getBean(entry);
		// Progress p =
		// progressListener.on(ServletUtil.getRequestParamsMap(request));
		Progress p = new Progress(100, 80, "80%");
		return ModelAndViewFactory.newModelAndViewFor("/share/progressbar").with("progress", p).build();
	}
}
