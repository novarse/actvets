package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class GetRaceEventResultsFailed implements Event {

	private final Throwable throwable;

	public GetRaceEventResultsFailed(Throwable caught) {
		this.throwable = caught;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
