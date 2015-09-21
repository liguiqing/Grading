package com.joy.grading.domain.paper;

public class Paper {

	public String name;
	
	public static class Builder{
		private Paper paper;
		
		public Builder (String name) {
			this.paper = new Paper();
			this.paper.name = name;
		}
		
		public Paper create() {
			return this.paper;
		}
	}
	
	public Paper() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
