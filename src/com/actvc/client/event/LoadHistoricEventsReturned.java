package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TEDTO;

public class LoadHistoricEventsReturned implements Event {

	private final TEDTO eventList;

	public LoadHistoricEventsReturned(TEDTO result) {
		this.eventList = result;
	}

	public TEDTO getEventList() {
		return eventList;
	}

}
