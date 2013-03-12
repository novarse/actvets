package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TRH;

public class GetRaceHistoryReturned implements Event {

	private final TRH raceHistory;

	public GetRaceHistoryReturned(TRH result) {
		this.raceHistory = result;
	}

	public TRH getRaceHistory() {
		return raceHistory;
	}

}
