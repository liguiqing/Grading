/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.domain.grade;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;

import org.junit.Test;

import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.CuttingsImage;
import com.easytnt.grading.domain.paper.Item;
import com.easytnt.grading.domain.paper.Section;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月31日
 * @version 1.0
 **/
public class CuttingsImageGradeRecordTest {

	@Test
	public void testScoringForItems()throws Exception{
		Referees referees = mock(Referees.class);
		CuttingsImage recordFor = mock(CuttingsImage.class);
		
		CuttingsImageGradeRecord record = new CuttingsImageGradeRecord(referees,recordFor);
		
		CuttingsArea area = new CuttingsArea();
		area.setId(1l);
		Section section = new Section();
		
		section.setTitle("二、填空题");
		section.setCaption("二、填空题");
		//area.bindSection(section);
		Item item1 = mock(Item.class);
		when(item1.isEffectiveScore(isA(Float.class))).thenReturn(Boolean.TRUE);
		section.addItem(item1);
		section.addItem(item1);
		section.addItem(item1);
		section.addItem(item1);
		
		ArrayList<Section> al = new ArrayList<>();
		al.add(section);
		when(recordFor.getSections()).thenReturn(al);
		
		record.scoringForItems(new Float[] {1.1f,2f,1f,2f});
		assertEquals("1.1,2,1,2",record.getScorestr());
		
		record.scoringForItems(new Float[] {1f,2f,1f,2f});
		assertEquals("1,2,1,2",record.getScorestr());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testScoringForItemsException()throws Exception{
		Referees referees = mock(Referees.class);
		CuttingsImage recordFor = mock(CuttingsImage.class);
		
		CuttingsImageGradeRecord record = new CuttingsImageGradeRecord(referees,recordFor);
		
		CuttingsArea area = new CuttingsArea();
		area.setId(1l);
		Section section = new Section();
		
		section.setTitle("二、填空题");
		section.setCaption("二、填空题");
		//area.bindSection(section);
		Item item1 = mock(Item.class);
		when(item1.isEffectiveScore(isA(Float.class))).thenReturn(Boolean.FALSE);
		section.addItem(item1);
		section.addItem(item1);
		section.addItem(item1);
		section.addItem(item1);
		
		ArrayList<Section> al = new ArrayList<>();
		al.add(section);
		when(recordFor.getSections()).thenReturn(al);
		
		record.scoringForItems(new Float[] {1f,2f,1f,2f});
	}
	
	@Test
	public void testCalScore()throws Exception{
		Referees referees = mock(Referees.class);
		CuttingsImage recordFor = mock(CuttingsImage.class);
		
		CuttingsImageGradeRecord record = new CuttingsImageGradeRecord(referees,recordFor);
		
		CuttingsArea area = new CuttingsArea();
		area.setId(1l);
		Section section = new Section();
		
		section.setTitle("二、填空题");
		section.setCaption("二、填空题");
		//area.bindSection(section);
		for(long i =1l;i<=4l;i++) {
			Item item1 = mock(Item.class);
			doNothing().when(item1).setItemId(i);
			when(item1.isEffectiveScore(isA(Float.class))).thenReturn(Boolean.TRUE);
			section.addItem(item1);
		}
		
		ArrayList<Section> al = new ArrayList<>();
		al.add(section);
		when(recordFor.getSections()).thenReturn(al);
		
		record.scoringForItems(new Float[] {1f,2f,1f,2f});
		
		record.finish();
		
		Float score = record.calScore();
		assertTrue(score.equals(6f));
	}
}


