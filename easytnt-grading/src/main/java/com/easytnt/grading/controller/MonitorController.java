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
import com.easytnt.commons.ui.ichart.ResultData;
import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.grading.domain.grade.Teacher;
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
	
	@RequestMapping(value="/monitor/progress",method=RequestMethod.GET)
	public ModelAndView onMonitorProgress()throws Exception{
		logger.debug("URL /monitor/progress Method Get");
		return createModelAndView(0,"progress").build();
	}

	@RequestMapping(value="/monitor/progress/data",method=RequestMethod.GET)
	public ModelAndView onGetProgressData()throws Exception{
		logger.debug("URL /monitor/progress/data Method Get");
		UserDetails user = shiroService.getUser();
		if(user.getSource() == null) {
			
		}
		
		ResultData resultData = new ResultData();
		List<Data> dataList = new ArrayList<Data>();
		String[] color = new String[]{"#a5c2d5","#cbab4f","#76a871","#76a871","#a56f8f","#c12c44","#a56f8f","#9f7961","#76a871","#6f83a5"};
		for(int i=0;i<10;i++){
			Data data = new Data();
			data.setName("数据"+i);
			data.setColor(color[i]);
			data.setValue(((int)(random.nextFloat()*10))/10f);
			dataList.add(data);
		}
		resultData.setData(dataList);
		resultData.setMax(1f);
		resultData.setMin(0f);
		resultData.setSpace(0.1f);
		resultData.setUnit("数据");
		
		return ModelAndViewFactory.newModelAndViewFor()
				.with("data",resultData).build();
	}
	
	
	@RequestMapping(value="/monitor/worker",method=RequestMethod.GET)
	public ModelAndView onMonitorWorker()throws Exception{
		logger.debug("URL /monitor/worker Method Get");
		UserDetails user = shiroService.getUser();
		List<Teacher> teachers;
		if(user.roleOf("LEADER")) {
			Teacher leader = teacherService.findTeacher(user.getUserName());
			teachers = teacherService.findSubjectTeachers(leader.getSubject());
		}else {
			teachers = teacherService.list();
		}
		return createModelAndView(1,"worker").build();
	}
	
	@RequestMapping(value="/monitor/team",method=RequestMethod.GET)
	public ModelAndView onMonitorTeam()throws Exception{
		//小组一致性 
		UserDetails user = shiroService.getUser();
		Teacher teacher = teacherService.findTeacher(user.getUserName());
		logger.debug("URL /monitor/team Method Get");
		return createModelAndView(2,"team").build();
	}
	
	@RequestMapping(value="/monitor/team/data",method=RequestMethod.GET)
	public ModelAndView onGetTeamData()throws Exception{
		logger.debug("URL /monitor/team/data Method Get");

		ResultData resultData = new ResultData();
		List<DataList> dataListList = new ArrayList<DataList>();
		List<String> labels = new ArrayList<String>();
		String[] color = new String[]{"#a5c2d5","#cbab4f","#76a871","#76a871","#a56f8f","#c12c44","#a56f8f","#9f7961","#76a871","#6f83a5"};
		for(int index=0;index<10;index++){
			List<Float> values = new ArrayList<Float>();
			DataList dataList = new DataList();
			dataList.setName("老师"+index);
			dataList.setLine_width(2);
			//dataList.setColor(color[index]);
			
			for(int i=1;i<=24;i++){
				values.add(((int)(random.nextFloat()*10))/10f);
			}
			dataList.setValue(values);
			dataListList.add(dataList);
		}
		for(int i=1;i<=24;i++){
			labels.add(i+"");
		}
		resultData.setData(dataListList);
		resultData.setLabels(labels);
		resultData.setMax(1f);
		resultData.setMin(0f);
		resultData.setSpace(0.1f);
		resultData.setUnit("数据");
		
		return ModelAndViewFactory.newModelAndViewFor()
				.with("data",resultData).build();
	}
	
	@RequestMapping(value="/monitor/personalStabled",method=RequestMethod.GET)
	public ModelAndView onPersonalStabled()throws Exception{
		//自身稳定性
		logger.debug("URL /monitor/personalStabled Method Get");
		return createModelAndView(3,"personalStabled").build();
	}
	
	
	@RequestMapping(value="/monitor/personalStabled/data/{account}",method=RequestMethod.GET)
	public ModelAndView onGetWorkerData(@PathVariable String account)throws Exception{
		logger.debug("URL /monitor/personalStabled/data{account} Method Get" ,account);
		ResultData resultData = new ResultData();
		List<DataList> dataList = new ArrayList<DataList>();
		List<String> labels = new ArrayList<String>();
		List<Float> values = new ArrayList<Float>();
		DataList DataList = new DataList();
		DataList.setName("张老师");
		DataList.setLine_width(2);
		DataList.setColor("#01acb6");
		
		for(int i=1;i<=24;i++){
			values.add(((int)(random.nextFloat()*10))/10f);
			labels.add(i+"");
		}
		DataList.setValue(values);
		
		dataList.add(DataList);
		resultData.setData(dataList);
		resultData.setLabels(labels);
		resultData.setMax(1f);
		resultData.setMin(0f);
		resultData.setSpace(0.1f);
		resultData.setUnit("数据");
		
		
		return ModelAndViewFactory.newModelAndViewFor()
				.with("data",resultData).build();
	}
	
	private ModelAndViewFactory createModelAndView(int activedIndex,String page) throws Exception{
		MenuGroup topRightMenuGroup = MenuGroupFactory.getInstance().getTopRightMenuGroup();
		MenuGroup rightMenuGroup = MenuGroupFactory.getInstance().getRightMenuGroup();
		MenuGroup monitorMenuGroup = MenuGroupFactory.getInstance().getMonitorMenuGroup();
		monitorMenuGroup.activedMenuByIndex(activedIndex);
		rightMenuGroup.activedMenuByIndex(2);
		UserDetails user = shiroService.getUser();
		return ModelAndViewFactory.newModelAndViewFor("/monitor")
				.with("user", user)
				.with("menus2", topRightMenuGroup.getMenus())
				.with("rightSideMenu", rightMenuGroup.getMenus())
				.with("menus3", monitorMenuGroup.getMenus())
				.with("page",page);
	}
}
