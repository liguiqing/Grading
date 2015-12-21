/**
 * 
 */
package com.easytnt.importpaper.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.grading.domain.cuttings.OrmResult;
import com.easytnt.importpaper.service.SaveOmrResultToDBService;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class SaveOmrResultToDBServiceImpl implements SaveOmrResultToDBService {

	private final Logger log = LoggerFactory.getLogger(SaveOmrResultToDBServiceImpl.class);

	private DataSource ds;
	private ReentrantLock lock = new ReentrantLock();
	private ArrayList<String> sqls = new ArrayList<>();

	public void setDatasource(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(List<OrmResult> ormResults) {
		ArrayList<String> tmepSqls = new ArrayList<>();
		for (OrmResult ormResult : ormResults) {
			tmepSqls.add(createSQL(ormResult));
		}

		ArrayList<String> saveToDBSqls = new ArrayList<>();
		lock.lock();
		try {
			sqls.addAll(tmepSqls);
			if (sqls.size() % 10000 == 0) {
				saveToDBSqls.addAll(sqls);
				sqls.clear();
			}
		} finally {
			lock.unlock();
		}

		if (!saveToDBSqls.isEmpty()) {
			saveToDb(saveToDBSqls);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.easytnt.importpaper.service.SaveCutImageInfoToDatabaseService#save(
	 * com.easytnt.importpaper.bean.CutImageInfo)
	 */
	@Override
	public void save(OrmResult ormResult) {
		String sql = createSQL(ormResult);
		ArrayList<String> tempSqls = new ArrayList<>();
		lock.lock();
		try {
			sqls.add(sql);
			if (sqls.size() % 10000 == 0) {
				tempSqls.addAll(sqls);
				sqls.clear();
			}
		} finally {
			lock.unlock();
		}

		if (!tempSqls.isEmpty()) {
			saveToDb(tempSqls);
		}
	}

	@Override
	public void clear() {
		if (!sqls.isEmpty()) {
			saveToDb(sqls);
		}
	}

	private void saveToDb(List<String> sqls) {
		if (ds == null) {
			for (String sql : sqls) {
				System.out.println(sql);
			}
		} else {
			Connection con = null;
			PreparedStatement ps = null;
			try {
				con = ds.getConnection();
				ps = con.prepareStatement("");
				for (String sql : sqls) {
					ps.addBatch(sql);
				}
				ps.executeBatch();
			} catch (Exception e) {
				e.printStackTrace();
				log.error(ThrowableParser.toString(e));
			} finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error(ThrowableParser.toString(e));
				}

			}
		}
	}

	private String createSQL(OrmResult ormResult) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO omrResult(paperId,studentId,kgScore,omrStr,omrScore) VALUES(");
		sql.append(ormResult.getPaperId()).append(",").append(ormResult.getStudentId()).append(",")
				.append(ormResult.getKgScore()).append(",'").append(ormResult.getOmrStr()).append("','")
				.append(ormResult).append("'");
		sql.append(")");
		return sql.toString();
	}

}
