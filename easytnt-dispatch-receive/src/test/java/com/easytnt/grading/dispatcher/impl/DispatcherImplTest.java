/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import static org.junit.Assert.*;

import org.junit.Test;


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
			when(cuttings.toString()).thenReturn("Cuttins" +i);
			doNothing().when(cuttings).nextPinci();
			blocks.add(cuttings);
		}
		return blocks;
	}
}

