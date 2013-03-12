package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class CountFailed implements Event {

	private final Throwable throwable;

	public CountFailed(Throwable caught) {
		this.throwable = caught;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
