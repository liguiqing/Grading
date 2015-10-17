package com.easytnt.grading.domain.exam;

import java.util.Date;

import com.easytnt.commons.entity.share.Entity;


public class Exam implements Entity<Exam>{

	private String name;
	
	private Date examDate;

	@Override
	public boolean sameIdentityAs(Exam other) {
		return false;
	}
	
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
	
    private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    
}
