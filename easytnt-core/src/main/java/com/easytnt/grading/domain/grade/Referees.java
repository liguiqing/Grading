/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.domain.cuttings.CuttingsImage;

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
	private CuttingsImageGradeRecord recordingNow;
	
	//待进行的评卷
	private CuttingsImageGradeRecord waittingRecord;
	
	//最近一个已经完成评卷
	private CuttingsImageGradeRecord lastFinishedRecord;
	
	
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
	public CuttingsImageGradeRecord fetchCuttings() throws Exception {
		if(iAmFree()) {
			
			if(this.dispatcher != null) {
				CuttingsImage cuttings = this.dispatcher.getFor(this);
				this.recordingNow = cuttings.createRecord(this);
			}			
		}
		return this.recordingNow;
	}
	

	public void recoverRecord(CuttingsImageGradeRecord gradeRecord) {
		if(gradeRecord == null || gradeRecord.equals(this.recordingNow))
			return;
		
		if(this.recordingNow != null && this.waittingRecord != null)
			throw new IllegalStateException("当前评卷不能覆盖！" + gradeRecord);
		
		if(this.recordingNow == null) {
			this.recordingNow = gradeRecord;
		    return ;
		}
		
		this.waittingRecord = this.recordingNow;
		this.recordingNow = gradeRecord;
	}

	public boolean iAmFree() {
		if(this.recordingNow == null) {
			if(this.waittingRecord != null) {
				this.recordingNow = this.waittingRecord;
			}
		}
		
		return this.recordingNow == null || this.recordingNow.isFinished();
	}
	
	public void done() {
		this.dispatcher = null;
		this.recordingNow = null;
		this.lastFinishedRecord = null;
		this.waittingRecord = null;
	}
	
	/**
	 * 给正在评的切割块打分
	 * @param scores
	 * @return
	 * @throws IllegalArgumentException 当小题分值不在有效范围内时抛出
	 */
	public CuttingsImageGradeRecord scoringForItems(Float[] scores) {
		this.recordingNow.scoringForItems(scores);
		this.recordingNow.finish();
		this.lastFinishedRecord  = this.recordingNow;
		return this.recordingNow;
	}
	
	public boolean inDoneTaskList(CuttingsImage cuttingsimage) {
		if(this.lastFinishedRecord != null) {
			if(this.lastFinishedRecord.recordedFor(cuttingsimage)) {
				return true;
			}
		}
		
		if(this.waittingRecord != null) {
			if(this.waittingRecord.recordedFor(cuttingsimage)) {
				return true;
			}
		}
		
		if(this.recordingNow != null) {
			if(this.recordingNow.recordedFor(cuttingsimage)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void insert(CuttingsImage cuttingsimage) {
		CuttingsImageGradeRecord redoRecord = cuttingsimage.createRecord(this);
		if(this.waittingRecord == null) {
			this.waittingRecord = this.recordingNow;			
		}
		this.recordingNow = redoRecord;
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

	private Long id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}

