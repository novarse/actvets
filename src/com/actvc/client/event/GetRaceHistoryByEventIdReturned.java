package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TRH;

public class GetRaceHistoryByEventIdReturned implements Event {

	private final List<TRH> ridersList;

	public GetRaceHistoryByEventIdReturned(List<TRH> result) {
		this.ridersList = result;
	}

	public List<TRH> getRaceHistoryList() {
		return ridersList;
	}

}
