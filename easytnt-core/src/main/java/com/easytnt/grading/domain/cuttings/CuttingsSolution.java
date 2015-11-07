/**
 * 
 * 
 **/

package com.easytnt.grading.domain.cuttings;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;
import com.easytnt.grading.domain.paper.ExamPaper;

/** 
 * <pre>
 * 切割方案，即一个考卷切割成多少份
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class CuttingsSolution implements ValueObject<CuttingsSolution>{
	
	private ExamPaper designFor;
	
	private List<CuttingsArea> cutTo;
	
	public static CuttingsSolution newSolutionFor(ExamPaper designFor) {
		CuttingsSolution solution = new CuttingsSolution();
		return solution;
	}
	
	public void newCuttingsDefines(CuttingsArea area) {
		initCutTo();
		this.cutTo.add(area);
	}
	
	private void initCutTo() {
		if(this.cutTo == null)
			this.cutTo = new ArrayList<>();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.designFor).append(this.cutTo).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CuttingsSolution))
			return false;
		CuttingsSolution other = (CuttingsSolution) o;

		return new EqualsBuilder().append(this.designFor, other.designFor).append(this.cutTo,other.cutTo).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.designFor).append(this.cutTo).build();
	}
	
	@Override
	public boolean sameValueAs(CuttingsSolution other) {
		return this.equals(other);
	}

	
	//以下功能为ORM或者自动构造使用，非此慎用
	public CuttingsSolution() {
		
	}
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExamPaper getDesignFor() {
		return designFor;
	}

	public void setDesignFor(ExamPaper designFor) {
		this.designFor = designFor;
	}

	public List<CuttingsArea> getCutTo() {
		return cutTo;
	}

	public void setCutTo(List<CuttingsArea> cutTo) {
		this.cutTo = cutTo;
	}

}

