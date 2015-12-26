/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.reporting.out;

import java.io.Writer;

/** 
 * <pre>
 * 报告输出
 * </pre>
 * 
 * @author 李贵庆 2015年12月26日
 * @version 1.0
 **/
public interface ReportingOutput {
	void write(Object root,Writer out);
}


