/**
 * Joy Anything (卓越安心)
 * 
 * <p><b>© 2015-2015 </b></p>
 * 
 **/

package com.easytnt.grading.controller;

import java.io.File;
import java.io.FileFilter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.easytnt.commons.ui.MenuGroup;
import com.easytnt.commons.web.view.ModelAndViewFactory;
import com.easytnt.security.UserDetails;

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
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView onIndex()throws Exception{
		logger.debug("URL /index Method Get");
		MenuGroup topRightMenuGroup = MenuGroupFactory.getInstance().getTopRightMenuGroup();
		MenuGroup rightMenuGroup = MenuGroupFactory.getInstance().getRightMenuGroup();
		//MenuGroup monitorMenuGroup = MenuGroupFactory.getInstance().getMonitorMenuGroup();
		//monitorMenuGroup.activedMenuByIndex(0);
		rightMenuGroup.activedMenuByIndex(0);
		return ModelAndViewFactory.newModelAndViewFor("/index")
				//.with("user", user)
				.with("menus2", topRightMenuGroup.getMenus())
				.with("rightSideMenu", rightMenuGroup.getMenus()).build();
		
		//return ModelAndViewFactory.newModelAndViewFor("/index").build();
	}

	@RequestMapping(value="/config",method=RequestMethod.GET)
	public ModelAndView onConfig(@RequestParam String page)throws Exception{
		logger.debug("URL /config Method Get");
		return ModelAndViewFactory.newModelAndViewFor("redirect:/"+page).build();
	}
	
	@RequestMapping(value="/monitor",method=RequestMethod.GET)
	public ModelAndView onMonitor(@RequestParam String page)throws Exception{
		logger.debug("URL /monitor Method Get");
		return ModelAndViewFactory.newModelAndViewFor("redirect:/monitor/"+page).build();

	}
	
	@RequestMapping(value="/dir",method=RequestMethod.GET)
	public ModelAndView onDir(HttpServletRequest request)throws Exception{
		logger.debug("URL /monitor Method Get");
		String root  = request.getParameter("root");
		File[] dirs = null;
		if(root == null) {
			dirs = File.listRoots();
		}else {
			new File(root).listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			});
		}
		return ModelAndViewFactory.newModelAndViewFor().with("dirs",dirs).build();
	}
	

}