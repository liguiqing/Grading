/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.commons.util.Closer;
import com.easytnt.grading.domain.exam.Exam;
import com.easytnt.grading.domain.paper.ExamPaper;
import com.easytnt.grading.domain.paper.Section;
import com.easytnt.grading.domain.room.Examinee;
import com.easytnt.reporting.out.ReportingOutput;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年12月20日
 * @version 1.0
 **/
public class ExamineeFinalScoreCalculator {
	private static Logger logger = LoggerFactory.getLogger(ExamineeFinalScoreCalculator.class);
	
	private JdbcTemplate jdbcTemplate;
	
	private SessionFactory sessionFactroy;
	
	private Long testId;
	
	private Exam exam;
	
	private List<ExamPaper> examPapers;
	
	private List<ExamineeTotalScore> totalScores;
	
	private TreeSet<Float> allTotalScores;
	
	private HashMap<ExamPaper,TreeSet<Float>> allPaperScores;
	
	private HashMap<ExamPaper,List> paperObjectItems;

	private static ConcurrentHashMap<Long, ExamineeFinalScoreCalculator> pool  = new ConcurrentHashMap<>();
	
	public static ExamineeFinalScoreCalculator newCalculator(Long testId) {
		ExamineeFinalScoreCalculator calculator = pool.get(testId);
		if(calculator != null)
			return calculator;
		
		calculator = new ExamineeFinalScoreCalculator();
		calculator.paperObjectItems = new HashMap();
		calculator.testId = testId;
		calculator.totalScores = new ArrayList<>();
		calculator.allTotalScores = new TreeSet<>(new Comparator<Float>() {

			@Override
			public int compare(Float o1, Float o2) {
				return o2.compareTo(o1);
			}
		});
		calculator.allPaperScores = new HashMap<>();
		pool.put(testId,calculator);
		return calculator;
	}
	
	/**
	 * 计算分一个考生的总分
	 * @param examineeuuid
	 * @return
	 * @throws UnsupportedOperationException 该考生试卷没有改完成时抛出
	 */
	public void calScore(String examineeuuid) throws UnsupportedOperationException{
		Session session = getSession();
		Examinee examinee = getExaminee(session, examineeuuid);
		initExam(session);
		initExamPapers(session);
		initObjectItems(session);
		calTotalScore(examinee);
		//session.close();
	}
	
	private Session getSession() {
		try {
			return this.sessionFactroy.getCurrentSession();
		}catch(Exception e) {
			return this.sessionFactroy.openSession();
		}
	}
	
	public void scoreListOutput(String rootPath,ReportingOutput output) {
		File rootDir = initOutputPath(rootPath);
		Writer writer = null;
		try {
			String fileName = "index.html";
			File html = new File(rootDir.getAbsolutePath()+File.separator+fileName);
			html.createNewFile();
			writer = new OutputStreamWriter(new FileOutputStream(html),"UTF-8");
			HashMap root = new HashMap();
			root.put("exam", this.exam);
			root.put("totalScoreList", this.totalScores);
			logger.debug("Score List to {}",fileName);
			output.write(root,writer);
		} catch (UnsupportedEncodingException e) {
			logger.error(ThrowableParser.toString(e));
		} catch (FileNotFoundException e) {
			logger.error(ThrowableParser.toString(e));
		} catch (IOException e) {
			logger.error(ThrowableParser.toString(e));
		}finally {
			Closer.close(writer);
		}
	}
	
	private File initOutputPath(String rootPath) {
		if(rootPath == null) {
			rootPath = System.getProperty("java.io.tmpdir");
		}
		
		File rootDir = new File(rootPath+File.separator+testId);
		if(!rootDir.exists()) {
			rootDir.mkdir();
		}
		return rootDir;
	}
	
	public void reportingOutput(String rootPath,ReportingOutput output) {
		File rootDir = initOutputPath(rootPath);
		
		for(ExamineeTotalScore totalScore:this.totalScores) {
			logger.debug(totalScore.getExaminee().getName()+
					" 唯一编号：" + totalScore.getExaminee().getUuid() + 
					" 满分" + totalScore.getFullScore() +
					" 得分" + totalScore.getScore() +
					" 分数等级" + totalScore.degree +
					" 排名等级" +	totalScore.ranking);
			String fileName = totalScore.examinee.getUuid()+".html";
			logger.debug("Output to " + rootPath +File.separator+fileName);
			Writer writer = null;
			try {
				File html = new File(rootDir.getAbsolutePath()+File.separator+fileName);
				html.createNewFile();
				writer = new OutputStreamWriter(new FileOutputStream(html),"UTF-8");
				HashMap root = new HashMap();
				root.put("totalPaper", totalScore);
				output.write(root,writer);
			} catch (UnsupportedEncodingException e) {
				logger.error(ThrowableParser.toString(e));
			} catch (FileNotFoundException e) {
				logger.error(ThrowableParser.toString(e));
			} catch (IOException e) {
				logger.error(ThrowableParser.toString(e));
			}finally {
				Closer.close(writer);
			}
			
		}
	}
//	
//	public void output(String rootPath) {
//		if(rootPath == null) {
//			rootPath = System.getProperty("java.io.tmpdir");
//		}
//		
//		File rootDir = new File(rootPath+File.separator+testId);
//		if(!rootDir.exists()) {
//			rootDir.mkdir();
//		}
//		
//		for(ExamineeTotalScore totalScore:this.totalScores) {
//			logger.debug(totalScore.getExaminee().getName()+
//					" 唯一编号：" + totalScore.getExaminee().getUuid() + 
//					" 满分" + totalScore.getFullScore() +
//					" 得分" + totalScore.getScore() +
//					" 分数等级" + totalScore.degree +
//					" 排名等级" +	totalScore.ranking);
//			String fileName = totalScore.examinee.getUuid()+".html";
//			logger.debug("Output to " + rootPath +File.separator+fileName);
//			Writer writer = null;
//			try {
//				File html = new File(rootDir.getAbsolutePath()+File.separator+fileName);
//				html.createNewFile();
//				writer = new OutputStreamWriter(new FileOutputStream(html),"UTF-8");
//				HashMap root = new HashMap();
//				root.put("totalPaper", totalScore);
//				out.write(root,writer);
//			} catch (UnsupportedEncodingException e) {
//				logger.error(ThrowableParser.toString(e));
//			} catch (FileNotFoundException e) {
//				logger.error(ThrowableParser.toString(e));
//			} catch (IOException e) {
//				logger.error(ThrowableParser.toString(e));
//			}finally {
//				Closer.close(writer);
//			}
//			
//		}
//	}
	
	private void paperRanking(ExamineeTotalScore totalScore) {
		for(ExamPaperScore paperScore:totalScore.paperScores) {
			Set<Float> paperScores = this.allPaperScores.get(paperScore.paper);
			Float size = new Float(paperScores.size());
			int p = 0;
			for(Float score:paperScores) {
				p++;
				if(paperScore.score.compareTo(score)==0) {
					break;
				}
			}
			paperScore.rankingRate = new Float(p)/size;
			paperScore.ranking = Ranking.cal(new Float(size-p)/size);
		}
		
	}
	
	public void ranking() {
		Float size = new Float(this.allTotalScores.size());
		for(ExamineeTotalScore totalScore:this.totalScores) {
			int p = 0;
			for(Float score:this.allTotalScores) {
				p++;
				if(totalScore.score.compareTo(score)==0) {
					break;
				}
			}
			totalScore.rankingRate = new Float(p)/size;
			totalScore.ranking = Ranking.cal(new Float(size-p)/size);
			paperRanking(totalScore);
	    }
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
		
		
		final ExamPaperScore paperScore = new ExamPaperScore(paper,examinee);
		paperScore.requiredItems(allSectionIds);
		//客观题目得分
		Object[] newOrgs = new Object[] {paper.getPaperId(),examinee.getUuid()};
		jdbcTemplate.query("select omrScore,kgScore,omrStr from omrResult where paperid=? and studentId=?",newOrgs , new RowMapper<ItemScore>() {

			@Override
			public ItemScore mapRow(ResultSet rs, int rowNum) throws SQLException {
				ItemScore score = new ItemScore();
				score.object = true;
				score.myAnswer = rs.getString("omrStr");
				score.score = rs.getFloat("kgScore");
				score.itemName = rs.getString("omrScore");
				paperScore.addItemScore(score);
				return null;
			}
			
		});
		
		//主观题目得分
		newOrgs = new Object[args.size()];
		for(i=0;i<args.size();i++) {
			newOrgs[i] = args.get(i);
		}
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
	
	private void initObjectItems(Session session) {
		if(this.examPapers == null)
			this.initExamPapers(session);
		String sql = "SELECT * FROM selectitemdefine WHERE paperid=? ORDER BY NAME*1";
		for(ExamPaper paper:this.examPapers) {
			final ArrayList<Long> ids = new ArrayList<>();
			final ArrayList<String> names = new ArrayList<>();
			final ArrayList<Float> scores = new ArrayList<>();
			final ArrayList<String> options = new ArrayList<>();
			this.jdbcTemplate.query(sql, new Object[] {paper.getPaperId()}, new RowMapper(){

				@Override
				public Object mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					ids.add(rs.getLong("id"));
					scores.add(rs.getFloat("fullscore"));
					options.add(rs.getString("answer"));
					names.add(rs.getString("name"));
					return null;
				}
			});
			ArrayList objectItems = new ArrayList();
			objectItems.add(ids);
			objectItems.add(names);
			objectItems.add(options);
			objectItems.add(scores);
			this.paperObjectItems.put(paper, objectItems);
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
		public float rankingRate;

		private List<ExamPaperScore> paperScores;
		
		private Examinee examinee;
		
		private Float fullScore = 0.0f;
		
		private Float score = 0.0f;
		
		private int ranking = 1; //排位
		
		private int degree = 1;  //等级 用fullScore计算
		
		public ExamineeTotalScore(Examinee examinee) {
			this.examinee = examinee;
			this.paperScores = new ArrayList<>();
			for(ExamPaper paper:examPapers) {
				this.fullScore += paper.getFullScore();
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
				this.score += paperScore.calScore();
			}
			allTotalScores.add(score);
			Float level = this.getLevel();
			this.degree = Degree.cal(level);
			//this.ranking = Ranking.cal(level);
		}

		public boolean hasMoreSubjects() {
			if(this.paperScores == null || this.paperScores.size() == 0)
				return false;
			return true;
		}
		
		public Float getLevel() {
			Float level = this.score / this.fullScore;
			return level;
		}
		
		public Float getScoreRate() {
			return getLevel();
		}
		
		public Float getRankingRate() {
			return this.rankingRate;
		}
		
		public Examinee getExaminee() {
			return examinee;
		}
		
		public String getExamName() {
			return exam.getDesc().getName();
		}

		public Float getFullScore() {
			return fullScore;
		}

		public Float getScore() {
			return score;
		}
		
		public String getRankingName() {
			return Ranking.levelName(this.ranking);
		}
		
		public String getDegreeName() {
			return Degree.levelName(this.degree);
		}

		public List<ExamPaperScore> getPaperScores() {
			return paperScores;
		}

		public int getRanking() {
			return ranking;
		}

		public int getDegree() {
			return degree;
		}
		
	}
	
	public class ExamPaperScore{
		
		public float rankingRate;

		private ExamPaper paper;
		
		private Examinee examinee;
		
		private Float score = 0.0f;
		
		private String schoolName;
		
		private String className;
		
		private List<ItemScore> itemsScore;
		
		private Map<Long,Boolean> requiredItemIds;
		
		private int ranking = 1; //排位
		
		private int degree = 1;  //等级 用fullScore计算
		
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
		
		public Float calScore() {
			for(ItemScore item:this.itemsScore) {
				this.score += item.score;
			}
			Float level = getLevel();
			this.degree = Degree.cal(level);
			
			if(allPaperScores.get(this.paper) == null) {
				allPaperScores.put(this.paper, new TreeSet<Float>(new Comparator<Float>() {

					@Override
					public int compare(Float o1, Float o2) {
						return o2.compareTo(o1);
					}
				}));
			}
			allPaperScores.get(this.paper).add(this.score);
			return this.score;
		}
		
		public void addItemScore(ItemScore score) {
			if(this.itemsScore == null) {
				this.itemsScore = new ArrayList<>();
			}
			this.itemsScore.add(score);
		}

		public void addItemScore(String itemName,Float score) {
			this.addItemScore(new ItemScore(itemName,score));
		}
		
		public void addItemScore(Long itemId,String itemName,Float score) {
			this.addItemScore(new ItemScore(itemId,itemName,score));
			if(this.requiredItemIds.get(itemId) != null) {
				this.requiredItemIds.put(itemId, Boolean.TRUE);
			}
		}

		public Float getLevel() {
			Float level = this.score / this.paper.getFullScore();
			return level;
		}
		
		public Float getScoreRate() {
			return getLevel();
		}
		
		public Float getRankingRate() {
			return this.rankingRate;
		}
		
		public String getPaperName() {
			return this.paper.getName();
		}
		
		public Float getFullScore() {
			return this.paper.getFullScore();
		}
		
		public boolean equals(Object o) {
			if(!(o instanceof ExamPaperScore)) {
				return false;
			}
			
			ExamPaperScore other = (ExamPaperScore) o;
			return this.paper.getPaperId() == other.paper.getPaperId();
		}
		
		public ExamPaper getPaper() {
			return this.paper;
		}

		public Float getScore() {
			return score;
		}

		public String getExamineeName() {
			return this.examinee.getName();
		}

		public Long getExamineeUuid() {
			return this.examinee.getUuid();
		}
		
		public String getSchoolName() {
			return schoolName;
		}

		public String getClassName() {
			return className;
		}

		public List<ItemScore> getItemsScore() {
			ArrayList<ItemScore> items = new ArrayList<>();
			if(this.itemsScore != null) {
				for(ItemScore itemScore:this.itemsScore) {
					if(itemScore.isObject()) {
						items.addAll(itemScore.getObjectSubItemScores(this.paper));
					}else {
						items.add(itemScore);
					}				
				}
			}
			return items;
		}		
		
		public String getRankingName() {
			return Ranking.levelName(this.ranking);
		}
		
		public String getDegreeName() {
			return Degree.levelName(this.degree);
		}

		public int getRanking() {
			return ranking;
		}

		public int getDegree() {
			return degree;
		}

	}
	
	public class ItemScore{
		private Long itemId;
		
		private String itemName;
		
		private String answer;
		
		private String myAnswer;
		
		private Float score = 0.0f;
		
		private Float fullScore = 0.0f;
		
		private boolean object = false;
		
		public ItemScore() {}
		
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
		
		public String getAnswer() {
			return this.answer;
		}
		
		public String getMyAnswer() {
			return this.myAnswer;
		}
		
		public Float getFullScore() {
			return this.fullScore;
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
		
		public boolean isObject() {
			return this.object;
		}
		
		public List<ItemScore> getObjectSubItemScores(ExamPaper paper) {
			if(this.object) {
				List<List> omr = paperObjectItems.get(paper);
				List<Long> ids = omr.get(0);
				List<String> names = omr.get(1);
				List<String> options = omr.get(2);
				List<Float> scores = omr.get(3);
				String[] myans = this.myAnswer.split(",");
				String[] myscores = this.itemName.split(",");
				ArrayList<ItemScore> items = new ArrayList<>();
				for(int i = 0;i<ids.size();i++) {
					ItemScore score = new ItemScore();
					score.itemId = ids.get(i);
					score.score = new Float(myscores[i]);
					score.fullScore = scores.get(i);
					score.itemName = names.get(i);
					score.answer = options.get(i);
					score.myAnswer = myans[i];
					score.object = true;
					items.add(score);
				}
				return items;
			}
			return null;
		}

	}
	
	/**
	 * 分数等级
	 * 得分在满分中的比值
	 * 计算值越大排名越高
	 * @author liguiqing
	 *
	 */
	public static class Degree{
		
		private final static Float[] levelValues = new Float[] {0.0f,0.60f,0.75f,0.90f};
		
		private final static int[] levels = new int[] {0,1,2,3};
		
		private final static String[] levenlsName = new String[] {"不及格","及格","良好","优秀"};
		
		public static int  cal(Float level) {
			for(int i = levelValues.length-1;i>=0;i--) {
				if(level >=levelValues[i]) {
					return levels[i];
				}
			}
			return levels[0];
		}
		
		public static String levelName(int level) {
			for(int i=0;i<levels.length;i++) {
				if(level == levels[i])
					return levenlsName[i];
			}
			return levenlsName[0];
		}
	}
	
	/**
	 * 排名等级
	 * 得分在群体中的百分位
	 * 计算值越大排名越高
	 * @author liguiqing
	 *
	 */
	public static class Ranking{

		private static Float[] levelValues = new Float[] {0.0f,0.25f,0.50f,0.75f};
		
		private static int [] levels = new int[] {0,1,2,3};
		
		private final static String[] levenlsName = new String[] {"下等","中下","中上","优等"};
		
		public static int  cal(Float level) {
			for(int i = levelValues.length-1;i>=0;i--) {
				if(level >=levelValues[i]) {
					return levels[i];
				}
			}
			return levels[0];
		}
		
		public static String levelName(int level) {
			for(int i=0;i<levels.length;i++) {
				if(level == levels[i])
					return levenlsName[i];
			}
			return levenlsName[0];
		}
	}
}


