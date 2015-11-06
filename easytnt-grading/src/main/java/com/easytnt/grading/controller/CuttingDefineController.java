/**
 * 
 */
package com.easytnt.grading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("cuttingDefine")
public class CuttingDefineController {

	@RequestMapping("/{examId}/{paperId}")
	public ModelAndView cuttingDefine(@PathVariable Long examId, @PathVariable Long paperId) throws Exception {

		return ModelAndViewFactory.newModelAndViewFor("/cuttingDefine/home").build();
	}
}
