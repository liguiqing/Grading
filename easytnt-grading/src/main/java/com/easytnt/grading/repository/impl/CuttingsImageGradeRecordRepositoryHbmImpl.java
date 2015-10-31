/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.easytnt.commons.entity.repository.HibernateRepository;
import com.easytnt.grading.domain.grade.CuttingsImageGradeRecord;
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
		}

	}

	@Override
	public void saveForScoring(CuttingsImageGradeRecord record) {
		String insertScoreinfolog = "INSERT INTO scoreinfolog (paperid, virtualroomid, studentoid, itemid, score, postdatetime, "
				+ " teacheroid, scorestr, pingci, spenttime, teacherip, memo, markstr, delmark, teachermark, kemuoid, papertype, id ) "
				+ " SELECT paperid,roomid,studentoid,itemid,?,?,?,?,?,?,'teacherip','memo','Y',0,'Y',?,'papertype',? FROM paperimport where imagepath = ? ";
		Query query =  getCurrentSession().createSQLQuery(insertScoreinfolog);
		int index = 0;
		query.setFloat(index++,record.calScore());
		query.setTimestamp(index++, record.getFinishTime());
		query.setLong(index++,record.getReferees().getId());
		query.setString(index++, record.getScorestr());
		query.setInteger(index++, record.getPinci());
		query.setInteger(index++, record.spendTime());
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
		
		String updatePaperimport = "update paperimport set pingci=? and getmark='Y' where  itemid=? and paperid=? and imagepath = ?";
		query =  getCurrentSession().createSQLQuery(updatePaperimport);
		index = 0;
		query.setInteger(index++, record.getPinci());
		query.setLong(index++, record.getRecordFor().definedOf().getId());
		query.setLong(index++, record.getRecordFor().definedOf().getPaper().getPaperId());
		query.setString(index++, record.getRecordFor().getImgPath());
		query.executeUpdate();
	}

}
