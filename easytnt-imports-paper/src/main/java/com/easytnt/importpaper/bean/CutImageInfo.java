/**
 * 
 */
package com.easytnt.importpaper.bean;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CutImageInfo {
	private int paperId;
	private int kemuId;
	private long virtualroomId;
	private long studentId;
	private int itemId;
	private String imagePath;

	public int getPaperId() {
		return paperId;
	}

	public CutImageInfo setPaperId(int paperId) {
		this.paperId = paperId;
		return this;
	}

	public int getKemuId() {
		return kemuId;
	}

	public CutImageInfo setKemuId(int kemuId) {
		this.kemuId = kemuId;
		return this;
	}

	public long getVirtualroomId() {
		return virtualroomId;
	}

	public CutImageInfo setVirtualroomId(long virtualroomId) {
		this.virtualroomId = virtualroomId;
		return this;
	}

	public long getStudentId() {
		return studentId;
	}

	public CutImageInfo setStudentId(long studentId) {
		this.studentId = studentId;
		return this;
	}

	public int getItemId() {
		return itemId;
	}

	public CutImageInfo setItemId(int itemId) {
		this.itemId = itemId;
		return this;
	}

	public String getImagePath() {
		return imagePath;
	}

	public CutImageInfo setImagePath(String imagePath) {
		this.imagePath = imagePath;
		return this;
	}

}
