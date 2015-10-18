/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.grading.domain.cuttings.PieceCuttings;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.mgt.GradingManager;
import com.easytnt.grading.service.RefereesService;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月17日
 * @version 1.0
 **/

@Controller
@RequestMapping(value = "/cuttings")
public class PieceCuttingsController {
	private static Logger logger = LoggerFactory.getLogger(PieceCuttingsController.class);
	
	@Autowired(required=false)
	private RefereesService refereesService;
	
	private GradingManager gradingManager;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView onShow(@PathVariable String blockuuid)
					throws Exception {
		logger.debug("URL /block/points/{} Method Get ", blockuuid);

		return ModelAndViewFactory.newModelAndViewFor("/block/pointsPanel").build();
	}
	
	@RequestMapping(value = "/{testId}/{paperId}/{cuttingsId}", method = RequestMethod.GET)
	public ModelAndView onShowCuttings(@PathVariable String blockuuid)
					throws Exception {
		logger.debug("URL /block/points/{} Method Get ", blockuuid);
		Referees referees = refereesService.getCurrentReferees();
		PieceCuttings cuttings = gradingManager.getPieceCuttingsFor(referees);
		
		return ModelAndViewFactory.newModelAndViewFor("/block/pointsPanel").build();
	}
}


