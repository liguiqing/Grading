/**
 * 
 */
package com.easytnt.grading.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.web.view.ModelAndViewFactory;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@Controller
@RequestMapping("/importPaper")
public class ImportPaperController {
	private static Logger logger = LoggerFactory.getLogger(ImportPaperController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return ModelAndViewFactory.newModelAndViewFor("importpaper/index").build();
	}
}
