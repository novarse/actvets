package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class GetRiderHistoryByIdFailed implements Event {

	private final Throwable error;

	public GetRiderHistoryByIdFailed(Throwable caught) {
		this.error = caught;
	}

	public Throwable getError() {
		return error;
	}

}
