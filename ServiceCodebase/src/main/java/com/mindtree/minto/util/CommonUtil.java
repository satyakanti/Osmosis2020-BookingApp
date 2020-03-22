package com.mindtree.minto.util;

import java.text.ParseException;
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
	
	public static Date getDate(String dateString, String dateFormat) {
		Date date = null;
		if (dateString != null && dateFormat != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	
	public static String getNewDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
		return sdf.format(new Date());
		
	}
}
