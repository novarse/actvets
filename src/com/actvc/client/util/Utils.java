package com.actvc.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static String formatDateForExport(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		return formatter.format(date);
	}

	public static String formatIdForExport(Long id) {
		return id == null ? "" : Long.toString(id);
	}
}
