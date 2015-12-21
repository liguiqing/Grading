/**
 * 
 */
package com.easytnt.cutimage.disruptor.event;

import java.util.ArrayList;
import java.util.List;

import com.easytnt.grading.domain.cuttings.OmrDefine;
import com.easytnt.grading.domain.cuttings.OrmResult;
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
	private OmrDefine omrDefine;
	private List<String> filePaths = new ArrayList<>();
	private OrmResult ormResult;

	public OrmResult getOrmResult() {
		return ormResult;
	}

	public DistinguishOMREvent setOrmResult(OrmResult ormResult) {
		this.ormResult = ormResult;
		return this;
	}

	public OmrDefine getOmrDefine() {
		return omrDefine;
	}

	public DistinguishOMREvent setOmrDefine(OmrDefine omrDefine) {
		this.omrDefine = omrDefine;
		return this;
	}

	public String getStudentId() {
		return studentId;
	}

	public DistinguishOMREvent setStudentId(String studentId) {
		this.studentId = studentId;
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
		studentId = event.studentId;
		filePaths = event.filePaths;
		omrDefine = event.omrDefine;
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
