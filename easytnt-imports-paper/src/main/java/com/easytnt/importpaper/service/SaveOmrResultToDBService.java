/**
 * 
 */
package com.easytnt.importpaper.service;

import java.util.List;

import com.easytnt.grading.domain.cuttings.OmrResult;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public interface SaveOmrResultToDBService {
	public void save(OmrResult ormResult);

	public void save(List<OmrResult> ormResults);

	public void clear();
}
