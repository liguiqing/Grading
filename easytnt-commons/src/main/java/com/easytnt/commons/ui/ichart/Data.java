package com.easytnt.commons.ui.ichart;

public class Data {
	private String name;
	private Float[] values;
	private String color;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float[] getValue() {
		return values;
	}

	public void addValue(Float value) {
		if(this.values == null) {
			this.values = new Float[1];
			this.values[0] = value;
		}else {
			Float[] vs = new Float[this.values.length+1];
			for(int i = 0;i<this.values.length;i++) {
				vs[i] = this.values[i];
			}
			vs[vs.length] = value;
			this.values = vs;
		}
	}
	
	public void setValue(Float value) {
		this.addValue(value);
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
