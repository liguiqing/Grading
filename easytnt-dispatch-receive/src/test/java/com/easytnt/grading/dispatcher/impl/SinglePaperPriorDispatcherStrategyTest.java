/**
 * 
 * 
 **/

package com.easytnt.grading.dispatcher.impl;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.easytnt.grading.dispatcher.Block;

/** 
 * <pre>
 * 
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public class SinglePaperPriorDispatcherStrategyTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testNullQueue()throws Exception{
		SinglePaperPriorDispatcherStrategy spds = new SinglePaperPriorDispatcherStrategy();
		
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("评卷队列为空！");
		
		spds.getDispatcherQueue(null);
	}
	
	@Test
	public void testetDispatcherQueue()throws Exception{
		SinglePaperPriorDispatcherStrategy spds = new SinglePaperPriorDispatcherStrategy(1);
		ArrayList pinci1 = new ArrayList<Queue<Block>>();
		Queue<Block> q1 = createQueue(2);
		pinci1.add(q1);
		assertEquals(spds.getDispatcherQueue(pinci1),q1);
		assertNotEquals(spds.getDispatcherQueue(pinci1),new ArrayDeque<Block>());
		
		Queue<Block> q2 = createQueue(2);
		
		pinci1.add(q2);
		assertEquals(spds.getDispatcherQueue(pinci1),q1);
		assertNotEquals(spds.getDispatcherQueue(pinci1),new ArrayDeque<Block>());
		assertNotEquals(spds.getDispatcherQueue(pinci1),q2);
		
		spds = new SinglePaperPriorDispatcherStrategy(2);
		assertEquals(spds.getDispatcherQueue(pinci1),q2);
		assertNotEquals(spds.getDispatcherQueue(pinci1),new ArrayDeque<Block>());
		assertNotEquals(spds.getDispatcherQueue(pinci1),q1);
	}
	
	private Queue<Block> createQueue(int size){
		Queue<Block> q = new ArrayDeque<Block>();
		for(int i = 0;i<size;i++) {
			Block block = mock(Block.class);
			q.add(block);
		}
		return q;
	}
}

