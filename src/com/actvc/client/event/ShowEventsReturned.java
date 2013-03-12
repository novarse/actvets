package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TE;

public class ShowEventsReturned implements Event {

	private List<TE> events;

	public ShowEventsReturned(List<TE> result) {
		this.setEvents(result);
	}

	public void setEvents(List<TE> events) {
		this.events = events;
	}

	public List<TE> getEvents() {
		return events;
	}

}
