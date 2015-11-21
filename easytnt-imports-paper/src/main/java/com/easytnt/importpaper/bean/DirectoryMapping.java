/**
 * 
 */
package com.easytnt.importpaper.bean;

/**
 * @author liuyu
 *
 */
public class DirectoryMapping {
	private int place;
	private MappingName mappingName;

	public int getPlace() {
		return place;
	}

	public DirectoryMapping setPlace(int place) {
		this.place = place;
		return this;
	}

	public MappingName getMappingName() {
		return mappingName;
	}

	public DirectoryMapping setMappingName(MappingName mappingName) {
		this.mappingName = mappingName;
		return this;
	}

}
