/**
 * 
 */
package com.easytnt.importpaper.disruptor.event;

import com.easytnt.importpaper.bean.CutImageInfo;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventTranslator;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class CutImageEvent {

	public static final EventFactory<CutImageEvent> FACTORY = new EventFactory<CutImageEvent>() {

		@Override
		public CutImageEvent newInstance() {
			return new CutImageEvent();
		}

	};

	private boolean isOver = false;
	private CutImageInfo cutImageInfo;

	public boolean isOver() {
		return isOver;
	}

	public CutImageEvent setOver(boolean isOver) {
		this.isOver = isOver;
		return this;
	}

	public CutImageInfo getCutImageInfo() {
		return cutImageInfo;
	}

	public CutImageEvent setCutImageInfo(CutImageInfo cutImageInfo) {
		this.cutImageInfo = cutImageInfo;
		return this;
	}

	public void clone(CutImageEvent event) {
		this.isOver = event.isOver;
		this.cutImageInfo = event.cutImageInfo;
	}

	public static class CutImageEventTranslator implements EventTranslator<CutImageEvent> {
		private CutImageEvent event;

		public CutImageEventTranslator(CutImageEvent event) {
			this.event = event;
		}

		@Override
		public void translateTo(CutImageEvent event, long sequence) {
			event.clone(this.event);
		}

	}

}
