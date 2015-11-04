/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.CuttingsImage;
import com.easytnt.grading.domain.grade.CuttingsImageGradeRecord;
import com.easytnt.grading.domain.grade.GradeTask;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.repository.CuttingsImageGradeRecordRepository;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月26日
 * @version 1.0
 **/
@Repository
public class CuttingsImageGradeRecordRepositoryHbmImpl extends
		HibernateRepository<CuttingsImageGradeRecord, Long> implements
		CuttingsImageGradeRecordRepository {

	@Override
	public void saveForFetching(CuttingsImageGradeRecord record) {
		String querySql = "SELECT 1 FROM getpaper WHERE teacheroid= ? AND itemid=? and paperid=? and imagepath = ?";
		Query query =  getCurrentSession().createSQLQuery(querySql);
		int index = 0;
		query.setLong(index++, record.getReferees().getId());
		query.setLong(index++, record.getRecordFor().definedOf().getId());
		query.setLong(index++, record.getRecordFor().definedOf().getPaper().getPaperId());
		query.setString(index++, record.getRecordFor().getImgPath());
		if(query.list().size() <= 0) {
			String insetSql = "INSERT INTO getpaper (paperid,	kemuoid, virtualroomid,studentoid, itemid, imagepath,pingci,teacheroid,scored,getpaperdatetime) "
					+ "select paperid,kemuoid,roomid,studentoid,itemid,imagepath,? ,?,0,? from paperimport where imagepath = ?";
			query =  getCurrentSession().createSQLQuery(insetSql);
			index = 0;
			query.setInteger(index++, record.getPinci());
			query.setLong(index++, record.getReferees().getId());
			query.setTimestamp(index++, record.getStartTime());
			query.setString(index, record.getRecordFor().getImgPath());
			query.executeUpdate();
			
			String updatePaperimport = "update paperimport set pingci=?, getmark=0 where  itemid=? and paperid=? and imagepath = ?";
			query =  getCurrentSession().createSQLQuery(updatePaperimport);
			index = 0;
			query.setInteger(index++, record.getPinci());
			query.setLong(index++, record.getRecordFor().definedOf().getId());
			query.setLong(index++, record.getRecordFor().definedOf().getPaper().getPaperId());
			query.setString(index++, record.getRecordFor().getImgPath());
			query.executeUpdate();
		}

	}

	@Override
	public void saveForScoring(CuttingsImageGradeRecord record) {
		saveGradeRecord(record,"Y");
	}
	

	@Override
	public void saveForBlank(CuttingsImageGradeRecord record) {
		saveGradeRecord(record,"B");
	}

	@Override
	public void saveForError(CuttingsImageGradeRecord record) {
		saveGradeRecord(record,"E");
	}
	
	private void saveGradeRecord(CuttingsImageGradeRecord record,String markStr) {
		String insertScoreinfolog = "INSERT INTO scoreinfolog (paperid, virtualroomid, studentoid, itemid, score, postdatetime, "
				+ " teacheroid, scorestr, pingci, spenttime, teacherip, memo, markstr, delmark, teachermark, kemuoid, papertype, id ) "
				+ " SELECT paperid,roomid,studentoid,itemid,?,?,?,?,?,?,'teacherip','memo',?,0,'Y',?,'papertype',? FROM paperimport where imagepath = ? ";
		Query query =  getCurrentSession().createSQLQuery(insertScoreinfolog);
		int index = 0;
		query.setFloat(index++,record.calScore());
		query.setTimestamp(index++, record.getFinishTime());
		query.setLong(index++,record.getReferees().getId());
		query.setString(index++, record.getScorestr());
		query.setInteger(index++, record.getPinci());
		query.setInteger(index++, record.spendTime());
		query.setString(index++, markStr);
		query.setLong(index++, record.getRecordFor().definedOf().subjectOf().getId());
		query.setLong(index++, record.genId());
		query.setString(index, record.getRecordFor().getImgPath());
		query.executeUpdate();
		
		String updateGetpaper = "update getpaper set pingci=?,scored=1 where teacheroid= ? AND itemid=? and paperid=? and imagepath = ?";
		query =  getCurrentSession().createSQLQuery(updateGetpaper);
		index = 0;
		query.setInteger(index++, record.getPinci());
		query.setLong(index++, record.getReferees().getId());
		query.setLong(index++, record.getRecordFor().definedOf().getId());
		query.setLong(index++, record.getRecordFor().definedOf().getPaper().getPaperId());
		query.setString(index++, record.getRecordFor().getImgPath());
		query.executeUpdate();
		
		String updatePaperimport = "update paperimport set pingci=?, getmark=1 where  itemid=? and paperid=? and imagepath = ?";
		query =  getCurrentSession().createSQLQuery(updatePaperimport);
		index = 0;
		query.setInteger(index++, record.getPinci());
		query.setLong(index++, record.getRecordFor().definedOf().getId());
		query.setLong(index++, record.getRecordFor().definedOf().getPaper().getPaperId());
		query.setString(index++, record.getRecordFor().getImgPath());
		query.executeUpdate();
	}

	@Override
	public CuttingsImageGradeRecord findUndoRecordOf(GradeTask task) {
		Referees referees = task.getAssignedTo();
		CuttingsArea area = task.getGenBy();
		String sql = "SELECT itemid,imagepath,getpaperdatetime,pingci FROM getpaper WHERE teacheroid=? AND itemid = ? AND scored=0;";
		Query query =  getCurrentSession().createSQLQuery(sql);
		int index = 0;
		query.setLong(index++, referees.getId());
		query.setLong(index++, area.getId());
		List result = query.list();
		if(result != null && result.size() > 0) {
			Object[] row = (Object[])result.get(0);
			CuttingsImage image = new CuttingsImage(task.getGenBy());
			image.setImageId(Long.parseLong(row[0]+""));
			image.setImgPath(row[1]+"");
			image.setUuid(row[1]+"");
			CuttingsImageGradeRecord gradeRecord = image.createRecord(referees);
			gradeRecord.setStartTime(((Timestamp)row[2]));
			gradeRecord.setPinci((int)row[3]);
			return gradeRecord;
		}
		return null;
	}

}
