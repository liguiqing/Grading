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

public class SectionTest {

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
		
		Section section1 = new Section();
		section1.setPaper(examPaper);
		section1.setArea(area);
		section1.setTitle("大题 1");
		section1.setCaption("内容1");
		section1.setMaxPinci(5);
		section1.setFullScore(5f);
		section1.setMaxerror(5f);
		examPaper.addSection(section1);
		
		Item item1 = new Item();
		//item1.setSection(section1);
		item1.setAnswerArea(area);
		item1.setTitle("给分点1");
		item1.setCaption("给分内容1");
		item1.setFullScore(5f);
		item1.setValidValues(new Float[]{1f,2f});
		section1.addItem(item1);
		
	}
	@Test
	public void testRemoveAndAddItem()throws Exception{
		ExamPaper examPaper = new ExamPaper("语文考卷",100f);
		examPaper.setPaperOid(100l);
		Section section1 = new Section(examPaper,50f,"内容1","题目1");
		examPaper.addSection(section1);
		
		Item item1 = new Item(5f, "得分标题1", "得分内容1");
		Item item2 = new Item(5f, "得分标题2", "得分内容2");
		Item item3 = new Item(5f, "得分标题3", "得分内容3");
		Item item4 = new Item(5f, "得分标题4", "得分内容4");
		Item item5 = new Item(5f, "得分标题5", "得分内容5");
		
		section1.addItem(item1);
		section1.addItem(item2);
		section1.addItem(item3);
		section1.addItem(item4);
		section1.addItem(item5);
		println(section1.getItems());
		
		section1.removeItems(2);
		
		Item item6 = new Item(5f, "得分标题6", "得分内容6");
		section1.addItem(2, item6);
		assertTrue(item6.getItemOid().equals(item3.getItemOid()));
		println(section1.getItems());
		
		section1.removeItems(1);
		section1.removeItems(3);
		
		Item item7 = new Item( 5f, "得分标题7", "得分内容7");
		section1.addItem(1, item7);
		assertTrue(item7.getItemOid().equals(item2.getItemOid()));
		
		Item item8 = new Item( 5f, "得分标题8", "得分内容8");
		section1.addItem(3, item8);
		assertTrue(item8.getItemOid().equals(item4.getItemOid()));
		println(section1.getItems());
		
		section1.removeItems(0);
		
		Item item9 = new Item( 5f, "得分标题9", "得分内容9");
		section1.addItem(0, item9);
		assertTrue(item9.getItemOid().equals(item1.getItemOid()));
		println(section1.getItems());
		
		section1.removeItems(4);
		
		Item item10 = new Item( 5f, "得分标题10", "得分内容10");
		section1.addItem(4, item10);
		assertTrue(item10.getItemOid().equals(item5.getItemOid()));
		println(section1.getItems());
		
		Item item11 = new Item( 5f, "得分标题11", "得分内容11");
		section1.addItem(4, item11);
		assertTrue(item11.getItemOid().equals(item10.getItemOid()));
		println(section1.getItems());
		
	}
	public void println(Set<Item> sets){
		List<Item> itemList = new ArrayList<Item>();
		itemList.addAll(sets);
		assertTrue(itemList.get(0).getItemOid().equals(10001001l));
		assertTrue(itemList.get(1).getItemOid().equals(10001002l));
		assertTrue(itemList.get(2).getItemOid().equals(10001003l));
		assertTrue(itemList.get(3).getItemOid().equals(10001004l));
		assertTrue(itemList.get(4).getItemOid().equals(10001005l));
	}
	@Test
	public void testAddItem()throws Exception{
		ExamPaper examPaper = new ExamPaper();
		examPaper.setName("考卷1");
		examPaper.setPaperOid(1000l);
		examPaper.setFullScore(10f);
		
		Section section1 = new Section();
		section1.setFullScore(5f);
		examPaper.addSection(section1);
		
		Item item = new Item();
		item.setFullScore(4f);
		section1.addItem(item);
		assertNotNull(section1.getItems());
		assertTrue(item.getItemOid()==100001001l);
	}
	
	@Test
	public void testAddSectionWithUnsupportedOperationException()throws Exception{
		Section section1 = new Section();
		section1.setFullScore(5f);
		section1.setSectionOid(20151111100101l);
		section1.addItem(new Item(3f,"1","1"));
		
		expectedException.expect(UnsupportedOperationException.class);
		expectedException.expectMessage("给分点大于试题分数");
		section1.addItem(new Item(3f,"2","2"));
	}
}
