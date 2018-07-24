package com.tjck.tamblog.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestNIO {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		List<String>synchlist = Collections.synchronizedList(list);
		//CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<String>();
		for (String syn : synchlist) {
			System.err.println(syn);
		}
	}
	
}
