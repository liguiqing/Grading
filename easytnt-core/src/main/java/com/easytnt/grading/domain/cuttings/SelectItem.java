/**
 * 
 */
package com.easytnt.grading.domain.cuttings;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.grading.domain.paper.ExamPaper;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class SelectItem {
	private Long id;
	private String name;
	private String answer;
	private float fullScore;
	private boolean singleSelect = true;
	private List<String> giveScoreRule;
	private List<Float> giveScoreRuleScore;
	private ExamPaper paper;
	private List<SelectItemArea> areas;

	private int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<SelectItemArea> getAreas() {
		return areas;
	}

	public SelectItem setAreas(List<SelectItemArea> areas) {
		this.areas = areas;
		return this;
	}

	public Long getId() {
		return id;
	}

	public SelectItem setId(Long id) {
		this.id = id;
		return this;
	}

	public ExamPaper getPaper() {
		return paper;
	}

	public SelectItem setPaper(ExamPaper paper) {
		this.paper = paper;
		return this;
	}

	public boolean isSingleSelect() {
		return singleSelect;
	}

	public SelectItem setSingleSelect(boolean singleSelect) {
		this.singleSelect = singleSelect;
		return this;
	}

	public String getName() {
		return name;
	}

	public SelectItem setName(String name) {
		this.name = name;
		return this;
	}

	public String getAnswer() {
		return answer;
	}

	public SelectItem setAnswer(String answer) {
		this.answer = answer;
		return this;
	}

	public float getFullScore() {
		return fullScore;
	}

	public SelectItem setFullScore(float fullScore) {
		this.fullScore = fullScore;
		return this;
	}

	public List<String> getGiveScoreRule() {
		return giveScoreRule;
	}

	public SelectItem setGiveScoreRule(List<String> giveScoreRule) {
		this.giveScoreRule = giveScoreRule;
		return this;
	}

	public List<Float> getGiveScoreRuleScore() {
		return giveScoreRuleScore;
	}

	public SelectItem setGiveScoreRuleScore(List<Float> giveScoreRuleScore) {
		this.giveScoreRuleScore = giveScoreRuleScore;
		return this;
	}

	public SelectItem setGieveScoreRuleCharacter(String gieveScoreRuleCharacter) {
		genGiveScoreRule(gieveScoreRuleCharacter);
		return this;
	}

	private void genGiveScoreRule(String gieveScoreRuleCharacter) {
		String[] rules = gieveScoreRuleCharacter.split(",");
		if (rules.length > 0) {
			giveScoreRule = new ArrayList<>();
			for (String rule : rules) {
				giveScoreRule.add(rule);
			}
		}
	}

	public String getGieveScoreRuleCharacter() {
		return genGiveScoreRuleCharacter();
	}

	private String genGiveScoreRuleCharacter() {
		if (giveScoreRule != null && giveScoreRule.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (String value : giveScoreRule) {
				sb.append(value).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
		return null;
	}

	public SelectItem setGieveScoreRuleScoreCharacter(String gieveScoreRuleScoreCharacter) {
		genGiveScoreRuleScore(gieveScoreRuleScoreCharacter);
		return this;
	}

	private void genGiveScoreRuleScore(String gieveScoreRuleScoreCharacter) {
		String[] rules = gieveScoreRuleScoreCharacter.split(",");
		if (rules.length > 0) {
			giveScoreRuleScore = new ArrayList<>();
			for (String rule : rules) {
				giveScoreRuleScore.add(Float.parseFloat(rule));
			}
		}
	}

	public String getGieveScoreRuleScoreCharacter() {
		return genGiveScoreRuleScoreCharacter();
	}

	private String genGiveScoreRuleScoreCharacter() {
		if (giveScoreRuleScore != null && giveScoreRuleScore.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (Float value : giveScoreRuleScore) {
				sb.append(value).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
		return null;
	}

	public SelectItem addAreas(SelectItemArea area) {
		if (areas == null) {
			areas = new ArrayList<>();
		}
		areas.add(area);
		return this;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(name).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SelectItem))
			return false;
		SelectItem other = (SelectItem) obj;

		return new EqualsBuilder().append(this.id, other.id).append(this.name, other.name).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id:", id).append("name:", name).append("answer:", answer)
				.append("fullScore:", fullScore).build();
	}

}
