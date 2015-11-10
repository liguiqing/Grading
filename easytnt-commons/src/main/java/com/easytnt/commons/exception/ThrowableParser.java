/**
 * <p><b>© 1997-2013 深圳市海云天教育测评有限公司 TEL: (86)755 - 86024188</b></p>
 * 
 **/

package com.easytnt.commons.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年7月31日
 * @version 1.0
 **/
public class ThrowableParser {

	public static String toString(Throwable t) {
		if(t == null)
			return "Throwable is null";
		StringWriter swriter = new StringWriter();
		PrintWriter  pwriter = new PrintWriter(swriter);
		t.printStackTrace(pwriter);
		String s =  swriter.toString();
		pwriter.close();
		return s;
	}
	
	public static String toString(Exception t) {
		return toString(t.getCause() == null ?t:t.getCause());
	}
}

