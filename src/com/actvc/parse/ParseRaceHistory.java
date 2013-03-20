package com.actvc.parse;

import java.text.ParseException;

import com.actvc.client.common.MyConst;
import com.actvc.client.entities.TEntity;
import com.actvc.client.entities.TRH;
import com.actvc.server.AppServiceImpl;

public class ParseRaceHistory implements ParseBase {
	@Override
	public TEntity getEntity(String[] items) {
		int place = (items[3].length() == 0 || items[3].equals("0")) ? MyConst
				.getPlacenotset() : Integer.parseInt(items[3]);
		String t = items[4];
		String[] dateTime = t.split(" ");
		if (dateTime.length == 2) {
			t = dateTime[1];
		} else if (dateTime.length == 1) {
			t = dateTime[0];
		} else {
			t = "";
		}
		int overTheLine = (items[5].length() == 0 || items[5].equals("0")) ? MyConst
				.getPlacenotset() : Integer.parseInt(items[5]);
		int points = (items[6].length() == 0) ? MyConst.getPointsnotset()
				: Integer.parseInt(items[6]);

		TRH rh = null;
		try {
			rh = new TRH(items[0] + "_" + items[7], Long.parseLong(items[0]),
					Long.parseLong(items[7]),
					AppServiceImpl.processDate(items[1]), items[2].replace(
							"\"", ""), "", place, overTheLine, t, points, "");

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rh;
	}

}
