/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.domain.cuttings.PieceCuttings;


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
	
	private Dispatcher dispatcher;
	
	private PieceGradeRecord recordingNow;
	
	public Referees(String name) {
		this.name = name;
	}
	
	public void useDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	public PieceGradeRecord fetchCuttings() throws Exception {
		if(this.dispatcher != null) {
			PieceCuttings cuttings = this.dispatcher.get(this);
			this.recordingNow = cuttings.addRecord(this);
		}
		return recordingNow;
	}

	
	
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.name).toHashCode();
	}
	
    @Override
	public boolean equals(Object o) {
		if(!(o instanceof Referees))
			return false;
		Referees other = (Referees)o;
		
		return new EqualsBuilder().append(this.name,other.name).isEquals();
	}
	
    @Override
	public String toString() {
		return new ToStringBuilder(this).append(this.name).build();
	}
    
	public Referees(){
		
	}
}

