package com.tjck.tamblog.test;

public class TestSingleTon {
	
	private volatile static TestSingleTon instance;
	public static TestSingleTon getInstance(){
		if (instance == null) {
			synchronized (TestSingleTon.class) {
				instance = new TestSingleTon();
			}
		}
		return instance;
	}
	private TestSingleTon(){
		
	}
	
	public static void main(String[] args) {
		TestSingleTon testSingleTon = TestSingleTon.getInstance();
		TestSingleTon testSingleTon1 = TestSingleTon.getInstance();
		System.err.println(testSingleTon == testSingleTon1);
	}
	
}
