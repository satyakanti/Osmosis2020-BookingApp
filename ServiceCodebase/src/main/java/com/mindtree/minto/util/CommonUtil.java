package com.mindtree.minto.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

	public static String toUpperCase(String string) {
		if (string != null) {
			return string.toUpperCase();
		}
		return null;
	}
	public static String toLowerCase(String string) {
		if (string != null) {
			return string.toLowerCase();
		}
		return null;
	}
	public static String formatDate(Date date) {
		String format = null;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
			format = sdf.format(date);
		}
		return format;
		
	}
	
	public static String getNewDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
		return sdf.format(new Date());
		
	}
}
