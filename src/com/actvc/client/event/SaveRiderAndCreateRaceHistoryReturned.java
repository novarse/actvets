package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class SaveRiderAndCreateRaceHistoryReturned implements Event {

	private final Boolean savedOk;

	public SaveRiderAndCreateRaceHistoryReturned(Boolean savedOk) {
		this.savedOk = savedOk;
	}

	public Boolean savedOk() {
		return savedOk;
	}

}
