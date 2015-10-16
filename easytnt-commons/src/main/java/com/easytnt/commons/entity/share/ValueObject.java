/**
 * <p><b></b></p>
 * 
 **/

package com.easytnt.commons.entity.share;

import java.io.Serializable;

/** 
 * <pre>
 * 值对象
 * </pre>
 *  
 * @author 李贵庆2015年9月23日
 * @version 1.0
 **/
public interface ValueObject <T> extends Serializable{

	 /**
	  * 两个值对象是相同
	  * @param other
	  * @return
	  */
	 boolean sameValueAs(T other);
}

