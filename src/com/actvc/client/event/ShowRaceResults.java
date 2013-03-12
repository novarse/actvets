package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class ShowRaceResults implements Event {

	private final Long eventID;

	public ShowRaceResults(Long eventID) {
		this.eventID = eventID;
	}

	public Long getEventID() {
		return eventID;
	}

}
