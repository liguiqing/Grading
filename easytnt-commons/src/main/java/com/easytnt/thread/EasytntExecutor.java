/**
 * 
 */
package com.easytnt.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * 创建java线程池
 * </pre>
 * 
 * @author liuyu
 *
 */
public class EasytntExecutor {

	private static EasytntExecutor easytntExecutor = new EasytntExecutor();
	private ExecutorService executorService;

	private EasytntExecutor() {
		createExecutorService();
	}

	private void createExecutorService() {
		executorService = Executors.newCachedThreadPool(EasytntTheadFactory.FACTORY);
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public static EasytntExecutor instance() {
		return easytntExecutor;
	}
}
