/**
 * 
 */
package com.easytnt.grading.domain.cuttings;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class AnswerCardCuttingTemplate {
	private int index;// 序号；如一张答题卡有前后，扫描的时候图片就有2张；0 代表第一张1代表第二章以此类推
	private int rotate;// 旋转角度

	public int getRotate() {
		return rotate;
	}

	public AnswerCardCuttingTemplate setRotate(int rotate) {
		this.rotate = rotate;
		return this;
	}

	public int getIndex() {
		return index;
	}

	public AnswerCardCuttingTemplate setIndex(int index) {
		this.index = index;
		return this;
	}

}
