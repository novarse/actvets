package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class EventShow implements Event {

	private final int eventID;

	public EventShow(int eventID) {
		this.eventID = eventID;
	}

	public int getEventID() {
		return eventID;
	}

}
