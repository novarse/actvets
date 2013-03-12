package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TE;

public class LoadRecentReturned implements Event {

	private final List<TE> events;

	public LoadRecentReturned(List<TE> result) {
		this.events = result;
	}

	public List<TE> getEvents() {
		return events;
	}

}
