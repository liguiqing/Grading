/**
 * 
 * 
 **/

package com.easytnt.grading.domain.room;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.easytnt.commons.entity.share.Entity;

/** 
 * <pre>
 * 考生
 * </pre>
 *  
 * @author 李贵庆2015年10月14日
 * @version 1.0
 **/
public class Examinee implements Entity<Examinee>{

	private Long uuid;
	
	private String name;
	
	private Room room;
	
	public Examinee(Long uuid,String name,Room room) {
		this.uuid = uuid;
		this.name = name;
		this.room = room;
	}
	
	public Examinee(Long uuid,Room room) {
		this.uuid = uuid;
		this.room = room;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.uuid).toHashCode();
	}
	
    @Override
	public boolean equals(Object o) {
		if(!(o instanceof Examinee))
			return false;
		Examinee other = (Examinee)o;
		
		return new EqualsBuilder().append(this.uuid,other.uuid).isEquals();
	}
	
    @Override
	public String toString() {
		return new ToStringBuilder(this).append(this.uuid).build();
	}
    
	@Override
	public boolean sameIdentityAs(Examinee other) {
		return this.equals(other);
	}
	
	public Examinee(){
		
	}
	
	private Long id;

	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name==null?"":name.trim();
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

