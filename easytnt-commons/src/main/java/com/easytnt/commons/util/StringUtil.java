/**
 * 
 */
package com.easytnt.commons.util;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class StringUtil {

	public static long toLong(String str) {
		return Long.parseLong(str);
	}

	public static int toInt(String str) {
		return Integer.parseInt(str);
	}

	public static boolean isEmpty(String str) {
		return str == null || "".equals(str);
	}
}
