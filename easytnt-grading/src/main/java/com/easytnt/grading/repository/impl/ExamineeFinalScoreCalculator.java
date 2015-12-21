/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.domain.room.Examinee;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年12月20日
 * @version 1.0
 **/
public class ExamineeFinalScoreCalculator {
	private JdbcTemplate jdbcTemplate;
	
	private SessionFactory sessionFactroy;
	
	private Long testId;
	
	private Exam exam;
	
	private List<ExamPaper> examPapers;
	
	private List<ExamineeTotalScore> totalScores;
	
	private static ConcurrentHashMap<Long, ExamineeFinalScoreCalculator> pool  = new ConcurrentHashMap<>();
	
	public static ExamineeFinalScoreCalculator newCalculator(Long testId) {
		ExamineeFinalScoreCalculator calculator = pool.get(testId);
		if(calculator != null)
			return calculator;
		
		calculator = new ExamineeFinalScoreCalculator();
		calculator.testId = testId;
		calculator.totalScores = new ArrayList<>();
		pool.put(testId,calculator);
		return calculator;
	}
	
	/**
	 * 计算分一个考生的总分
	 * @param examineeuuid
	 * @return
	 * @throws UnsupportedOperationException 该考生试卷没有改完成时抛出
	 */
	public String outputFinalScoreOf(String examineeuuid) throws UnsupportedOperationException{
		Session session = this.sessionFactroy.openSession();
		Examinee examinee = getExaminee(session, examineeuuid);
		initExam(session);
		initExamPapers(session);
		calTotalScore(examinee);
		session.close();
		return "";
	}
	
	private void calTotalScore(Examinee examinee) {
		ExamineeTotalScore totalScore = new ExamineeTotalScore(examinee);
		for(ExamPaper paper:this.examPapers) {
			Set<Section> sections = paper.getSections();
			ArrayList<Long> allSectionIds = new ArrayList<>();
			for(Section section : sections) {
				allSectionIds.add(section.getSectionId());
			}
			ExamPaperScore paperScore = calAExamPaper(paper,allSectionIds,examinee);
			totalScore.addPaperScore(paperScore);
		}
		totalScore.calScore();
		this.totalScores.add(totalScore);
	}
	
	private Examinee getExaminee(Session session,String uuid) {
		String hql = "From Examinee where uuid=?";
		Query query = session.createQuery(hql);
		query.setString(0, uuid);
		return (Examinee)query.uniqueResult();
	}
	
	private ExamPaperScore calAExamPaper(ExamPaper paper,List<Long> allSectionIds,
			Examinee examinee) {
		String sql = " select a.itemid,b.itemname,a.score from lastscore a  INNER JOIN paperiteminfo b ON b.itemid=a.itemid where a.itemid=? and a.studentoid=?";
		ArrayList args = new ArrayList();
		StringBuffer newSql = new StringBuffer();
		int i = 0;
		for(Long sectionId:allSectionIds) {
			newSql.append(sql);
			args.add(sectionId);
			args.add(examinee.getUuid());
			if(i< allSectionIds.size()-1) {
			   newSql.append(" union ");
			}
			i++;
		}
		Object[] newOrgs = new Object[args.size()];
		for(i=0;i<args.size();i++) {
			newOrgs[i] = args.get(i);
		}
		
		final ExamPaperScore paperScore = new ExamPaperScore(paper,examinee);
		paperScore.requiredItems(allSectionIds);
		jdbcTemplate.query(newSql.toString(), newOrgs, new RowMapper<ItemScore>() {

			@Override
			public ItemScore mapRow(ResultSet rs, int rowNum) throws SQLException {
				//ItemScore score = new ItemScore(rs.getLong(1),rs.getString(2),rs.getFloat(3));
				paperScore.addItemScore(rs.getLong(1),rs.getString(2),rs.getFloat(3));
				return null;
			}
			
		});
		return paperScore;
	}

	private void initExam(Session session) {
		if(this.exam == null ) {
			this.exam = (Exam)session.load(Exam.class, testId);
		}
	}
	
	private void initExamPapers(Session session) {
		if(this.examPapers == null) {
			String hql = "SELECT a.paper_id as paper_id,a.full_score as full_score,a.subjectivity_score as subjectivity_score, "
					+ " a.objectivity_score as objectivity_score,a.paper_oid as paper_oid,a.paper_name as paper_name,"
					+ " a.cutting_root_path as cutting_root_path,a.paper_card_root_path,a.paper_type "
					+ " FROM paper_info a INNER JOIN test_used_paper b ON b.paper_id=a.paper_id "
					+ " INNER JOIN test c ON c.test_id=b.test_id AND c.test_oid=?";
			SQLQuery query = session.createSQLQuery(hql);
			query.setLong(0, this.exam.getOid());
			query.addEntity(ExamPaper.class);
			this.examPapers = query.list();
		}
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactroy = sessionFactory;
	}
	
	private ExamineeFinalScoreCalculator () {
		
	}
	
	public class ExamineeTotalScore{
		private List<ExamPaperScore> paperScores;
		
		private Examinee examinee;
		
		private Float fullScore;
		
		private Float score;
		
		public ExamineeTotalScore(Examinee examinee) {
			this.examinee = examinee;
			this.paperScores = new ArrayList<>();
			for(ExamPaper paper:examPapers) {
				fullScore =+ paper.getFullScore();
			}
		}
		
		public void addPaperScore(ExamPaperScore paperScore) {
			this.paperScores.add(paperScore);
		}
		
		public void calScore() {
			for(ExamPaperScore paperScore:this.paperScores) {
				if(!paperScore.validate()) {
					throw new UnsupportedOperationException(examinee.getName()+"-"+examinee.getUuid()+"试卷没评完，不能出成绩");
				}
				paperScore.calScore();
			}
		}
	}
	
	public class ExamPaperScore{
		private ExamPaper paper;
		
		private Examinee examinee;
		
		private Float score;
		
		private String schoolName;
		
		private String className;
		
		private List<ItemScore> itemsScore;
		
		private Map<Long,Boolean> requiredItemIds;
		
		public ExamPaperScore(ExamPaper paper,Examinee examinee) {
			this.paper = paper;
			this.examinee = examinee;
		}
		
		public void requiredItems(List<Long> requiredItemIds) {
			this.requiredItemIds = new HashMap<>();
			for(Long id:requiredItemIds) {
				this.requiredItemIds.put(id, Boolean.FALSE);
			}
		}
		
		public boolean validate() {
			Set<Long> keys = this.requiredItemIds.keySet();
			for(Long id:keys) {
				if(!this.requiredItemIds.get(id)) {
					return false;
				}
			}
			return true;
		}
		
		public void calScore() {
			for(ItemScore item:this.itemsScore) {
				this.score += item.score;
			}
		}

		public void addItemScore(String itemName,Float score) {
			if(this.itemsScore == null) {
				this.itemsScore = new ArrayList<>();
			}
			this.itemsScore.add(new ItemScore(itemName,score));
		}
		
		public void addItemScore(Long itemId,String itemName,Float score) {
			if(this.itemsScore == null) {
				this.itemsScore = new ArrayList<>();
			}
			this.itemsScore.add(new ItemScore(itemId,itemName,score));
		}

		public ExamPaper getPaper() {
			return this.paper;
		}

		public Float getScore() {
			return score;
		}

		public void setScore(Float score) {
			this.score = score;
		}

		public String getExamineeName() {
			return this.examinee.getName();
		}

		public String getExamineeUuid() {
			return this.examinee.getUuid();
		}
		
		public String getSchoolName() {
			return schoolName;
		}

		public void setSchoolName(String schoolName) {
			this.schoolName = schoolName;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public List<ItemScore> getItemsScore() {
			return itemsScore;
		}

		public void setItemsScore(List<ItemScore> itemsScore) {
			this.itemsScore = itemsScore;
		}		
	}
	
	public class ItemScore{
		private Long itemId;
		
		private String itemName;
		
		private Float score;
		
		public ItemScore(String itemName, Float score) {
			this.itemName = itemName;
			this.score = score;
		}

		public ItemScore(Long itemId, String itemName, Float score) {
			super();
			this.itemId = itemId;
			this.itemName = itemName;
			this.score = score;
		}

		public Long getItemId() {
			return itemId;
		}

		public void setItemId(Long itemId) {
			this.itemId = itemId;
		}

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public Float getScore() {
			return score;
		}

		public void setScore(Float score) {
			this.score = score;
		}
	}
	
	public class ScoreLevel{
		private String star = "★";
		
		private String empty = "☆";
		
		private Float[] levelValue = new Float[] {0.0f,0.60f,.075f,0.90f};
		
		private String[] levelString = new String[] {"0%","60%","75%","90%"};
	}
	
	public class PositionLevel{
		private String star = "★";
		
		private String empty = "☆";
		
		private Float[] levelValue = new Float[] {0.25f,0.50f,.075f,1.0f};
		
		private String[] levelString = new String[] {"25%","50%","75%","100%"};
	}
}


