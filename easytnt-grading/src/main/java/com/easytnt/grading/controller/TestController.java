/**
 * 
 */
package com.easytnt.grading.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.junit.Test;
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
@RequestMapping("/test")
public class TestController {
	private static Logger logger = LoggerFactory.getLogger(TestController.class);

	@RequestMapping("/1")
	public ModelAndView test1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return ModelAndViewFactory.newModelAndViewFor("/importpaper/test").build();
	}

	@RequestMapping("/2")
	public ModelAndView test2(@RequestBody Map<String, Object> data, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return ModelAndViewFactory.newModelAndViewFor("importpaper/index").build();
	}

	@RequestMapping("/3")
	public ModelAndView test3(@RequestBody String myData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return ModelAndViewFactory.newModelAndViewFor("importpaper/index").build();
	}

	@RequestMapping("/4")
	public ModelAndView test3(@RequestBody TestBean testbean, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return ModelAndViewFactory.newModelAndViewFor("importpaper/index").build();
	}

	@RequestMapping("/5")
	public ModelAndView test5(@RequestBody List<String> data, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return ModelAndViewFactory.newModelAndViewFor("importpaper/index").build();
	}

	@RequestMapping("/6")
	public ModelAndView test6(@RequestBody String[] data, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return ModelAndViewFactory.newModelAndViewFor("importpaper/index").build();
	}

	@RequestMapping("/7")
	public ModelAndView test7(@RequestBody List<TestBean> data, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return ModelAndViewFactory.newModelAndViewFor("importpaper/index").build();
	}
}
