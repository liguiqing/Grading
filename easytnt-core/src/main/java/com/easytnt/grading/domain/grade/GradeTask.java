/**
 * 
 * 
 **/

package com.easytnt.grading.domain.grade;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.paper.Section;

/** 
 * <pre>
 * 评卷任务
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class GradeTask implements ValueObject<GradeTask> {
	
	private Referees referees;
	
	private CuttingsArea area;
	
	private TaskType type;
	
	private TaskStatus status;
	
	private int refereesTotal = 0;
	
	public static GradeTask createOfficialGradeTask(Referees referees,CuttingsArea area) {
		return new GradeTask(referees,area,TaskType.Official);
	}
	
	public static GradeTask createTryGradeTask(Referees referees,CuttingsArea area) {
		return new GradeTask(referees,area,TaskType.TryTask);
	}
	
	public GradeTask(Referees referees,CuttingsArea area,TaskType taskType) {
		this.referees = referees;
		this.area = area;
		this.type = taskType;
		this.status = TaskStatus.Ready;
	}
	
	public boolean taskOf(Referees referees) {
		return this.referees.equals(referees);
	}
	
	public boolean typeOf(TaskType type) {
		return this.type.equals(type);
	}
	
	public void ready() {
		this.status = TaskStatus.Ready;
	}
	
	public void play() {
		this.status = TaskStatus.Play;
	}
	
	public void finish() {
		this.status = TaskStatus.Finish;
	}
	
	public boolean isFinished() {
		return this.status.equals(TaskStatus.Finish);
	}
	
	public List<Section> getSections(){
		return this.area.getSections();
	}
	
	public void useDispatcher(Dispatcher dispatcher) {
		this.referees.useDispatcher(dispatcher);
	}

	public String getSubjectName() {
		//TODO
		return "数学";
	}
	
	public int getRefereesTotal() {
		return this.refereesTotal;
	}
	
	public int getTotal() {
		//TODO
		return 12223;
	}
	

	public void increment() {
		this.refereesTotal++;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.referees).append(this.area).append(this.type).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GradeTask))
			return false;
		GradeTask other = (GradeTask) o;

		return new EqualsBuilder().append(this.referees, other.referees).append(this.type, other.type).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.referees).append(this.area).append(this.type).build();
	}
	
	@Override
	public boolean sameValueAs(GradeTask other) {
		return this.equals(other);
	}

	//以下功能为ORM或者自动构造使用，非此慎用
	public GradeTask() {}

	public CuttingsArea getArea() {
		return area;
	}

	public void setArea(CuttingsArea area) {
		this.area = area;
	}

	public Referees getReferees() {
		return referees;
	}

	public void setReferees(Referees referees) {
		this.referees = referees;
	}

	public TaskType getType() {
		return type;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public void setRefereesTotal(int refereesTotal) {
		this.refereesTotal = refereesTotal;
	}
	
}

