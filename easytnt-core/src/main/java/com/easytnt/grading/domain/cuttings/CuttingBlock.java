/**
 * 
 */
package com.easytnt.grading.domain.cuttings;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.grading.domain.share.Area;

/**
 * <pre>
 * 切割块
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CuttingBlock {
	private Long key;
	private String name;
	private int width;
	private int height;
	private List<CuttingDefine> cuttingDefines;

	public Long getKey() {
		return key;
	}

	public CuttingBlock setKey(Long key) {
		this.key = key;
		return this;
	}

	public String getName() {
		return name;
	}

	public CuttingBlock setName(String name) {
		this.name = name;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public CuttingBlock setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public CuttingBlock setHeight(int height) {
		this.height = height;
		return this;
	}

	public List<CuttingDefine> getCuttingDefines() {
		return cuttingDefines;
	}

	public CuttingBlock setCuttingDefines(List<CuttingDefine> cuttingDefines) {
		this.cuttingDefines = cuttingDefines;
		return this;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(key).append(name).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CuttingBlock)) {
			return false;
		}
		CuttingBlock tmp = (CuttingBlock) obj;

		return new EqualsBuilder().append(key, tmp.key).append(name, tmp.name).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("key:", key).append("name:", name).append("width:", width)
				.append("height:", height).build();
	}

	public CuttingsArea toCuttingsArea() {
		CuttingDefine cutDef = cuttingDefines.get(0);
		CuttingsArea cuttingsArea = new CuttingsArea();
		cuttingsArea.setName(cutDef.getName());
		cuttingsArea.setRequiredPinci(cutDef.getRequiredPinci());
		cuttingsArea.setMaxerror(cutDef.getMaxerror());
		cuttingsArea.setFullScore(getFullScore());
		cuttingsArea.setItemAreas(getPositionOfItemInArea());
		return cuttingsArea;
	}

	private float getFullScore() {
		float fullScore = 0f;
		for (CuttingDefine cutDef : cuttingDefines) {
			fullScore += cutDef.getFullScore();
		}
		return fullScore;
	}

	private List<PositionOfItemInArea> getPositionOfItemInArea() {
		ArrayList<PositionOfItemInArea> items = new ArrayList<>();
		int top = 0;
		for (CuttingDefine cutDef : cuttingDefines) {
			Area area = cutDef.getArea();
			for (GiveScorePoint giveScorePoint : cutDef.getGiveScorePoints()) {
				PositionOfItemInArea item = createItem(giveScorePoint);
				Area tempArea = area.clone();
				tempArea.setLeft(0);
				tempArea.setTop(top);
				item.setAreaIn(tempArea);
				items.add(item);
			}
			top = area.getHeight();
		}
		return items;
	}

	private PositionOfItemInArea createItem(GiveScorePoint giveScorePoint) {
		PositionOfItemInArea item = new PositionOfItemInArea();
		item.setName(giveScorePoint.getName());
		item.setFullScore(giveScorePoint.getFullScore());
		item.setValidValues(giveScorePoint.getValidValues());
		return item;
	}

}
