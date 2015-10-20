/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;

import java.util.Collection;
import java.util.Set;

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
	
	//正在进行的评卷
	private PieceGradeRecord recordingNow;
	
	//待进行的评卷
	private Set<PieceGradeRecord> recordings;
	
	public Referees(String name) {
		this.name = name;
	}
	
	public void useDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	/**
	 * 取一张切割块，并使其进入在评状态
	 * @return
	 * @throws Exception 取卷产生异常时抛出 
	 */
	public PieceGradeRecord fetchCuttings() throws Exception {
		if(iAmFree()) {
			if(this.dispatcher != null) {
				PieceCuttings cuttings = this.dispatcher.getFor(this);
				this.recordingNow = cuttings.addRecord(this);
			}			
		}
		return this.recordingNow;
	}

	public boolean iAmFree() {
		return this.recordingNow == null || this.recordingNow.isFinished();
	}
	
	public void done() {
		this.dispatcher = null;
		this.recordingNow = null;
	}
	
	/**
	 * 给正在评的切割块打分
	 * @param scores
	 * @return
	 * @throws IllegalArgumentException 当小题分值不在有效范围内时抛出
	 */
	public Collection<ItemGradeRecord> scoringForItems(Float[] scores) {
		Collection<ItemGradeRecord> igrs = this.recordingNow.scoringForItems(scores);
		for(ItemGradeRecord igr:igrs)
			igr.recordedBy(this);
		this.recordingNow.finish();
		return igrs;
	}
	
	public Collection<ItemGradeRecord> scoring(Float score) {
		//TODO
		return null;
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
    
    
    //以下功能为ORM或者自动构造使用，非此慎用
	public Referees(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

