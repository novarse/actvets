package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class GetEventDescReturned implements Event {

	private final String description;

	public GetEventDescReturned(String result) {
		this.description = result;
	}

	public String getDescription() {
		return description;
	}

}
