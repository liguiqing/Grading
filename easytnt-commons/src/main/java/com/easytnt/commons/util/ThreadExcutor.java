/**
 * 
 * 
 **/

package com.easytnt.commons.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/** 
 * <pre>
 * 多线程执行器
 * </pre>
 *  
 * @author 李贵庆2015年10月10日
 * @version 1.0
 **/
public class ThreadExcutor {

	private ExecutorService executorService;
	
	private ThreadExcutor() {
		this.executorService = Executors.newCachedThreadPool(new MyThreadFactory());
	}
	
	private final static class Holder{
		private final static ThreadExcutor instance = new ThreadExcutor();
	}
	
	public static ThreadExcutor getInstance() {
		return ThreadExcutor.Holder.instance;
	}
	
	public void submit(Runnable task) {
		this.executorService.submit(task);
	}
	
	
	private static class MyThreadFactory implements ThreadFactory{
		private static final AtomicInteger poolNo = new AtomicInteger();
		@Override
		public Thread newThread(Runnable runner) {
			SecurityManager manager = System.getSecurityManager();
			ThreadGroup group = manager != null?manager.getThreadGroup():Thread.currentThread().getThreadGroup();
			Thread thread = new Thread(group,runner,"EasyTNT-Thread-" + poolNo.getAndIncrement() + "-");
			if(thread.isDaemon()) {
				thread.setDaemon(Boolean.FALSE);
			}
			if(thread.getPriority() != Thread.NORM_PRIORITY) {
				thread.setPriority(Thread.NORM_PRIORITY);
			}
			return thread;
		}
		
	}
}

