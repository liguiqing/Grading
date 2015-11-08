/**
 * 
 */
package com.easytnt.importpaper.bean;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CutImageInfo {
	private int testId;
	private int paperId;
	private int kemuId;
	private long roomId;
	private long virtualroomId;
	private int roomType;
	private long studentId;
	private int itemId;
	private int diquId;
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

	public int getTestId() {
		return testId;
	}

	public CutImageInfo setTestId(int testId) {
		this.testId = testId;
		return this;
	}

	public long getRoomId() {
		return roomId;
	}

	public CutImageInfo setRoomId(long roomId) {
		this.roomId = roomId;
		return this;
	}

	public int getRoomType() {
		return roomType;
	}

	public CutImageInfo setRoomType(int roomType) {
		this.roomType = roomType;
		return this;
	}

	public int getDiquId() {
		return diquId;
	}

	public CutImageInfo setDiquId(int diquId) {
		this.diquId = diquId;
		return this;
	}

	public String getName(int idx) {
		Path path = Paths.get(imagePath);
		return path.getName(idx).toString();
	}

}
