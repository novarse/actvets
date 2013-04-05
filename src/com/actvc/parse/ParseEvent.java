package com.actvc.parse;

import java.text.ParseException;

import com.actvc.client.entities.TE;
import com.actvc.server.AppServiceImpl;

public class ParseEvent implements ParseBase {

	@Override
	public TE getEntity(String[] items) {
		TE event = new TE();
		event.setId(Long.parseLong(items[0]));
		String dateStr = items[1];
		try {
			event.setDate(AppServiceImpl.processDate(dateStr));
		} catch (ParseException e) {
			throw new RuntimeException("Error parsing event date: " + dateStr);

		}
		event.setDirectorId(!items[2].trim().isEmpty() ? Long
				.parseLong(items[2]) : null);
		// long season = items[3].equals("S") ? 1 : (items[3].equals("W") ? 2 :
		// 0);
		// if (season == 0) {
		// throw new RuntimeException("Invalid season: " + items[3]);
		// }
		event.setSeasonId(!items[3].trim().isEmpty() ? Long.parseLong(items[3])
				: null);
		event.setLocationId(Long.parseLong(items[4]));
		event.setEventDescriptionId(Long.parseLong(items[5]));
		event.setEventTypeId(Long.parseLong(items[6]));
		return event;
	}

}
