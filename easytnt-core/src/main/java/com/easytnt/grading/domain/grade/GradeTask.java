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
	
	private Referees assignedTo;
	
	private CuttingsArea genBy;
	
	private TaskType type;
	
	private TaskStatus status;
	
	private int assignedToTotal = 0;
	
	private int taskTotal = 0;
	
	public static GradeTask createOfficialGradeTask(Referees referees,CuttingsArea genBy) {
		return new GradeTask(referees,genBy,TaskType.Official);
	}
	
	public static GradeTask createTryGradeTask(Referees referees,CuttingsArea genBy) {
		return new GradeTask(referees,genBy,TaskType.TryTask);
	}
	
	public GradeTask(Referees referees,CuttingsArea genBy,TaskType taskType) {
		this.assignedTo = referees;
		this.genBy = genBy;
		this.type = taskType;
		this.status = TaskStatus.Ready;
	}
	
	public boolean taskOf(Referees referees) {
		return this.assignedTo.equals(referees);
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
		if(this.status == null)
			this.status = TaskStatus.Ready;
		return this.status.equals(TaskStatus.Finish);
	}
	
	public List<Section> getSections(){
		return this.genBy.getSections();
	}
	
	public void useDispatcher(Dispatcher dispatcher) {
		this.assignedTo.useDispatcher(dispatcher);
	}
	
	public CuttingsImageGradeRecord getAGradeRecord() throws Exception{
		return this.assignedTo.fetchCuttings();
	}

	public String getSubjectName() {
		//TODO
		return "数学";
	}
	
	public int getAssignedToTotal() {
		return this.assignedToTotal;
	}
	
	public int getTotal() {
		return 0;
	}
	

	public void increment() {
		this.assignedToTotal++;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.assignedTo).append(this.genBy).append(this.type).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GradeTask))
			return false;
		GradeTask other = (GradeTask) o;

		return new EqualsBuilder().append(this.assignedTo, other.assignedTo).append(this.type, other.type).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.assignedTo).append(this.genBy).append(this.type).build();
	}
	
	@Override
	public boolean sameValueAs(GradeTask other) {
		return this.equals(other);
	}

	//以下功能为ORM或者自动构造使用，非此慎用
	public GradeTask() {}
	
	private Long taskId;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public CuttingsArea getArea() {
		return genBy;
	}

	public void setArea(CuttingsArea area) {
		this.genBy = area;
	}

	public Referees getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Referees assignedTo) {
		this.assignedTo = assignedTo;
	}

	public CuttingsArea getGenBy() {
		return genBy;
	}

	public void setGenBy(CuttingsArea genBy) {
		this.genBy = genBy;
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
		this.assignedToTotal = refereesTotal;
	}

	public int getTaskTotal() {
		return taskTotal;
	}

	public void setTaskTotal(int taskTotal) {
		this.taskTotal = taskTotal;
	}

	public void setAssignedToTotal(int assignedToTotal) {
		this.assignedToTotal = assignedToTotal;
	}
	
}

