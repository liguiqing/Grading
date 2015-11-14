package com.easytnt.commons.util;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年11月10日
 * @version 1.0
 **/
public class Closer {
	private static Logger logger = LoggerFactory.getLogger(Closer.class);
	
	public static void close(Closeable closeable) {
		try{
			if(closeable != null) {
				closeable.close();
			}
		}catch(IOException e) {
			logger.error(ThrowableParser.toString(e));
			throw new RuntimeException();
		}
	}
}
