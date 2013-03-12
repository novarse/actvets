package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class GetEventDescByEventIdReturned implements Event {

	private final String eventDesc;

	public GetEventDescByEventIdReturned(String result) {
		this.eventDesc = result;
	}

	public String getEventDesc() {
		return eventDesc;
	}

}
