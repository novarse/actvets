package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;

public class GetEventDatesAndDescsByEventTypeReturned implements Event {

	private final List<String> datesAndIds;

	public GetEventDatesAndDescsByEventTypeReturned(List<String> result) {
		this.datesAndIds = result;
	}

	public List<String> getDatesAndIds() {
		return datesAndIds;
	}

}
