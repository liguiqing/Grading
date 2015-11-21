package com.easytnt.grading.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.commons.entity.cqrs.QueryBuilder;
import com.easytnt.commons.ui.MenuGroup;
import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.grading.domain.exam.Subject;
import com.easytnt.grading.service.SubjectExamService;
import com.easytnt.grading.service.SubjectService;

@Controller
@RequestMapping(value = "/subject")
public class SubjectController {
	private static Logger logger = LoggerFactory.getLogger(SubjectController.class);

	@Autowired(required = false)
	private SubjectExamService subjectExamService;
	
	@Autowired(required = false)
	private SubjectService subjectService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView onGet()throws Exception {
		logger.debug("URL /subject Method GET ");
		MenuGroup topRightMenuGroup = MenuGroupFactory.getInstance().getTopRightMenuGroup();
		MenuGroup rightMenuGroup = MenuGroupFactory.getInstance().getRightMenuGroup();
		MenuGroup configMenuGroup = MenuGroupFactory.getInstance().getConfigMenuGroup();
		configMenuGroup.activedMenuByIndex(1);
		rightMenuGroup.activedMenuByIndex(3);
		return ModelAndViewFactory.newModelAndViewFor("/config")
				.with("resultList", subjectExamService.list())
				.with("menus2", topRightMenuGroup.getMenus())
				.with("rightSideMenu", rightMenuGroup.getMenus())
				.with("menus3", configMenuGroup.getMenus())
				.with("page","subject").build();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView onCreateSubject(@RequestBody Subject sub)
					throws Exception {
		logger.debug("URL /sub Method POST ", sub);
		subjectService.create(sub);
		return ModelAndViewFactory.newModelAndViewFor("/subject/editSubjectPaper").build();
	}
	//根据id查询科目
	@RequestMapping(value = "/{subId}", method = RequestMethod.GET)
	public ModelAndView onViewSubject(@PathVariable Long subId)
					throws Exception {
		logger.debug("URL /subId/{} Method Get ", subId);
		Subject subject = subjectService.load(subId);
		return ModelAndViewFactory.newModelAndViewFor("/subject/editSubjectPaper").with("subject",subject).build();
	}
	
	//更新科目
	@RequestMapping(method = RequestMethod.PUT)
	public ModelAndView onUpdateSubject(@RequestBody Subject sub)
					throws Exception {
		logger.debug("URL /subject Method PUT ", sub);
		subjectService.update(sub);
		return ModelAndViewFactory.newModelAndViewFor("/subject/editSubjectPaper").build();
	}
	//删除科目
	@RequestMapping(method = RequestMethod.DELETE)
	public ModelAndView onDeleteSubject(@RequestBody Subject sub)
					throws Exception {
		logger.debug("URL /subject Method DELETE ", sub);
		subjectService.delete(sub);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	//分页获取科目
	@RequestMapping(value="/query/{page}/{size}",method = RequestMethod.GET)
	public ModelAndView onDeleteSubject(@PathVariable int page,@PathVariable int size,HttpServletRequest request)
					throws Exception {
		logger.debug("URL /subject/query/{}/{} Method GET ", page,size);
        Query<Subject> query = new QueryBuilder().newQuery(page,size,request.getParameterMap());
        subjectService.query(query);
		return ModelAndViewFactory.newModelAndViewFor("/subject/listSubjectPaper").with("result",query.getResults())
				.with("totalPage",query.getTotalPage()).build();
	}
}
