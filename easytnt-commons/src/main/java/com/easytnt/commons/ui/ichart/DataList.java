package com.easytnt.commons.ui.ichart;

import java.util.List;

public class DataList {
	private String name;
	private List<Float> value;
	private String color;
	private Integer line_width;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Float> getValue() {
		return value;
	}

	public void setValue(List<Float> value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getLine_width() {
		return line_width;
	}

	public void setLine_width(Integer line_width) {
		this.line_width = line_width;
	}

}
