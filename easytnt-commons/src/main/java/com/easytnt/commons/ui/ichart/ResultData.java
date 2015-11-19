package com.easytnt.commons.ui.ichart;

import java.util.List;

public class ResultData {
	private List data;
	private List<String> labels;
	private Float min;
	private Float Max;
	private Float space;
	private String unit;

	public Float getSpace() {
		return space;
	}

	public void setSpace(Float space) {
		this.space = space;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public Float getMin() {
		return min;
	}

	public void setMin(Float min) {
		this.min = min;
	}

	public Float getMax() {
		return Max;
	}

	public void setMax(Float max) {
		Max = max;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
