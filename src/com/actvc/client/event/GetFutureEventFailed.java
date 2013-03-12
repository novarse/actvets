package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class GetFutureEventFailed implements Event {

	private final Throwable throwable;

	public GetFutureEventFailed(Throwable caught) {
		this.throwable = caught;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
