/**
 * 
 */
package com.easytnt.cutimage.utils;

/**
 * <pre>
 * </pre>
 * 
 * @author liuyu
 *
 */
public class NumberFormat {
	public static String clearZero(Double value) {
		return clearZero(value.toString());
	}

	public static String clearZero(Float value) {
		return clearZero(value.toString());
	}

	public static String clearZero(String value) {
		if (value.indexOf(".") == -1) {
			return value;
		}

		int idx = 0;
		for (int i = value.length() - 1; i >= 0; i--) {
			char c = value.charAt(i);
			if ('.' == c) {
				idx = i;
				break;
			} else if ('0' != c) {
				idx = i;
				break;
			}
		}
		return value.substring(0, idx);
	}
}
