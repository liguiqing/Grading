/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.domain.grade;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.junit.Assert.*;

import org.junit.Test;

import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.domain.cuttings.CuttingsImage;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月2日
 * @version 1.0
 **/
public class RefereesTest {

	@Test
	public void testIAmFree()throws Exception{
		Referees referees = new Referees();
		assertTrue(referees.iAmFree());
		Dispatcher dispatcher = mock(Dispatcher.class);
		CuttingsImage cuttings = mock(CuttingsImage.class);
		CuttingsImageGradeRecord gradRecord = mock(CuttingsImageGradeRecord.class);
		when(dispatcher.getFor(referees)).thenReturn(cuttings);
		when(cuttings.createRecord(referees)).thenReturn(gradRecord);
		referees.useDispatcher(dispatcher);
		referees.fetchCuttings();
		when(gradRecord.isFinished()).thenReturn(Boolean.FALSE).thenReturn(Boolean.TRUE);
		assertFalse(referees.iAmFree());
		assertTrue(referees.iAmFree());
	}
}


