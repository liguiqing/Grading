/**
 * 
 */
package com.easytnt.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

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
	private static final AtomicInteger poolNo = new AtomicInteger();

	private EasytntTheadFactory() {
	}

	@Override
	public Thread newThread(Runnable runner) {
		SecurityManager manager = System.getSecurityManager();
		ThreadGroup group = manager != null ? manager.getThreadGroup() : Thread.currentThread().getThreadGroup();
		Thread thread = new Thread(group, runner, "EasyTNT-Thread-" + poolNo.getAndIncrement() + "-");
		if (thread.isDaemon()) {
			thread.setDaemon(Boolean.FALSE);
		}
		if (thread.getPriority() != Thread.NORM_PRIORITY) {
			thread.setPriority(Thread.NORM_PRIORITY);
		}
		return thread;
	}

}
