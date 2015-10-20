/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.PieceCuttings;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.grade.PieceGradeRecord;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.service.GradeTaskService;
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
@RequestMapping(value = "/task")
public class GradingTaskController {
	private static Logger logger = LoggerFactory.getLogger(GradingTaskController.class);

	@Autowired(required = false)
	private RefereesService refereesService;

	@Autowired(required = false)
	private GradeTaskService taskService;

	@RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
	public ModelAndView onGetTask(@PathVariable Long taskId, @PathVariable Long areaId) throws Exception {
		logger.debug("URL /task/{} Method Get", taskId);
		Referees referees = refereesService.getCurrentReferees();
		GradeTask task = taskService.getTaskOf(taskId, referees);

		CuttingsArea area = task.getArea();
		List<Section> sections = area.getSections();

		return ModelAndViewFactory.newModelAndViewFor("/task/gradingTask").with("referees", referees).with("task", task)
				.with("sections", sections).build();
	}

	@RequestMapping(value = "/{taskId}/getcut", method = RequestMethod.GET)
	public ModelAndView onGetCuttings(@PathVariable Long taskId) throws Exception {
		logger.debug("URL /task/{}/ Method Get", taskId);
		Referees referees = refereesService.getCurrentReferees();
		PieceGradeRecord pieceGradeRecord = taskService.createPieceGradeRecordBy(taskId, referees);
		PieceCuttings cuttings = pieceGradeRecord.getRecordFor();
		return ModelAndViewFactory.newModelAndViewFor("/index").with("imgPath", cuttings.getImgPath()).build();
	}

	@RequestMapping(value = "/{taskId}/itemscoring", method = RequestMethod.POST)
	public ModelAndView onScoring(@RequestBody Float[] scores, @PathVariable Long taskId) throws Exception {
		logger.debug("URL /task/{}/itemscoring Method POST", taskId, scores.toString());

		Referees referees = refereesService.getCurrentReferees();
		taskService.itemScoring(taskId, referees, scores);
		return ModelAndViewFactory.newModelAndViewFor("").build();
	}

}
