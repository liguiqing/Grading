/**
 * 
 * 
 **/

package com.easytnt.grading.domain.exam;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.Entity;
import com.easytnt.grading.domain.paper.ExamPaper;

/** 
 * <pre>
 * 科目（或者称学科）考试,如，语文考试、数学考试
 * 科目考试可以独立进行，也可以是一次多学科考试{@link Exam}的组部分，
 * 如九年上学期期末考试包含有语文考试、数学考试、英语考试等
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class SubjectExam implements Entity<SubjectExam>{
    
    private Long oid;
    
    private ExamDesc desc;
    
    private Subject subject;
    
    private Exam belongTo;
    
    private Set<ExamPaper> usedPaper;
    
    private Integer testYear;
    
    private Integer testMonth;
    
    private Integer testWeek;
    
    public SubjectExam(ExamDesc desc,Subject subject ,Long oid) {
		this.desc = desc;
		this.subject = subject;
		this.oid = oid;
	}
    
    private void init() {
		if (this.usedPaper == null) {
			this.usedPaper = new LinkedHashSet<ExamPaper>();
		}
	}
    
    public static SubjectExam newSubjectExam(String subjectName,int subjectCode,Float fullScore,
    		Float objectivityScore,Float subjectivityScore) {
    	SubjectExam se = new SubjectExam();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
    	Calendar now = Calendar.getInstance();  
    	se.oid = Long.valueOf(sdf.format(now.getTime()));
    	se.desc  =new ExamDesc(subjectName);
    	se.subject = new Subject(subjectName,subjectCode);
    	ExamPaper paper = new ExamPaper(subjectName,fullScore,objectivityScore,subjectivityScore);
    	se.addExamPapers(paper);
    	return se;
    }
    
    public void addExamPapers(ExamPaper examPaper){
    	init();
    	examPaper.addSubjectExams(this);
    	examPaper.setPaperOid( this.oid * 10 + (this.usedPaper.size()+1));
    	this.usedPaper.add(examPaper);
    }
    
    public static SubjectExam createBy(Set<ExamPaper> usedPaper,ExamDesc desc,Subject subject) {
    	SubjectExam se = new SubjectExam();
    	se.usedPaper = usedPaper;
    	se.desc = desc;
    	se.subject = subject;
    	return se;
    }

    @Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.oid).toHashCode();
	}
	
    @Override
	public boolean equals(Object o) {
		if(!(o instanceof SubjectExam))
			return false;
		SubjectExam other = (SubjectExam)o;
		
		return new EqualsBuilder().append(this.oid,other.oid).isEquals();
	}
	
    @Override
	public String toString() {
		return new ToStringBuilder(this).append(this.desc).build();
	}

	@Override
	public boolean sameIdentityAs(SubjectExam other) {
		return this.equals(other);
	}
	
	//以下功能为ORM或者自动构造使用，非此慎用
	public SubjectExam() {
		
	}
	
	private Long testId;

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public ExamDesc getDesc() {
		return desc;
	}

	public void setDesc(ExamDesc desc) {
		this.desc = desc;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Exam getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(Exam belongTo) {
		this.belongTo = belongTo;
	}

	public Set<ExamPaper> getUsedPaper() {
		return usedPaper;
	}

	public void setUsedPaper(Set<ExamPaper> usedPaper) {
		this.usedPaper = usedPaper;
	}

	public Integer getTestYear() {
		return testYear;
	}

	public void setTestYear(Integer testYear) {
		this.testYear = testYear;
	}

	public Integer getTestMonth() {
		return testMonth;
	}

	public void setTestMonth(Integer testMonth) {
		this.testMonth = testMonth;
	}

	public Integer getTestWeek() {
		return testWeek;
	}

	public void setTestWeek(Integer testWeek) {
		this.testWeek = testWeek;
	}
}

