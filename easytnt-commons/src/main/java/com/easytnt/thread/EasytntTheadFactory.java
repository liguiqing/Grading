/**
 * 
 */
package com.easytnt.thread;

import java.util.concurrent.ThreadFactory;

/**
 * <pre>
 * 生成线程的线程工厂
 * </pre>
 * 
 * @author liuyu
 *
 */
public class EasytntTheadFactory implements ThreadFactory {

	public static EasytntTheadFactory FACTORY = new EasytntTheadFactory();

	private EasytntTheadFactory() {
	}

	private int counter = 1;
	private final String prefix = "easytnt-";

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, prefix + "-" + counter++);
	}

}
