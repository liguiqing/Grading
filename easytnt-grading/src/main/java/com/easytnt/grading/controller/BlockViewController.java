package com.easytnt.grading.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.web.view.ModelAndViewFactory;

@Controller
@RequestMapping(value = "/block")
public class BlockViewController {
	private static Logger logger = LoggerFactory.getLogger(BlockViewController.class);

	@RequestMapping(value = "/points/{blockuuid}", method = RequestMethod.GET)
	public ModelAndView onShowPoints(@PathVariable String blockuuid)
					throws Exception {
		logger.debug("URL /block/points/{} Method Get ", blockuuid);

		return ModelAndViewFactory.newModelAndViewFor("/block/pointsPanel").build();
	}
	
	@RequestMapping(value = "/img/{blockuuid}", method = RequestMethod.GET)
	public ModelAndView onShowImg(@PathVariable String blockuuid)
					throws Exception {
		logger.debug("URL /block/img/{} Method Get ", blockuuid);
      
		return ModelAndViewFactory.newModelAndViewFor("/block/imgPanel").build();
	}
}
