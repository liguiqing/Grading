/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher;

import java.util.List;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public interface BlockFetcher {

	/*
	 * 获取block
	 */
	List<Block> fetch(int amount);

}

