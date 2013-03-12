package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class GeneralFailure implements Event {

	private final Throwable throwable;

	public GeneralFailure(Throwable caught) {
		this.throwable = caught;
	}

	public Throwable getThrowable() {
		return throwable;
	}
}
