package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class DisplayFailedEvent implements Event {

	private final Throwable throwable;

	public DisplayFailedEvent(Throwable throwable) {
		this.throwable = throwable;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
