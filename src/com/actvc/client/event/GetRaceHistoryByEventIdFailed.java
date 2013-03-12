package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class GetRaceHistoryByEventIdFailed implements Event {

	private final Throwable throwable;

	public GetRaceHistoryByEventIdFailed(Throwable caught) {
		this.throwable = caught;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
