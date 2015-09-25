package com.joy.grading.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.joy.commons.ui.Menu;
import com.joy.commons.web.view.ModelAndViewFactory;
import com.joy.grading.domain.exam.Exam;
import com.joy.grading.domain.paper.Paper;
import com.joy.grading.service.ExamService;
import com.joy.grading.service.PaperService;;

@Controller
@RequestMapping(value = "/marking")
public class MakingController {
	private static Logger logger = LoggerFactory.getLogger(MakingController.class);

	@Autowired(required=false)
	private ExamService examService;
	
	@Autowired(required=false)
	private PaperService paperService;
	
	@RequestMapping(value = "/{examId}/{paperId}", method = RequestMethod.GET)
	public ModelAndView onView(@PathVariable Long examId, @PathVariable Long paperId)
					throws Exception {
		logger.debug("URL /marking/{}/{} Method Get ", examId, paperId);
		List<Menu> menus = new ArrayList<Menu>();
		menus.add( new Menu("个人中心",""));
		menus.add( new Menu("参考答案",""));
		menus.add( new Menu("统计信息",""));
		menus.add( new Menu("锁定屏幕",""));
		menus.add( new Menu("退出",""));
		Exam exam = examService.load(examId);
		Paper paper = paperService.load(paperId);
		
		String title = exam.getName() + paper.getName() + "评分";
		return ModelAndViewFactory.newModelAndViewFor("/marking/index").with("title", "试卷评分").with("menus", menus)
						.build();
	}
}