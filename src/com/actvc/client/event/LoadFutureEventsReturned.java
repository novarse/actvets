package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TEDTO;

public class LoadFutureEventsReturned implements Event {

	private final TEDTO eventDTO;

	public LoadFutureEventsReturned(TEDTO result) {
		this.eventDTO = result;
	}

	public TEDTO getEventList() {
		return eventDTO;
	}

}
