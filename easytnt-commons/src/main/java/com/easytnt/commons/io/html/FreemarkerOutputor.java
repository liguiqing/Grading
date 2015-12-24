/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.commons.io.html;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.commons.io.Outputor;
import com.easytnt.commons.util.Closer;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年12月24日
 * @version 1.0
 **/
public class FreemarkerOutputor implements Outputor {
	private static Logger logger = LoggerFactory.getLogger(FreemarkerOutputor.class);
	
	private String templateName;
	
	private Configuration config ;   
	
	public FreemarkerOutputor (Configuration config,String templateName) {
		this.config = config;
		this.templateName = templateName;
//		config = new Configuration();  
//		config.setLocale(Locale.CHINA);  
//        config.setDefaultEncoding("utf-8");  
//        config.setEncoding(Locale.CHINA, "utf-8");
//        config.setObjectWrapper(new DefaultObjectWrapper());  
	}
	
	@Override
	public void output(Object root,Writer out) {
		try{  
            //获得模板  
            Template template=config.getTemplate(templateName,"utf-8");  
            //生成文件（这里是我们是生成html）  
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


