package com.easytnt.grading.domain.paper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.easytnt.grading.domain.share.Area;

public class ExamPaperTest {

	
	@Rule
	public final ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void before()throws Exception{
	}
	
	@Test
	public void testSave()throws Exception{
		ExamPaper examPaper = new ExamPaper();
		examPaper.setName("考卷1");
		examPaper.setPaperOid(1l);
		examPaper.setFullScore(6f);
		
		PaperType type= new PaperType();
		type.setTypeName("A卷");
		examPaper.setPaperType(type);
		
		Area area = new Area(1,1,1,1);
		
		Section section1 = new Section(examPaper,5f,"大题 1","内容1");
		section1.setArea(area);
		section1.setMaxPinci(5);
		section1.setMaxerror(5f);
		examPaper.addSection(section1);
		
		Item item1 = new Item(5f,"给分点1","给分内容1");
		item1.setAnswerArea(area);
		item1.setValidValues(new Float[]{1f,2f,3f,4f,5f});
		section1.addItem(item1);
	}
	
	@Test
	public void testAddSection()throws Exception{
		ExamPaper examPaper = new ExamPaper();
		examPaper.setName("考卷1");
		examPaper.setPaperOid(1000l);
		examPaper.setFullScore(10f);
		
		Section section1 = new Section(examPaper);
		section1.setFullScore(5f);
		examPaper.addSection(section1);
		assertNotNull(examPaper.getSections());
		assertTrue(section1.getPaper().equals(examPaper));
		assertTrue(section1.getSectionOid()==100001l);
	}
	@Test
	public void testRemoveAndAddSection()throws Exception{
		ExamPaper examPaper = new ExamPaper("语文考卷",100f);
		examPaper.setPaperOid(100l);
		Section section1 = new Section(examPaper,5f,"内容1","题目1");
		Section section2 = new Section(examPaper,5f,"内容2","题目2");
		Section section3 = new Section(examPaper,5f,"内容3","题目3");
		Section section4 = new Section(examPaper,5f,"内容4","题目4");
		Section section5 = new Section(examPaper,5f,"内容5","题目5");
		
		examPaper.addSection(section1);
		examPaper.addSection(section2);
		examPaper.addSection(section3);
		examPaper.addSection(section4);
		examPaper.addSection(section5);
		
		examPaper.removeSections(2);
		
		Section section6 = new Section(examPaper,5f,"内容6","题目6");
		examPaper.addSection(2, section6);
		assertTrue(section6.getSectionOid().equals(section3.getSectionOid()));
		
		examPaper.removeSections(1);
		examPaper.removeSections(3);
		
		Section section7 = new Section(examPaper,5f,"内容7","题目7");
		examPaper.addSection(1, section7);
		
		assertTrue(section7.getSectionOid().equals(section2.getSectionOid()));
		println(examPaper.getSections());
		
		Section section8 = new Section(examPaper,5f,"内容8","题目8");
		examPaper.addSection(3, section8);
		assertTrue(section8.getSectionOid().equals(section4.getSectionOid()));
		println(examPaper.getSections());
		
		examPaper.removeSections(0);
		
		Section section9 = new Section(examPaper,5f,"内容9","题目9");
		examPaper.addSection(0, section9);
		assertTrue(section9.getSectionOid().equals(section1.getSectionOid()));
		println(examPaper.getSections());
		
		examPaper.removeSections(4);
		
		Section section10 = new Section(examPaper,5f,"内容10","题目10");
		examPaper.addSection(4, section10);
		assertTrue(section10.getSectionOid().equals(section5.getSectionOid()));
		println(examPaper.getSections());
		
		Section section11 = new Section(examPaper,5f,"内容11","题目11");
		examPaper.addSection(4, section11);
		assertTrue(section10.getSectionOid().equals(section11.getSectionOid()));
		println(examPaper.getSections());
	}
	public void println(Set<Section> sets){
		List<Section> secList = new ArrayList<Section>();
		secList.addAll(sets);
		assertTrue(secList.get(0).getSectionOid().equals(10001l));
		assertTrue(secList.get(1).getSectionOid().equals(10002l));
		assertTrue(secList.get(2).getSectionOid().equals(10003l));
		assertTrue(secList.get(3).getSectionOid().equals(10004l));
		assertTrue(secList.get(4).getSectionOid().equals(10005l));
	}
	@Test
	public void testAddSectionWithUnsupportedOperationException()throws Exception{
		ExamPaper examPaper = new ExamPaper();
		examPaper.setPaperOid(1000l);
		examPaper.setFullScore(10f);
		
		examPaper.addSection(new Section(examPaper,5f,"1","1"));
		examPaper.addSection(new Section(examPaper,5f,"2","2"));
		
		expectedException.expect(UnsupportedOperationException.class);
		expectedException.expectMessage("试题分数大于试卷分数");
		examPaper.addSection(new Section(examPaper,5f,"3","3"));
	}
}
