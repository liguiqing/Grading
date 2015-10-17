/**
 * 
 */
package com.easytnt.importpaper.bean;

/**
 * @author liuyu
 *
 */
public class DirectoryMapping {
	private int paperId;
	private int place;
	private String mappingName;

	public int getPlace() {
		return place;
	}

	public int getPaperId() {
		return paperId;
	}

	public DirectoryMapping setPaperId(int paperId) {
		this.paperId = paperId;
		return this;
	}

	public DirectoryMapping setPlace(int place) {
		this.place = place;
		return this;
	}

	public String getMappingName() {
		return mappingName;
	}

	public DirectoryMapping setMappingName(String mappingName) {
		this.mappingName = mappingName;
		return this;
	}

}
