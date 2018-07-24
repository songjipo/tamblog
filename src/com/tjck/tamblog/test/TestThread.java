package com.tjck.tamblog.test;

public class TestThread {

	public static void main(String[] args) {
		/*threadExtend extend1 = new threadExtend();
		threadExtend extend2 = new threadExtend();
		extend1.setName("extend1");
		extend2.setName("extend2");
		extend1.start();
		extend2.start();*/
		threadRunable threadRunable = new threadRunable();
		Thread thread1 = new Thread(threadRunable, "runable1");
		Thread thread2 = new Thread(threadRunable, "runable2");
		thread1.start();
		thread2.start();
	}
	
}

class threadRunable implements Runnable{

	public void run() {
		System.err.println("runable-" + Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

class threadExtend extends Thread{
	public void run() {
		System.err.println("entends-" + Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
