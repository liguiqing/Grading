/**
 * 
 */
package com.easytnt.grading.domain.cuttings;

import java.util.List;

import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.share.Area;

/**
 * <pre>
 * 切割定义
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CuttingDefine {
	private Long id;// 唯一ID
	private String name;// 切割块的名称
	private ExamPaper paper;// 试卷
	private int answerCardImageIdx = 0;// 答题卡图片位置
	private Area area;// 在图片中的位置
	private int requiredPinci = 1; // 必须执行的评判次数
	private Float maxerror = 0f; // 最大误差值
	private Float fullScore = 0f;
	private List<GiveScorePoint> giveScorePoints;

	public Long getId() {
		return id;
	}

	public CuttingDefine setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public CuttingDefine setName(String name) {
		this.name = name;
		return this;
	}

	public ExamPaper getPaper() {
		return paper;
	}

	public CuttingDefine setPaper(ExamPaper paper) {
		this.paper = paper;
		return this;
	}

	public int getAnswerCardImageIdx() {
		return answerCardImageIdx;
	}

	public CuttingDefine setAnswerCardImageIdx(int answerCardImageIdx) {
		this.answerCardImageIdx = answerCardImageIdx;
		return this;
	}

	public Area getArea() {
		return area;
	}

	public CuttingDefine setArea(Area area) {
		this.area = area;
		return this;
	}

	public int getRequiredPinci() {
		return requiredPinci;
	}

	public CuttingDefine setRequiredPinci(int requiredPinci) {
		this.requiredPinci = requiredPinci;
		return this;
	}

	public Float getMaxerror() {
		return maxerror;
	}

	public CuttingDefine setMaxerror(Float maxerror) {
		this.maxerror = maxerror;
		return this;
	}

	public Float getFullScore() {
		return fullScore;
	}

	public CuttingDefine setFullScore(Float fullScore) {
		this.fullScore = fullScore;
		return this;
	}

	public List<GiveScorePoint> getGiveScorePoints() {
		return giveScorePoints;
	}

	public CuttingDefine setGiveScorePoints(List<GiveScorePoint> giveScorePoints) {
		this.giveScorePoints = giveScorePoints;
		return this;
	}

}
