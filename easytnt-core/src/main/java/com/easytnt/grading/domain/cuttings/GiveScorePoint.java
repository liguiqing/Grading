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

	public Long getId() {
		return id;
	}

	public GiveScorePoint setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public GiveScorePoint setName(String name) {
		this.name = name;
		return this;
	}

	public Area getArea() {
		return area;
	}

	public GiveScorePoint setArea(Area area) {
		this.area = area;
		return this;
	}

	public Float[] getValidValues() {
		return validValues;
	}

	public GiveScorePoint setValidValues(Float[] validValues) {
		this.validValues = validValues;
		return this;
	}

	public Float getFullScore() {
		return fullScore;
	}

	public GiveScorePoint setFullScore(Float fullScore) {
		this.fullScore = fullScore;
		return this;
	}

	public boolean isSeriesScore() {
		return seriesScore;
	}

	public GiveScorePoint setSeriesScore(boolean seriesScore) {
		this.seriesScore = seriesScore;
		return this;
	}

	public Float getInterval() {
		return interval;
	}

	public GiveScorePoint setInterval(Float interval) {
		this.interval = interval;
		return this;
	}

	public CuttingDefine getCuttingDefine() {
		return cuttingDefine;
	}

	public GiveScorePoint setCuttingDefine(CuttingDefine cuttingDefine) {
		this.cuttingDefine = cuttingDefine;
		return this;
	}

}
