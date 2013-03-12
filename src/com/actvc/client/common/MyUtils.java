package com.actvc.client.common;

import java.util.Date;

public class MyUtils {

	@SuppressWarnings("deprecation")
	public static String getDateStr(Date d) {
		if (d != null) {
			String newDate = d.getDate() + "/"
					+ ((d.getMonth() < 9) ? "0" : "") + (d.getMonth() + 1)
					+ "/" + (d.getYear() + 1900);
			return newDate;
		}
		return "";
	}

	@SuppressWarnings("deprecation")
	public static String getDateStrOrdered(Date d) {
		String newDate = (d.getYear() + 1900) + "_"
				+ ((d.getMonth() < 9) ? "0" : "") + (d.getMonth() + 1) + "_"
				+ ((d.getDate() < 10) ? "0" : "") + d.getDate();
		return newDate;
	}

	@SuppressWarnings("deprecation")
	public static boolean futureDate(String date) {
		String[] dateParts = date.split("/");
		Date d = new Date(Integer.parseInt(dateParts[2]) - 1900, Integer
				.parseInt(dateParts[1]) - 1, Integer.parseInt(dateParts[0]));
		Date now = new Date();
		return (d.after(now));
	}

	public static String capitalizeFirst(String text) {
		return (text.length() > 0 ? Character.toUpperCase(text.charAt(0))
				+ text.substring(1).toLowerCase() : text);
	}

}
