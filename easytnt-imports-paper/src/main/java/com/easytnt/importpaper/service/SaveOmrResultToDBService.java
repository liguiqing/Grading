/**
 * 
 */
package com.easytnt.importpaper.service;

import java.util.List;

import com.easytnt.grading.domain.cuttings.OrmResult;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public interface SaveOmrResultToDBService {
	public void save(OrmResult ormResult);

	public void save(List<OrmResult> ormResults);

	public void clear();
}
