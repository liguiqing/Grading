/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

import org.junit.Test;






import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.util.ThreadExcutor;
import com.easytnt.grading.dispatcher.DispatcherStrategy;
import com.easytnt.grading.fetch.Fetcher;
import com.easytnt.grading.share.ImgCuttings;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public class DispatcherImplTest {
	private static Logger logger = LoggerFactory.getLogger(DispatcherImplTest.class);
	@Test
	public void testGet()throws Exception{
		
		DispatcherStrategy dispatcherStrategy = new SinglePaperPriorDispatcherStrategy(1); //mock(DispatcherStrategy.class);
		Fetcher blockFetcher = mock(Fetcher.class);
		ConcurrentLinkedDeque<ImgCuttings> queue = new ConcurrentLinkedDeque<ImgCuttings>();
		List<ImgCuttings> blocks = getMockImgCuttings();
		queue.addAll(blocks);
		//when(dispatcherStrategy.getDispatcherQueue(isA(List.class))).thenReturn(queue);
		when(blockFetcher.fetch(isA(Integer.class))).thenReturn(blocks);
		DispatcherImpl dispather = new DispatcherImpl(dispatcherStrategy,blockFetcher);
		//Thread.currentThread().join();
		for(int i = 0;i<blocks.size();i++) {
			dispather.get();
			Thread.sleep(10);
		}
		Thread.sleep(5000);
		
		assertNotNull(dispather.get());
		assertNotNull(dispather.get());
		dispather.stop();
		Thread.sleep(1000);
	}
	
	private List<ImgCuttings>  getMockImgCuttings() {
		ArrayList<ImgCuttings> blocks= new ArrayList<ImgCuttings>();
		for(int i = 0;i<1000;i++) {
			ImgCuttings cuttings  = mock(ImgCuttings.class);
			when(cuttings.getCurrentPinci()).thenReturn(1);
			doNothing().when(cuttings).nextPinci();
			when(cuttings.toString()).thenReturn("Cuttings" +i);
			doNothing().when(cuttings).nextPinci();
			blocks.add(cuttings);
		}
		return blocks;
	}
	
	@Test
	public void testGetAsync()throws Exception{
		DispatcherStrategy dispatcherStrategy = new SinglePaperPriorDispatcherStrategy(1); //mock(DispatcherStrategy.class);
		Fetcher blockFetcher = mock(Fetcher.class);
		ConcurrentLinkedDeque<ImgCuttings> queue = new ConcurrentLinkedDeque<ImgCuttings>();
		List<ImgCuttings> blocks = getImgCuttings();
		queue.addAll(blocks);
		//when(dispatcherStrategy.getDispatcherQueue(isA(List.class))).thenReturn(queue);
		when(blockFetcher.fetch(isA(Integer.class))).thenReturn(blocks);
		
		final DispatcherImpl dispather = new DispatcherImpl(dispatcherStrategy,blockFetcher);
		
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO: handle exception
		}
		final CountDownLatch countDown = new CountDownLatch(100);
		ArrayList<Runnable> refereeses = new ArrayList<>();
		
		final ArrayList<ImgCuttings> pin1 = new ArrayList<>();
		final ArrayList<ImgCuttings> pin2 = new ArrayList<>();
		final AtomicInteger countnum=new AtomicInteger();
		for( int i=0;i<100;i++) {
			final int a = i;
			refereeses.add(new Runnable() {

				@Override
				public void run() {
					
					logger.debug( a + " I am working");
//					
					ImgCuttings ic = dispather.get();
					if(ic != null) {
						logger.debug(ic.toString());						
					}
					
//					
//					if(ic.getCurrentPinci() ==1)
//						pin1.add(ic);
//					
//					if(ic.getCurrentPinci() ==2)
//						pin2.add(ic);
////					System.out.println("************"+countnum.incrementAndGet());
					try {
						Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
			}
					
					countDown.countDown();
//					countDown.countDown();
//					ic = dispather.get();
//					logger.debug(ic.toString());
//					if(ic.getCurrentPinci() ==1)
//						pin1.add(ic);
//					
//					if(ic.getCurrentPinci() ==2)
//						pin2.add(ic);
//					
//					logger.debug( a + " I am stop");
					
				}
				
			});
		}
		
		for(int i = 0;i<100;i++) {
			ThreadExcutor.getInstance().submit(refereeses.get(i));
		}
		
		System.out.println("over.... end");
		countDown.await();
		
	}
	
	private List<ImgCuttings>  getImgCuttings() {
		
		ArrayList<ImgCuttings> blocks= new ArrayList<ImgCuttings>();
		for(int i = 0;i<100;i++) {
			ImgCuttings cuttings  = new MockImgCuttings(i+"");
			blocks.add(cuttings);
		}
		return blocks;
	}
	
	private static class MockImgCuttings implements ImgCuttings{
		int pinci = 0;
		String uuid ;
		
		public MockImgCuttings(String uuid) {
			this.uuid = uuid;
		}
		
		@Override
		public int getCurrentPinci() {
			return pinci;
		}

		@Override
		public void nextPinci() {
			++pinci;
		}

		@Override
		public int incrementPinciAndGet() {
			return ++pinci;
		}
		
		@Override
		public String toString() {
			return this.uuid + " -- " + pinci;
		}
		
		public boolean equals(Object o) {
			MockImgCuttings other  = (MockImgCuttings)o;
			return this.uuid.equals(other.uuid) && this.pinci == other.pinci;
		}
		
	}
}

