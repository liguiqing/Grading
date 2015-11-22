/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.mock.respository;

import java.util.HashMap;
import java.util.List;

import com.easytnt.commons.entity.cqrs.Query;
import com.easytnt.grading.domain.grade.Referees;
import com.easytnt.grading.repository.RefereesRepository;

/** 
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年10月20日
 * @version 1.0
 **/
public class RefereesRepositoryMocker implements RefereesRepository {

	private static HashMap<Long,Referees> refereeses = new HashMap<>();
	
	public void initMethod() {
		refereesConcreate();
	}
	
	private void refereesConcreate() {
		for(Long id=1l;id<=10;id++) {
			Referees r = new Referees("Test"+id);
			r.setId(id);
			RefereesRepositoryMocker.refereeses.put(id, r);
		}
			
	}
	
	@Override
	public void save(Referees t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveOrUpdate(Referees t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Referees t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Referees load(Long pk) {
		return refereeses.get(pk) ;
	}

	@Override
	public Referees get(Long pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Referees t) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Referees> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void query(Query<Referees> query) {
		// TODO Auto-generated method stub
		
	}

}


