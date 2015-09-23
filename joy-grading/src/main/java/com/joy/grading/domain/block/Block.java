package com.joy.grading.domain.block;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.joy.grading.domain.paper.Item;
import com.joy.grading.domain.paper.Paper;

public class Block {

	private Set<Item> items;

	private Block next;

	private Paper paper;

	private int top;

	private int right;

	private int bottom;

	private int left;

	public int hashCode() {
		return new HashCodeBuilder().append(this.paper).append(this.top).append(this.right).append(this.bottom)
						.append(this.left)
						.toHashCode();
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Block))
			return false;
		Block other = (Block) o;
		return new EqualsBuilder().append(this.paper, other.paper).append(this.top, other.top)
						.append(this.right, other.right).append(this.bottom, other.bottom).isEquals();
	}

	public String toString() {
		return new ToStringBuilder(this).append(this.paper)
						.append("potision ", this.top + "," + this.right + "," + this.bottom + "," + this.left).build();
	}

}
