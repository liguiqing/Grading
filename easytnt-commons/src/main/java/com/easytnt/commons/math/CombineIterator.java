/**
 * <p><b>© </b></p>
 * 
 **/
package com.easytnt.commons.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 李贵庆 2015年12月6日
 * @version 1.0
 **/
public class CombineIterator implements Iterator {

	// 源数据
	private Float[] source;
	// 结果数组大小
	private int resultSize;
	// 结果数组个数
	private int size;
	// 当前元素索引
	private int[] index;
	// 当前序列索引
	private int offset = 0;

	public CombineIterator(Float[] source, int resultSize) {
		if (source == null)
			throw new NullPointerException();
		int n = source.length;
		if (n < resultSize || resultSize <= 0)
			throw new IllegalArgumentException("size : " + n + ", m : "
					+ resultSize);
		this.source = source;
		this.size = clacSize(n, resultSize);
		this.resultSize = resultSize;
		this.index = new int[resultSize];
		for (int i = 0; i < resultSize; i++) {
			this.index[i] = i;
		}
		this.index[resultSize - 1] -= 1;
	}

	@Override
	public boolean hasNext() {
		return offset < size;
	}

	private int clacSize(int n, int m) {
		return Factorial.factorial(n - m + 1, n).divide(Factorial.factorial(m))
				.intValue();
	}

	@Override
	public Float[] next() {
		int idx = resultSize - 1;
		int n = source.length;
		if (index[idx] < n - 1) {
			index[idx] += 1;
		} else {
			idx -= 1;
			while (idx > 0 && index[idx] == index[idx + 1] - 1) {
				idx -= 1;
			}
			index[idx] += 1;
			for (int i = idx + 1; i <= resultSize - 1; i++) {
				index[i] = index[idx] + (i - idx);
			}
		}

		Float[] result = new Float[resultSize];
		for (int i = 0; i <= resultSize - 1; i++) {
			result[i] = source[index[i]];
		}
		offset++;
		return result;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	private static class Factorial {

		/**
		 * 计算1到n的阶乘,0! = 1
		 * 
		 * @param n
		 * @return 1 * 2 *3 * ... * (n - 1) * n
		 */
		public static BigInteger factorial(int n) {
			if (n == 0)
				return new BigInteger("1");
			return factorial(1, n);
		}

		/**
		 * 计算start到end的阶乘,不支持0参数
		 * 
		 * @param start
		 *            起始数(包含)
		 * @param end
		 *            终止数(包含)
		 * @return start * (start + 1) * ... *(end - 1) * end
		 */
		public static BigInteger factorial(int start, int end) {
			if (start <= 0 || end < start)
				throw new IllegalArgumentException("start : " + start
						+ ",end : " + end);
			BigInteger result = new BigInteger("1");
			for (int i = start; i <= end; i++) {
				result = result.multiply(new BigInteger(i + ""));
			}
			return result;
		}
	}

	public static void main(String[] args) {
		Float[] source = new Float[] { 3.0f, 4.0f, 5.0f };

		int resultSize = 2;
		CombineIterator itr = new CombineIterator(source, resultSize);

		while (itr.hasNext()) {
			Float[] a = itr.next();
			System.out.println(a[0]+"--"+a[1]);
		}
	}
}
