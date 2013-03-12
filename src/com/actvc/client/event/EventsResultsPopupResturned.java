package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class EventsResultsPopupResturned implements Event {

	private final Long tag;

	public EventsResultsPopupResturned(Long tag) {
		this.tag = tag;
	}

	public Long getTag() {
		return tag;
	}

}
