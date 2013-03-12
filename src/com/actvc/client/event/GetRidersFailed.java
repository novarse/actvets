package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class GetRidersFailed implements Event {

	private final Throwable error;

	public GetRidersFailed(Throwable caught) {
		this.error = caught;
	}

	public Throwable getError() {
		return error;
	}

}
