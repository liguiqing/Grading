/**
 * 
 */
package com.easytnt.grading.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	private static Logger log = LoggerFactory.getLogger(ImportPaperController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return ModelAndViewFactory.newModelAndViewFor("importpaper/index").build();
	}

	@RequestMapping(value = "/{saveToDb}/scan", method = RequestMethod.GET)
	public ModelAndView scan(@PathVariable int saveToDb, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// saveToDb 0 不保存数据库 1保存数据
		log.debug("begin /{target}/scan...");
		log.debug("end /{target}/scan...");
		return ModelAndViewFactory.newModelAndViewFor("importpaper/index").build();
	}

	@RequestMapping(value = "/{type}/stat", method = RequestMethod.GET)
	public ModelAndView getScanStatisticInfo(@PathVariable int type, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// saveToDb 0 不保存数据库 1保存数据
		log.debug("begin /{type}/stat...");
		log.debug("end /{type}/stat...");
		return ModelAndViewFactory.newModelAndViewFor("importpaper/index").build();
	}
}
