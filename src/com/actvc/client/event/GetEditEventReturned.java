package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TE;

public class GetEditEventReturned implements Event {

	private final TE event;

	public GetEditEventReturned(TE result) {
		this.event = result;
	}

	public TE getEvent() {
		return event;
	}

}
