/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.grading.repository;

import java.util.List;

import com.easytnt.commons.entity.repository.Repository;
import com.easytnt.grading.domain.cuttings.CuttingDefine;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年11月2日
 * @version 1.0
 **/
public interface CuttingDefineRepository extends Repository<CuttingDefine, Long> {
	public List<CuttingDefine> listCuttingDefinesWith(Long paperId);

	public void deleteCuttingDefinesWith(Long paperId);

}
