package com.easytnt.grading.domain.grade;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.grading.domain.exam.Subject;

/**
 * 20151109
 * 
 * @author 钟水林
 *
 */
public class Teacher {
	private Long teacherId;
	
	private Subject subject;
	
	private String teacherAccount;
	
	private String teacherName;
	
	private String teacherPassord;
	
	private int leader;

	public void genAccount(int seq) {
    	if(this.subject != null) {
    		if(seq <1 ) {	
	    		if(this.isManager()) {
	    			this.teacherAccount =  (this.subject.getSubjectCode() * 100 ) + "";//String.valueOf(this.subject.getSubjectCode() * 100 +seq);
	    		}else {
	    			this.teacherAccount =  (this.subject.getSubjectCode() * 1000 ) + "";//String.valueOf(this.subject.getSubjectCode() * 1000 +seq);
	    		}
	    		}else {
	    			this.teacherAccount = seq + "" ;
	    		}
	    		return;
    	}
    	throw new UnsupportedOperationException("没有设置评卷老师所属科目，无法生成账号");
	}

    public boolean isManager() {
    	return this.leader > 0;
    }
    
    public void resetPassord() {
    	this.teacherPassord = this.teacherAccount;
    }
    
    public void copyNameFrom(Teacher other) {
    	this.teacherName = other.teacherName;
    }
    
	public List<Teacher> cloneTimes(int times){
		ArrayList<Teacher> ts = new ArrayList<>();
		int account = Integer.valueOf(this.teacherAccount);
		for(int i = 0;i<times;i++) {
			Teacher t = new Teacher();
			t.subject = this.subject;
			t.teacherName = this.teacherName;
		    t.teacherAccount = (++account)+"";
		    t.teacherPassord = t.teacherAccount;
			t.leader = this.leader;
			ts.add(t);
		}
		return ts;
	}
	
	//重写
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.teacherAccount).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Teacher))
			return false;
		Teacher other = (Teacher) o;
		return new EqualsBuilder().append(this.teacherAccount, other.teacherAccount).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.teacherAccount).append(this.teacherName).build();
	}
	
	public Teacher() {
		
	}
	
	//set  get
	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}


	public String getTeacherAccount() {
		return teacherAccount;
	}

	public void setTeacherAccount(String teacherAccount) {
		this.teacherAccount = teacherAccount;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherPassord() {
		return teacherPassord;
	}

	public void setTeacherPassord(String teacherPassord) {
		this.teacherPassord = teacherPassord;
	}
	
	public int getLeader() {
		return leader;
	}

	public void setLeader(int leader) {
		this.leader = leader;
	}
}
