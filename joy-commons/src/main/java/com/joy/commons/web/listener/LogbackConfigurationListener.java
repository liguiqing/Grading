/**
 * <p><b>2015 - 2015</b></p>
 * 
 **/

package com.joy.commons.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年8月3日
 * @version 1.0
 **/
public class LogbackConfigurationListener implements ServletContextListener {
	private static final String CONFIG_FILE = "logbackConfigLocation";
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String config = sce.getServletContext().getInitParameter(CONFIG_FILE);
		String file = sce.getServletContext().getRealPath(config);
		try {
			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			loggerContext.reset();
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(loggerContext);
			configurator.doConfigure(file);
			System.out.print("Logback configurations from " + file);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}

