package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class ShowFutureEvent implements Event {

	private final Long eventId;

	public ShowFutureEvent(Long eventID) {
		this.eventId = eventID;
	}

	public Long getEventId() {
		return eventId;
	}

}
