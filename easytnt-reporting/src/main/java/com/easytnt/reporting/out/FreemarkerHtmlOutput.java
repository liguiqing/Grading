/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.reporting.out;

import java.io.IOException;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年12月26日
 * @version 1.0
 **/
public class FreemarkerHtmlOutput implements ReportingOutput{
private static Logger logger = LoggerFactory.getLogger(FreemarkerHtmlOutput.class);
	
	private String templateName;
	
	private Configuration config ;   
	
	public FreemarkerHtmlOutput (Configuration config,String templateName) {
		this.config = config;
		this.templateName = templateName;
	}
	
	@Override
	public void write(Object root,Writer out) {
		try{  
            //获得模板  
            Template template=config.getTemplate(templateName,"utf-8");  
            template.process(root, out);     
            out.flush();     
        } catch (IOException e) {  
            logger.error(ThrowableParser.toString(e));
        } catch (TemplateException e) {  
        	logger.error(ThrowableParser.toString(e));
        }finally{  

        }   
	}
}


