/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.mgt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easytnt.grading.dispatcher.Dispatcher;
import com.easytnt.grading.domain.cuttings.CuttingsArea;
import com.easytnt.grading.domain.cuttings.PieceCuttings;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.service.RefereesService;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月17日
 * @version 1.0
 **/
@Service
public class GradingManagerImpl implements GradingManager {

	@Autowired(required=false)
	private RefereesService refereesService;
	
	@Autowired(required=false)
	private PieceCuttingsManager pieceCuttingsManager;
	
	@Override
	public PieceCuttings getPieceCuttingsFor(Referees referees) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PieceCuttings getPieceCuttingsFor(CuttingsArea area) {
		Dispatcher dispatcher =  pieceCuttingsManager.getDispatcherFor(area);
		Referees referees = refereesService.getCurrentReferees();
		PieceCuttings cuttings = dispatcher.get(referees);
		return cuttings;
	}

}


