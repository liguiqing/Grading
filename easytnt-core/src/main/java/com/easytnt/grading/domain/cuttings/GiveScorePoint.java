/**
 * 
 */
package com.easytnt.grading.domain.cuttings;

import com.easytnt.grading.domain.share.Area;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <pre>
 * 给分点
 * </pre>
 * 
 * @author liuyu
 *
 */
public class GiveScorePoint {
	private Long id;
	private String name;
	private Area area = new Area();
	private Float[] validValues;
	private Float fullScore;
	private boolean seriesScore = true;// 是否连续给分
	private Float interval = 0f;// 如果连续给分的给分区间
	@JsonIgnore
	private CuttingDefine cuttingDefine;
}
