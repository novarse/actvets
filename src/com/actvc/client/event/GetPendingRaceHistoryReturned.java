package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TPRH;

public class GetPendingRaceHistoryReturned implements Event {

	private final TPRH prh;

	public GetPendingRaceHistoryReturned(TPRH result) {
		this.prh = result;
	}

	public TPRH getPrh() {
		return prh;
	}

}
