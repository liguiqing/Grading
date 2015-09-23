package com.joy.grading.domain.block;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.joy.commons.entity.share.Entity;
import com.joy.grading.domain.paper.Item;
import com.joy.grading.domain.paper.Paper;

public class Block implements Entity<Block>{

	private Set<Item> items;

	private Block next;

	private Paper paper;

	private int top;

	private int right;

	private int bottom;

	private int left;

	private String uuid;

	@Override
	public boolean sameIdentityAs(Block other) {
		return new EqualsBuilder().append(this.paper, other.paper).append(this.top, other.top)
						.append(this.right, other.right).append(this.bottom, other.bottom).isEquals();
	}
	
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
		return sameIdentityAs((Block)o);
	}

	public String toString() {
		return new ToStringBuilder(this).append(this.paper)
						.append("potision ", this.top + "," + this.right + "," + this.bottom + "," + this.left).build();
	}
	
	private Long id;

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public Block getNext() {
		return next;
	}

	public void setNext(Block next) {
		this.next = next;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
