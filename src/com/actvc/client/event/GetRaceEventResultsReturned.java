package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TEDTO;

public class GetRaceEventResultsReturned implements Event {

	private final TEDTO eventDetails;

	public GetRaceEventResultsReturned(TEDTO result) {
		this.eventDetails = result;
	}

	public TEDTO getEventDetails() {
		return eventDetails;
	}

}
