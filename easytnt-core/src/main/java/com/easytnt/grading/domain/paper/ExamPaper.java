/**
 * 
 * 
 **/

package com.easytnt.grading.domain.paper;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.Entity;
import com.easytnt.grading.domain.cuttings.AnswerCardCuttingTemplate;

/**
 * <pre>
 * 考卷，就是一类试卷，如语文试卷，数学试卷，理科综合试卷
 * </pre>
 * 
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class ExamPaper implements Entity<ExamPaper> {

	private String name;

	private PaperType paperType;

	private Long paperId;

	private List<Section> sections;

	private Float fullScore;

	private int answerCardImageNum;// 答题卡图片数量

	private List<AnswerCardCuttingTemplate> answerCardCuttingTemplates;

	private String cuttingRootPath;

	public ExamPaper(String name, Float fullScore) {
		this.name = name;
		this.fullScore = fullScore;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.paperId).toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ExamPaper))
			return false;
		ExamPaper other = (ExamPaper) o;

		return new EqualsBuilder().append(this.paperId, other.paperId).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append(this.name).append(this.paperId).build();
	}

	@Override
	public boolean sameIdentityAs(ExamPaper other) {
		return this.equals(other);
	}

	// 以下功能为ORM或者自动构造使用，非此慎用
	public ExamPaper() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public Float getFullScore() {
		return fullScore;
	}

	public void setFullScore(Float fullScore) {
		this.fullScore = fullScore;
	}

	public PaperType getPaperType() {
		return paperType;
	}

	public void setPaperType(PaperType paperType) {
		this.paperType = paperType;
	}

	public int getAnswerCardImageNum() {
		return answerCardImageNum;
	}

	public void setAnswerCardImageNum(int answerCardImageNum) {
		this.answerCardImageNum = answerCardImageNum;
	}

	public List<AnswerCardCuttingTemplate> getAnswerCardCuttingTemplates() {
		return answerCardCuttingTemplates;
	}

	public void setAnswerCardCuttingTemplates(List<AnswerCardCuttingTemplate> answerCardCuttingTemplates) {
		this.answerCardCuttingTemplates = answerCardCuttingTemplates;
	}

	public String getCuttingRootPath() {
		return cuttingRootPath;
	}

	public void setCuttingRootPath(String cuttingRootPath) {
		if (!cuttingRootPath.endsWith("/") && !cuttingRootPath.endsWith("\\")) {
			cuttingRootPath += "/";
		}
		this.cuttingRootPath = cuttingRootPath;
	}

}
