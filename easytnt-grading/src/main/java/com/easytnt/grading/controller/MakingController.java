package com.easytnt.grading.controller;

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

import com.easytnt.commons.ui.Menu;
import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.domain.paper.Paper;
import com.easytnt.grading.service.ExamService;
import com.easytnt.grading.service.PaperService;;

@Controller
@RequestMapping(value = "/marking")
public class MakingController {
	private static Logger logger = LoggerFactory.getLogger(MakingController.class);

	@Autowired(required=false)
	private ExamService examService;
	
	@Autowired(required=false)
	private PaperService paperService;
	
	@RequestMapping(value = "/{examId}/{paperId}/{blockuuid}", method = RequestMethod.GET)
	public ModelAndView onView(@PathVariable Long examId, @PathVariable Long paperId,@PathVariable String blockuuid)
					throws Exception {
		logger.debug("URL /marking/{}/{}/{} Method Get ", examId, paperId,blockuuid);
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
	
	@RequestMapping(value = "/monitor/{examId}/{paperId}/{blockuuid}", method = RequestMethod.GET)
	public ModelAndView onMonitor(@PathVariable Long examId, @PathVariable Long paperId,@PathVariable String blockuuid)
					throws Exception {
		logger.debug("URL /marking/monitor/{}/{}/{} Method Get ", examId, paperId,blockuuid);
		List<Menu> menus = new ArrayList<Menu>();
		menus.add( new Menu("退出",""));
		Exam exam = examService.load(examId);
		Paper paper = paperService.load(paperId);
		
		String title = exam.getName() + paper.getName() + "监控";
		return ModelAndViewFactory.newModelAndViewFor("/marking/monitorIndex").with("title", "试卷评分").with("menus2", menus)
						.build();
	}
}
