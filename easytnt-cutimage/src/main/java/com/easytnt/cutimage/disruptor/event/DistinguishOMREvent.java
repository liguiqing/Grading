/**
 * 
 */
package com.easytnt.cutimage.disruptor.event;

import java.util.ArrayList;
import java.util.List;

import com.easytnt.grading.domain.cuttings.SelectItem;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslator;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class DistinguishOMREvent {
	private String studentId;
	private ExamPaper paper;
	private List<SelectItem> items;
	private List<String> filePaths = new ArrayList<>();

	public ExamPaper getPaper() {
		return paper;
	}

	public DistinguishOMREvent setPaper(ExamPaper paper) {
		this.paper = paper;
		return this;
	}

	public String getStudentId() {
		return studentId;
	}

	public DistinguishOMREvent setStudentId(String studentId) {
		this.studentId = studentId;
		return this;
	}

	public List<SelectItem> getItems() {
		return items;
	}

	public DistinguishOMREvent setItems(List<SelectItem> items) {
		this.items = items;
		return this;
	}

	public List<String> getFilePaths() {
		return filePaths;
	}

	public DistinguishOMREvent setFilePaths(List<String> filePaths) {
		this.filePaths = filePaths;
		return this;
	}

	protected void clone(DistinguishOMREvent event) {
		items = event.items;
		studentId = event.studentId;
		filePaths = event.filePaths;
		paper = event.paper;
	}

	public static final EventFactory<DistinguishOMREvent> FACTORY = new EventFactory<DistinguishOMREvent>() {
		@Override
		public DistinguishOMREvent newInstance() {
			return new DistinguishOMREvent();
		}
	};

	public static class DistinguishOMREventTranslator implements EventTranslator<DistinguishOMREvent> {
		private DistinguishOMREvent event;

		public DistinguishOMREventTranslator(DistinguishOMREvent event) {
			this.event = event;
		}

		@Override
		public void translateTo(DistinguishOMREvent event, long sequence) {
			event.clone(this.event);
		}

	}

}
