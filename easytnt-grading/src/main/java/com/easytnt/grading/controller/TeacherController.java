package com.easytnt.grading.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.commons.entity.cqrs.QueryBuilder;
import com.easytnt.commons.ui.MenuGroup;
import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.grading.domain.grade.Teacher;
import com.easytnt.grading.service.SubjectService;
import com.easytnt.grading.service.TeacherService;

@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {
	private static Logger logger = LoggerFactory.getLogger(TeacherController.class);

	@Autowired(required = false)
	private TeacherService teacherService;
	
	@Autowired(required = false)
	private SubjectService subjectService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView onList()
					throws Exception {
		logger.debug("URL /teacher Method GET ");
		MenuGroup topRightMenuGroup = MenuGroupFactory.getInstance().getTopRightMenuGroup();
		MenuGroup rightMenuGroup = MenuGroupFactory.getInstance().getRightMenuGroup();
		MenuGroup configMenuGroup = MenuGroupFactory.getInstance().getConfigMenuGroup();
		configMenuGroup.activedMenuByIndex(3);
		rightMenuGroup.activedMenuByIndex(3); 
		
		Query<Teacher> query = new QueryBuilder().newQuery(1,10,new HashMap());
		teacherService.query(query);
		return ModelAndViewFactory.newModelAndViewFor("/config")
				.with("query", query)
				.with("subjects", subjectService.list())
				.with("menus2", topRightMenuGroup.getMenus())
				.with("rightSideMenu", rightMenuGroup.getMenus())
				.with("menus3", configMenuGroup.getMenus())
				.with("page","worker").build();
	}
	
	@RequestMapping(value="/{amount}",method = RequestMethod.POST)
	public ModelAndView onCreateTeacher(@PathVariable int amount,@RequestBody Teacher teacher)
					throws Exception {
		logger.debug("URL /Teacher Method POST ", teacher);
		teacherService.create(teacher, amount);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	@RequestMapping(value = "/{teacherId}", method = RequestMethod.GET)
	public ModelAndView onViewTeacher(@PathVariable Long teacherId)
					throws Exception {
		logger.debug("URL /TeacherId/{} Method Get ", teacherId);
		Teacher teacher = teacherService.load(teacherId);
		return ModelAndViewFactory.newModelAndViewFor("/teacher/editTeacher").with("teacher",teacher).build();
	}
	
	//用户修改
	@RequestMapping(method = RequestMethod.PUT)
	public ModelAndView onUpdateTeacher(@RequestBody Teacher teacher)
					throws Exception {
		logger.debug("URL /Teacher Method PUT ", teacher);
		teacherService.update(teacher);
		return ModelAndViewFactory.newModelAndViewFor("/teacher/editTeacher").build();
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ModelAndView onDeleteTeacher(@RequestBody Teacher teacher)
					throws Exception {
		logger.debug("URL /Teacher Method DELETE ", teacher);
		teacherService.delete(teacher);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
	
	@RequestMapping(value="/query/{page}/{size}",method = RequestMethod.GET)
	public ModelAndView onQueryTeacher(@PathVariable int page,@PathVariable int size,HttpServletRequest request)
					throws Exception {
		logger.debug("URL /Teacher/query/{}/{} Method GET ", page,size);
        Query<Teacher> query = new QueryBuilder().newQuery(page,size,request.getParameterMap());
        teacherService.query(query);
		return ModelAndViewFactory.newModelAndViewFor("/teacher/listTeacher").with("query",query)
				.with("totalPage",query.getTotalPage()).build();
	}
	
	@RequestMapping(value="/password/reset/{teacherId}",method = RequestMethod.PUT)
	public ModelAndView onResetPassword(@PathVariable Long teacherId)
					throws Exception {
		logger.debug("URL /password/reset//{} Method GET ", teacherId);
        Teacher teacher = teacherService.load(teacherId);
        teacherService.resetPassword(teacher);
		return ModelAndViewFactory.newModelAndViewFor().build();
	}
}
