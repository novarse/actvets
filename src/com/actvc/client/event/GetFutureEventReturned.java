package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TEDTO;

public class GetFutureEventReturned implements Event {

	private final TEDTO futureEvent;

	public GetFutureEventReturned(TEDTO result) {
		this.futureEvent = result;
	}

	public TEDTO getDetails() {
		return futureEvent;
	}

}
