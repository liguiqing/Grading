/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.domain.share;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.ValueObject;

/**
 * <pre>
 * 试卷图片或者切割块图像中一块区域
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/
public class Area implements ValueObject<Area> {

	private int left;

	private int top;

	private int width;

	private int height;

	public Area() {
	}

	public Area(int left, int top, int width, int height) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.left).append(this.top).append(this.width).append(this.height)
				.toHashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Area))
			return false;
		Area other = (Area) o;

		return new EqualsBuilder().append(this.left, other.left).append(this.top, other.top)
				.append(this.width, other.width).append(this.height, other.height).isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Left:", this.left).append("Top:", this.top)
				.append("Width:", this.width).append("Height:", this.height).build();
	}

	@Override
	public boolean sameValueAs(Area other) {
		return this.equals(other);
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Area clone() {
		Area area = new Area();
		area.left = left;
		area.top = top;
		area.width = width;
		area.height = height;
		return area;
	}

}
