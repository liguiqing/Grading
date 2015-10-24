/**
 * 
 */
package com.easytnt.commons.web.view;

/**
 * <Pre>
 * </Pre>
 *
 * @author liuyu
 * @data 2015年10月22日 下午2:44:49
 */
public class Progress {
	private int total;
	private int completed;
	private int percent;
	private String text;
	private boolean finished;

	public Progress(int total, int completed, String text) {
		this.total = total;
		this.completed = completed;
		this.text = text;
		Double percent = completed * 1.0 / total * 100;
		this.percent = percent.intValue();
	}

	public int getTotal() {
		return total;
	}

	public Progress setTotal(int total) {
		this.total = total;
		return this;
	}

	public int getCompleted() {
		return completed;
	}

	public Progress setCompleted(int completed) {
		this.completed = completed;
		return this;
	}

	public int getPercent() {
		return percent;
	}

	public Progress setPercent(int percent) {
		this.percent = percent;
		return this;
	}

	public String getText() {
		return text;
	}

	public Progress setText(String text) {
		this.text = text;
		return this;
	}

	public boolean isFinished() {
		return finished;
	}

	public Progress setFinished(boolean finished) {
		this.finished = finished;
		return this;
	}

}
