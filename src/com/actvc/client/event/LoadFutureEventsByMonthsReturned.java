package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TEDTO;

public class LoadFutureEventsByMonthsReturned implements Event {

	private final TEDTO eventDTO;

	public LoadFutureEventsByMonthsReturned(TEDTO result) {
		this.eventDTO = result;
	}

	public TEDTO getEventList() {
		return eventDTO;
	}

}
