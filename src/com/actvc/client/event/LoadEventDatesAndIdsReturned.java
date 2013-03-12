package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TE;

public class LoadEventDatesAndIdsReturned implements Event {

	private final List<TE> eventList;

	public LoadEventDatesAndIdsReturned(List<TE> result) {
		this.eventList = result;
	}

	public List<TE> getEvents() {
		return eventList;
	}

}
