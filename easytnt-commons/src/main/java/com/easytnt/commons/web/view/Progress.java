/**
 * 
 */
package com.easytnt.commons.web.view;

import java.math.BigDecimal;

/**
 * <Pre>
 * </Pre>
 *
 * @author liuyu
 * @data 2015年10月22日 下午2:44:49
 */
public class Progress {
	private Long total;
	private Long completed;

	public Progress(Long total, Long completed) {
		this.total = total;
		this.completed = completed;
	}

	public int percent() {
		Double percnet = completed * 1.0 / total * 100;
		return percnet.intValue();
	}

	public boolean isFinished() {
		return completed.equals(total);
	}

}
