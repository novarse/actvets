package com.actvc.server.util;

import java.util.Calendar;
import java.util.Date;

public class Utils {

	public static Date getDateMonthsFrom(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

}
