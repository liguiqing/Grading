package com.joy.grading.domain.exam;

public class Exam {

	private String name;

	
	public static class Builder{
		private Exam exam;
		
		public Builder(String name) {
			this.exam = new Exam();
			this.exam.name = name;
		}
		
		public Exam create() {
			return this.exam;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
