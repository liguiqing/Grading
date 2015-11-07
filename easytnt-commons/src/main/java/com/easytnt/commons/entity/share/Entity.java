/**
 * <p><b></b></p>
 * 
 **/

package com.easytnt.commons.entity.share;
/** 
 * <pre>
 * 实体接口，所有实体对象都必须继承之
 * </pre>
 *  
 * @author 李贵庆2015年9月23日
 * @version 1.0
 **/
public interface Entity<T> {

	/**
	 * 判断一个实体是否同一个实体
	 * 绝大部分情况下此方法返回值与与equals一致
	 * @param other
	 * @return
	 */
	boolean sameIdentityAs(T other);
}

