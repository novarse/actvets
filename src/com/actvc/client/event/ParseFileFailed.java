package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class ParseFileFailed implements Event {

	private final Throwable throwable;

	public ParseFileFailed(Throwable caught) {
		this.throwable = caught;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
