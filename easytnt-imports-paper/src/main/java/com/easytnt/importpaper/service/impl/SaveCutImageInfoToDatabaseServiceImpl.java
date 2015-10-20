/**
 * 
 */
package com.easytnt.importpaper.service.impl;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easytnt.commons.exception.ThrowableParser;
import com.easytnt.importpaper.bean.CutImageInfo;
import com.easytnt.importpaper.service.SaveCutImageInfoToDatabaseService;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class SaveCutImageInfoToDatabaseServiceImpl implements SaveCutImageInfoToDatabaseService {

	private final Logger log = LoggerFactory.getLogger(SaveCutImageInfoToDatabaseServiceImpl.class);

	private DataSource ds;
	private ReentrantLock lock = new ReentrantLock();
	private ArrayList<String> sqls = new ArrayList<>();

	public void setDatasource(DataSource ds) {
		this.ds = ds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.easytnt.importpaper.service.SaveCutImageInfoToDatabaseService#save(
	 * com.easytnt.importpaper.bean.CutImageInfo)
	 */
	@Override
	public void save(CutImageInfo cutImageInfo) {
		String sql = createSQL(cutImageInfo);
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
			save(tempSqls);
		}
	}

	@Override
	public void clear() {
		if (!sqls.isEmpty()) {
			save(sqls);
		}
	}

	private void save(List<String> sqls) {
		Connection con = null;
		try {
			con = ds.getConnection();
			Statement statement = con.createStatement();
			for (String sql : sqls) {
				statement.addBatch(sql);
			}
			statement.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ThrowableParser.toString(e));
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(ThrowableParser.toString(e));
			}

		}
	}

	private String createSQL(CutImageInfo cutImageInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO paperimport ( testid, kemuoid, paperid,")
				.append("studentoid, itemid, roomid, virtualroomid,roomtype, pingci,")
				.append(" getmark,diquId,imagepath) VALUES(");
		sql.append(cutImageInfo.getTestId()).append(",");
		sql.append(cutImageInfo.getKemuId()).append(",");
		sql.append(cutImageInfo.getPaperId()).append(",");
		sql.append(cutImageInfo.getStudentId()).append(",");
		sql.append(cutImageInfo.getItemId()).append(",");
		sql.append(cutImageInfo.getRoomId()).append(",");
		sql.append(cutImageInfo.getVirtualroomId()).append(",");
		sql.append(cutImageInfo.getRoomType()).append(",");
		sql.append("0,0,");
		sql.append(cutImageInfo.getDiquId()).append(",");
		sql.append("'").append(cutImageInfo.getImagePath()).append("'");
		sql.append(");");
		return sql.toString();
	}

}
