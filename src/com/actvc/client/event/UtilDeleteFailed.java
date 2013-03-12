package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class UtilDeleteFailed implements Event {

	private final Throwable throwable;

	public UtilDeleteFailed(Throwable caught) {
		this.throwable = caught;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
