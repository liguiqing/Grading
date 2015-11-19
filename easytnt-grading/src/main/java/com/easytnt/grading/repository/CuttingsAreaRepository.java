/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository;

import java.util.List;

import com.easytnt.commons.entity.repository.Repository;
import com.easytnt.grading.domain.cuttings.CuttingsArea;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月2日
 * @version 1.0
 **/
public interface CuttingsAreaRepository extends Repository<CuttingsArea, Long> {

	public List<CuttingsArea> listCuttingsAreaOfInPaper(Long paperId);

	public void deletePositionOfItemInAreas();
}
