package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TED;

public class GetEditEventDescReturned implements Event {

	private final TED eventDesc;

	public GetEditEventDescReturned(TED result) {
		this.eventDesc = result;
	}

	public TED getEventDesc() {
		return eventDesc;
	}

}
