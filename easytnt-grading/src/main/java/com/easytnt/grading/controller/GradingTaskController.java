/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.ui.Menu;
import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.grading.domain.cuttings.CuttingsImage;
import com.easytnt.grading.domain.grade.CuttingsImageGradeRecord;
import com.easytnt.grading.domain.grade.GradeTask;
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
	public ModelAndView onGetTask(@PathVariable Long taskId) throws Exception {
		logger.debug("URL /task/{} Method Get", taskId);
		List<Menu> menus = new ArrayList<Menu>();
		menus.add( new Menu("个人中心",""));
		menus.add( new Menu("参考答案",""));
		menus.add( new Menu("统计信息",""));
		menus.add( new Menu("锁定屏幕",""));
		menus.add( new Menu("退出",""));
		
		Referees referees = refereesService.getCurrentReferees();
		GradeTask task = taskService.getTaskOf(taskId, referees);
		taskService.recoverUndo(task); 
		referees = task.getAssignedTo();

		CuttingsImageGradeRecord gradeRecord = task.getAGradeRecord();
		List<Section> sections = gradeRecord.getRecordFor().getSections();
		return ModelAndViewFactory.newModelAndViewFor("/task/gradingTask")
				.with("menus", menus)
				.with("referees", referees)
				.with("task", task)
				.with("sections", sections).build();
	}

	/**
	 * 读取切割图
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{taskId}/cuttings", method = RequestMethod.GET)
	public ModelAndView onGetCuttings(@PathVariable Long taskId) throws Exception {
		logger.debug("URL /task/{}/cuttings Method Get", taskId);
		Referees referees = refereesService.getCurrentReferees();
		CuttingsImageGradeRecord pieceGradeRecord = taskService.createImageGradeRecordBy(taskId, referees);
		CuttingsImage cuttings = pieceGradeRecord.getRecordFor();
		return ModelAndViewFactory.newModelAndViewFor().with("imgPath", cuttings.getImgPath()).with("imageId", cuttings.getImageId()).build();
	}

	/**
	 * 给分
	 * @param scores
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{taskId}/itemscoring", method = RequestMethod.POST)
	public ModelAndView onScoring(@RequestBody Float[] scores, @PathVariable Long taskId) throws Exception {
		logger.debug("URL /task/{}/itemscoring Method POST", taskId, scores.toString());

		Referees referees = refereesService.getCurrentReferees();
		taskService.itemScoring(taskId, referees, scores);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	/**
	 * 回看已经评过某一记录
	 * @param scores
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{taskId}/review/{recordId}", method = RequestMethod.GET)
	public ModelAndView onReview(@PathVariable Long taskId,@PathVariable Long recordId) throws Exception {
		logger.debug("URL /task/{}/review/{} Method POST", taskId, recordId);
		Referees referees = refereesService.getCurrentReferees();
		
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	/**
	 * 取消回看已经评过某一记录
	 * @param scores
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{taskId}/cancel/{recordId}", method = RequestMethod.GET)
	public ModelAndView onCancelReview(@RequestBody Float[] scores, @PathVariable Long taskId) throws Exception {
		logger.debug("URL /task/{}/itemscoring Method POST", taskId, scores.toString());

		Referees referees = refereesService.getCurrentReferees();
		taskService.itemScoring(taskId, referees, scores);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	/**
	 * 给分
	 * @param scores
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{taskId}/redo/itemscoring", method = RequestMethod.POST)
	public ModelAndView onRedoScoring(@RequestBody Float[] scores, @PathVariable Long taskId) throws Exception {
		logger.debug("URL /task/{}/itemscoring Method POST", taskId, scores.toString());
		Referees referees = refereesService.getCurrentReferees();
		taskService.itemScoring(taskId, referees, scores);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	@RequestMapping(value = "/{taskId}/error", method = RequestMethod.POST)
	public ModelAndView onError(@PathVariable Long taskId,@RequestParam String reason) throws Exception {
		logger.debug("URL /task/{}/error Method POST,reason is {}", taskId,reason);

		Referees referees = refereesService.getCurrentReferees();
		taskService.itemError(taskId, referees,reason);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	
	@RequestMapping(value = "/{taskId}/blank", method = RequestMethod.POST)
	public ModelAndView onBlank(@PathVariable Long taskId) throws Exception {
		logger.debug("URL /task/{}/error Method POST", taskId);

		Referees referees = refereesService.getCurrentReferees();
		taskService.itemBlank(taskId, referees);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}

}
