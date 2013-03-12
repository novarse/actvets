package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TEL;

public class GetLocationReturned implements Event {

	private final TEL eventLocation;

	public GetLocationReturned(TEL result) {
		this.eventLocation = result;
	}

	public TEL getEventLocation() {
		return eventLocation;
	}

}
