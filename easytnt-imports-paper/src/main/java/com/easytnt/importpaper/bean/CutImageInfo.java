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
	private Long testId = 0L;
	private Long paperId = 0L;
	private Long kemuId = 0L;
	private Long roomId = 0L;
	private Long virtualroomId = 0L;
	private Long roomType = 0L;
	private Long studentId = 0L;
	private Long itemId = 0L;
	private Long diquId = 0L;
	private String imagePath;

	public Long getPaperId() {
		return paperId;
	}

	public CutImageInfo setPaperId(Long paperId) {
		this.paperId = paperId;
		return this;
	}

	public Long getKemuId() {
		return kemuId;
	}

	public CutImageInfo setKemuId(Long kemuId) {
		this.kemuId = kemuId;
		return this;
	}

	public Long getVirtualroomId() {
		return virtualroomId;
	}

	public CutImageInfo setVirtualroomId(Long virtualroomId) {
		this.virtualroomId = virtualroomId;
		return this;
	}

	public Long getStudentId() {
		return studentId;
	}

	public CutImageInfo setStudentId(Long studentId) {
		this.studentId = studentId;
		return this;
	}

	public Long getItemId() {
		return itemId;
	}

	public CutImageInfo setItemId(Long itemId) {
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

	public Long getTestId() {
		return testId;
	}

	public CutImageInfo setTestId(Long testId) {
		this.testId = testId;
		return this;
	}

	public Long getRoomId() {
		return roomId;
	}

	public CutImageInfo setRoomId(Long roomId) {
		this.roomId = roomId;
		return this;
	}

	public Long getRoomType() {
		return roomType;
	}

	public CutImageInfo setRoomType(Long roomType) {
		this.roomType = roomType;
		return this;
	}

	public Long getDiquId() {
		return diquId;
	}

	public CutImageInfo setDiquId(Long diquId) {
		this.diquId = diquId;
		return this;
	}

	public String getName(int idx) {
		Path path = Paths.get(imagePath);
		return path.getName(idx).toString();
	}

}
