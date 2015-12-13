/**
 * 
 */
package com.easytnt.grading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.grading.domain.cuttings.CuttingSolution;
import com.easytnt.grading.service.CuttingTestpaperService;
import com.easytnt.grading.service.CuttingsSolutionService;
import com.google.gson.Gson;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
@Controller
@RequestMapping("/cuttingDefine")
public class CuttingDefineController {
	@Autowired(required = false)
	private CuttingTestpaperService cuttingTestpaperService;
	@Autowired(required = false)
	private CuttingsSolutionService cuttingsSolutionService;

	@RequestMapping(value = "/{examId}/{paperId}", method = RequestMethod.GET)
	public ModelAndView index(@PathVariable Long examId, @PathVariable Long paperId) throws Exception {

		return ModelAndViewFactory.newModelAndViewFor("/cuttingDefine/home").with("examId", examId)
				.with("paperId", paperId).build();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@RequestBody CuttingSolution cuttingsSolution) throws Exception {
		cuttingsSolutionService.saveCuttingDefines(cuttingsSolution);
		return ModelAndViewFactory.newModelAndViewFor("").build();
	}

	@RequestMapping(value = "/get/{examId}/{paperId}", method = RequestMethod.GET)
	public ModelAndView get(@PathVariable Long examId, @PathVariable Long paperId) throws Exception {
		CuttingSolution cuttingsSolution = cuttingsSolutionService.getCuttingDefines(paperId);
		return ModelAndViewFactory.newModelAndViewFor("").with("cuttingsSolution", cuttingsSolution).build();
	}

	@RequestMapping(value = "/cutting/{paperId}", method = RequestMethod.GET)
	public ModelAndView cutting(@PathVariable Long paperId) throws Exception {
		cuttingTestpaperService.cutting(paperId);
		return ModelAndViewFactory.newModelAndViewFor("").build();
	}

	@RequestMapping(value = "/gettest/{examId}/{paperId}", method = RequestMethod.GET)
	public ModelAndView getTest(@PathVariable Long examId, @PathVariable Long paperId) throws Exception {
		CuttingSolution cuttingsSolution = new CuttingSolution();
		return ModelAndViewFactory.newModelAndViewFor("").with("cuttingsSolution", cuttingsSolution).build();
	}

	@RequestMapping(value = "/savetest", method = RequestMethod.POST)
	public void saveTest(@RequestBody CuttingSolution cuttingsSolution) throws Exception {
		Gson gson = new Gson();
		String json = gson.toJson(cuttingsSolution);
		System.out.println(json);
	}

}
