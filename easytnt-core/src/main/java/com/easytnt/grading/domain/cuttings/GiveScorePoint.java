/**
 * 
 */
package com.easytnt.grading.domain.cuttings;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.NumberUtils;

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

	public void setValidscoredot(String validscoredot) {
		genValidValues(validscoredot);
	}

	public void genValidValues(String validscoredot) {
		String[] values = validscoredot.split(",");
		if (values.length > 0) {
			Float[] scores = new Float[values.length];
			int i = 0;
			for (String value : values) {
				scores[i++] = NumberUtils.parseNumber(value, Float.class);
			}
			this.validValues = scores;
		}
	}

	public String getValidscoredot() {
		return genValidscoredot(this.validValues);
	}

	public String genValidscoredot(Float[] validValues) {
		if (validValues.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (Float value : validValues) {
				sb.append(value).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
		return null;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return new HashCodeBuilder().append(id).append(name).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (!(obj instanceof GiveScorePoint)) {
			return false;
		}
		GiveScorePoint tmp = (GiveScorePoint) obj;

		return new EqualsBuilder().append(id, tmp.id).append(name, tmp.name).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id:", id).append("name:", name).build();
	}

}
