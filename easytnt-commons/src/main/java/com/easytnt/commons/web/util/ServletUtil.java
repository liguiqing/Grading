/**
 * 
 */
package com.easytnt.commons.web.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

/**
 * <Pre>
 * </Pre>
 *
 * @author liuyu
 * @data 2015年10月22日 下午3:36:21
 */
public class ServletUtil {

	public static Map<String, String> getRequestParamsMap(ServletRequest request) {
		HashMap<String, String> requestParamsMap = new HashMap<>();
		Map<String, String[]> tmpMap = request.getParameterMap();
		for (String key : tmpMap.keySet()) {
			String[] values = tmpMap.get(key);
			if (values == null) {
				values = new String[] { "" };
			}
			requestParamsMap.put(key, values[0]);
		}
		return requestParamsMap;
	}
}
