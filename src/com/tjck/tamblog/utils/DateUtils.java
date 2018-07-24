package com.tjck.tamblog.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String stime = sdf.format(date);
		return stime;
	}
	
	public static String getCurrentTimeyyyyMMdd() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		String stime = sdf1.format(date);
		return stime;
	}
	
}
