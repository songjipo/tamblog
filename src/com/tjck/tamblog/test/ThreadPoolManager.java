package com.tjck.tamblog.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
	
	private String name;
	private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1000);
	private ThreadPoolExecutor executor;
	ThreadFactory factory = new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			return new Thread(r, name + "-" + Thread.currentThread());
		}
	};
	
	public ThreadPoolManager(String name){
		this.name = name;
		executor = new ThreadPoolExecutor(5, 100, 100, TimeUnit.SECONDS, queue, factory);
	}
	
	public void submit(Runnable runnable){
		executor.submit(runnable);
	}
	
	public void shutdown(){
		while(true){
			if (queue.size() ==0 && executor.getActiveCount() == 0) {
				System.err.println("线程执行完成，关闭线程池");
				executor.shutdown();
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		ThreadPoolManager poolManager = new ThreadPoolManager("测试线程池");
		for (int i = 0; i < 10; i++) {
			Runnable task = new SayHello("第" + i + "个人");
			poolManager.submit(task);
		}
		poolManager.shutdown();
		System.err.println("主线程结束");
	}
	
}

class SayHello implements Runnable{
	private String name;
	public SayHello(String name) {
		this.name = name;
	}
	@Override
	public void run() {
		System.err.println("hello-" + name);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
