/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;
/** 
 * <pre>
 * 评卷员
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class Referees {

	private String name;
	
	public Referees(String name) {
		this.name = name;
	}
	
	public Referees(){
		
	}
	@Override
	public int hashCode() {
		//TODO 
		return this.name.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		//TODO 
		if(!(o instanceof Referees)) {
			return false;
		}
		Referees other = (Referees)o;
		return this.name.equals(other.name);
	}
	
	@Override
	public String toString() {
		//TODO  
		return this.name;
	}
}

