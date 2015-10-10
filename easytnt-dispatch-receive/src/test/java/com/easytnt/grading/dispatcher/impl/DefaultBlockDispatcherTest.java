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

import com.easytnt.grading.dispatcher.Block;
import com.easytnt.grading.dispatcher.BlockFetcher;
import com.easytnt.grading.dispatcher.DispatcherStrategy;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public class DefaultBlockDispatcherTest {

	@Test
	public void testGet()throws Exception{
		
		DispatcherStrategy dispatcherStrategy = new SinglePaperPriorDispatcherStrategy(1); //mock(DispatcherStrategy.class);
		BlockFetcher blockFetcher = mock(BlockFetcher.class);
		ConcurrentLinkedDeque<Block> queue = new ConcurrentLinkedDeque<Block>();
		List<Block> blocks = getMockBlocks();
		queue.addAll(blocks);
		//when(dispatcherStrategy.getDispatcherQueue(isA(List.class))).thenReturn(queue);
		when(blockFetcher.fetch(isA(Integer.class))).thenReturn(blocks);
		DefaultBlockDispatcher dispather = new DefaultBlockDispatcher(dispatcherStrategy,blockFetcher);
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
	
	private List<Block>  getMockBlocks() {
		ArrayList<Block> blocks= new ArrayList<Block>();
		for(int i = 0;i<1000;i++) {
			Block block  = mock(Block.class);
			when(block.getCurrentPinci()).thenReturn(1);
			when(block.toString()).thenReturn("Block" +i);
			doNothing().when(block).nextPinci();
			blocks.add(block);
		}
		return blocks;
	}
}

