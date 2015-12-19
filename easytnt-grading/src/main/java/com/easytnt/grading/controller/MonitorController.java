package com.easytnt.grading.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.ui.MenuGroup;
import com.easytnt.commons.ui.ichart.Data;
import com.easytnt.commons.ui.ichart.DataList;
import com.easytnt.commons.ui.ichart.IchartData;
import com.easytnt.commons.ui.ichart.ResultData;
import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.domain.grade.Teacher;
import com.easytnt.grading.service.GradeTaskService;
import com.easytnt.grading.service.MonitorService;
import com.easytnt.grading.service.RefereesService;
import com.easytnt.grading.service.TeacherService;
import com.easytnt.security.ShiroService;
import com.easytnt.security.UserDetails;



/**
 * <pre>
 *  
 * </pre>
 * 
 * @author 李贵庆2015年11月9日
 * @version 1.0
 **/
@Controller
public class MonitorController {
	private static Logger logger = LoggerFactory.getLogger(MonitorController.class);
	 
	Random random = new Random(1);
	
	@Autowired(required=false)
	private TeacherService teacherService;
	
	@Autowired(required=false)
	private ShiroService shiroService;
	
	@Autowired(required=false)
	private MonitorService monitorService;
	
	@Autowired(required=false)
	private GradeTaskService gradeTaskService;
	
	@Autowired(required=false)
	private RefereesService refereesService;
	
	
	@RequestMapping(value="/monitor/progress",method=RequestMethod.GET)
	public ModelAndView onMonitorProgress()throws Exception{
		logger.debug("URL /monitor/progress Method Get");
		return createModelAndView(0,"progress").build();
	}

	@RequestMapping(value="/monitor/progress/data",method=RequestMethod.GET)
	public ModelAndView onGetProgressData()throws Exception{
		logger.debug("URL /monitor/progress/data Method Get");
		IchartData  datas  = monitorService.subjectsMonitor(-1l);
		return ModelAndViewFactory.newModelAndViewFor()
				.with("data",datas).build();
	}
	
	
	@RequestMapping(value="/monitor/worker",method=RequestMethod.GET)
	public ModelAndView onMonitorWorker()throws Exception{
		logger.debug("URL /monitor/worker Method Get");
		UserDetails user = shiroService.getUser();
		IchartData  datas ;
		if(user.roleOf("LEADER")) {
			Teacher leader = teacherService.findTeacher(user.getUserName());
		    datas = monitorService.sameTeamMonitor(leader);
		}else {
			Teacher leader = teacherService.findTeacher(user.getUserName());
		    datas = monitorService.sameTeamMonitor(leader);
		}
		return createModelAndView(1,"worker").with("datas", datas).build();
	}
	
	@RequestMapping(value="/monitor/team",method=RequestMethod.GET)
	public ModelAndView onMonitorTeam()throws Exception{
		//小组一致性 
		logger.debug("URL /monitor/team Method Get");
		return createModelAndView(2,"team").build();
	}
	
	@RequestMapping(value="/monitor/team/data",method=RequestMethod.GET)
	public ModelAndView onGetTeamData()throws Exception{
		logger.debug("URL /monitor/team/data Method Get");

		UserDetails user = shiroService.getUser();
		Teacher leader = teacherService.findTeacher(user.getUserName());
		Referees referees = refereesService.getCurrentReferees();
		List<GradeTask> tasks = gradeTaskService.getTaskOf(referees);
		//GradeTask task = gradeTaskService.getTaskOf(refereesService.load(leader.getTeacherId())).get(0);
		if(tasks!= null && tasks.size() >0) {
			IchartData  datas  = monitorService.teamMonitorOfWorking(leader,tasks.get(0));
			
			return createModelAndView(1,"worker").with("datas", datas).build();
		}else {
			return createModelAndView(1,"worker").with("datas", new ArrayList()).build();
		}
	}
	
	@RequestMapping(value="/monitor/personalStabled",method=RequestMethod.GET)
	public ModelAndView onPersonalStabled()throws Exception{
		//自身稳定性
		logger.debug("URL /monitor/personalStabled Method Get");
		return createModelAndView(3,"personalStabled").build();
	}
	
	
	@RequestMapping(value="/monitor/personalStabled/data",method=RequestMethod.GET)
	public ModelAndView onGetWorkerData(@PathVariable String account)throws Exception{
		logger.debug("URL /monitor/personalStabled/data Method Get" ,account);
		

		UserDetails user = shiroService.getUser();
		IchartData  datas ;
		if(user.roleOf("LEADER")) {
			Teacher leader = teacherService.findTeacher(user.getUserName());
		    datas = monitorService.sameTeamMonitor(leader);
			
		}else {
			Teacher leader = teacherService.findTeacher(user.getUserName());
		    datas = monitorService.sameTeamMonitor(leader);
		}
		
		return ModelAndViewFactory.newModelAndViewFor()
				.with("data",datas).build();
	}
	
	private ModelAndViewFactory createModelAndView(int activedIndex,String page) throws Exception{
		MenuGroup topRightMenuGroup = MenuGroupFactory.getInstance().getTopRightMenuGroup();
		MenuGroup rightMenuGroup = MenuGroupFactory.getInstance().getRightMenuGroup();
		MenuGroup monitorMenuGroup = MenuGroupFactory.getInstance().getMonitorMenuGroup();
		monitorMenuGroup.activedMenuByIndex(activedIndex);
		rightMenuGroup.activedMenuByIndex(2);
		UserDetails user = shiroService.getUser();
		return ModelAndViewFactory.newModelAndViewFor("/monitor")
				//.with("user", user)
				.with("menus2", topRightMenuGroup.getMenus())
				.with("rightSideMenu", rightMenuGroup.getMenus())
				.with("menus3", monitorMenuGroup.getMenus())
				.with("page",page);
	}
}
