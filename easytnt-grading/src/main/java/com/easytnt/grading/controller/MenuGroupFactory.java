package com.easytnt.grading.controller;

import org.springframework.stereotype.Component;

import com.easytnt.commons.ui.Menu;
import com.easytnt.commons.ui.MenuGroup;

/**
 * <pre>
 *  临时做法，以后做成可以配置
 * </pre>
 * 
 * @author 李贵庆2015年11月9日
 * @version 1.0
 **/

public class MenuGroupFactory {

	private static MenuGroup topRightMenuGroup = MenuGroup.createMenuGroup();

	private static MenuGroup workerTopMenuGroup = MenuGroup.createMenuGroup();

	private static MenuGroup rightMenuGroup = MenuGroup.createMenuGroup();

	private static MenuGroup configMenuGroup = MenuGroup.createMenuGroup();

	private static MenuGroup monitorMenuGroup = MenuGroup.createMenuGroup();
	
	private final static class Holder{
		private final static MenuGroupFactory instance = new MenuGroupFactory();
	}
	
	public static MenuGroupFactory getInstance() {
		return MenuGroupFactory.Holder.instance;
	}
	
	private  MenuGroupFactory() {
    	topRightMenuGroup.appendMenu(new Menu("退    出","logout"));
    	
    	workerTopMenuGroup.appendMenu(new Menu("退    出","logout"));
    	
    	rightMenuGroup.appendMenu(new Menu("首    页","home"));
    	rightMenuGroup.appendMenu(new Menu("评    卷","task"));
    	rightMenuGroup.appendMenu(new Menu("进度监控","monitor?page=progress"));
    	rightMenuGroup.appendMenu(new Menu("评卷设置","config?page=subject"));
    	rightMenuGroup.appendMenu(new Menu("个人中心","infocenter"));
    	
    	configMenuGroup.appendMenu(new Menu("定义考试","config?page=exam"));
    	configMenuGroup.appendMenu(new Menu("科目设置","config?page=subject"));
    	configMenuGroup.appendMenu(new Menu("导入考生","config?page=examinee"));
    	configMenuGroup.appendMenu(new Menu("评卷老师管理","config?page=teacher"));
    	
    	monitorMenuGroup.appendMenu(new Menu("评卷进度","monitor?page=progress"));
    	monitorMenuGroup.appendMenu(new Menu("评卷教师监控","monitor?page=worker"));
    	monitorMenuGroup.appendMenu(new Menu("小组一致性","monitor?page=team"));
    	monitorMenuGroup.appendMenu(new Menu("自身稳定性","monitor?page=personalStabled"));
	}

	public  MenuGroup getTopRightMenuGroup() {
		return topRightMenuGroup.clone();
	}

	public  MenuGroup getWorkerTopMenuGroup() {
		return workerTopMenuGroup.clone();
	}

	public  MenuGroup getRightMenuGroup() {
		return rightMenuGroup.clone();
	}

	public  MenuGroup getConfigMenuGroup() {
		return configMenuGroup.clone();
	}

	public  MenuGroup getMonitorMenuGroup() {
		return monitorMenuGroup.clone();
	}
}
