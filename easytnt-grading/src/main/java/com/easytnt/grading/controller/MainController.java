/**
 * Joy Anything (卓越安心)
 * 
 * <p><b>© 2015-2015 </b></p>
 * 
 **/

package com.easytnt.grading.controller;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.ui.Menu;
import com.easytnt.commons.ui.MenuGroup;
import com.easytnt.commons.web.view.ModelAndViewFactory;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年7月31日
 * @version 1.0
 **/
@Controller
public class MainController {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);
    
    private static MenuGroup topRightMenuGroup = MenuGroup.createMenuGroup();
    
    private static MenuGroup workerTopMenuGroup = MenuGroup.createMenuGroup();
    
    private static MenuGroup rightMenuGroup = MenuGroup.createMenuGroup();
    
    private static MenuGroup configMenuGroup = MenuGroup.createMenuGroup();
    
    private static MenuGroup monitorMenuGroup = MenuGroup.createMenuGroup();
    
    public MainController() {
    	topRightMenuGroup.appendMenu(new Menu("退    出","logout"));
    	
    	workerTopMenuGroup.appendMenu(new Menu("退    出","logout"));
    	
    	rightMenuGroup.appendMenu(new Menu("首    页","home"));
    	rightMenuGroup.appendMenu(new Menu("评    卷","task"));
    	rightMenuGroup.appendMenu(new Menu("进度监控","monitor?page=subject&index=1"));
    	rightMenuGroup.appendMenu(new Menu("评卷设置","config?page=subject&index=1"));
    	rightMenuGroup.appendMenu(new Menu("个人中心","infocenter?page=subject&index=1"));
    	
    	configMenuGroup.appendMenu(new Menu("定义考试","confing?page=test&index=0"));
    	configMenuGroup.appendMenu(new Menu("科目设置","confing?page=subject&index=1"));
    	configMenuGroup.appendMenu(new Menu("导入考生","confing?page=examinee&index=2"));
    	configMenuGroup.appendMenu(new Menu("评卷老师管理","confing?page=worker&index=3"));
    	
    	monitorMenuGroup.appendMenu(new Menu("评卷进度","monitor?page=progress&index=0"));
    	monitorMenuGroup.appendMenu(new Menu("评卷教师监控","monitor?page=worker&index=1"));
    	monitorMenuGroup.appendMenu(new Menu("小组一致性","monitor?page=team&index=2"));
    	monitorMenuGroup.appendMenu(new Menu("自身稳定性","monitor?page=self&index=3"));
    }
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView onIndex()throws Exception{
		logger.debug("URL /index Method Get");
		
		return ModelAndViewFactory.newModelAndViewFor("/index").build();
	}

	@RequestMapping(value="/config",method=RequestMethod.GET)
	public ModelAndView onConfig(@RequestParam String page,@RequestParam int index)throws Exception{
		logger.debug("URL /config Method Get");
		configMenuGroup.activedMenuByIndex(index);
		rightMenuGroup.activedMenuByIndex(3);
		return ModelAndViewFactory.newModelAndViewFor("/config")
				.with("menus2", topRightMenuGroup.getMenus())
				.with("rightSideMenu", rightMenuGroup.getMenus())
				.with("menus3", configMenuGroup.getMenus()).build();
	}
	
	@RequestMapping(value="/monitor",method=RequestMethod.GET)
	public ModelAndView onMonitor(@RequestParam String page,@RequestParam int index)throws Exception{
		logger.debug("URL /config Method Get");
		monitorMenuGroup.activedMenuByIndex(index);
		rightMenuGroup.activedMenuByIndex(2);
		return ModelAndViewFactory.newModelAndViewFor("/config")
				.with("menus2", topRightMenuGroup.getMenus())
				.with("rightSideMenu", rightMenuGroup.getMenus())
				.with("menus3", monitorMenuGroup.getMenus()).build();
	}
}

