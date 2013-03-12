package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class LoadRidersFailed implements Event {

	private final Throwable throwable;

	public LoadRidersFailed(Throwable caught) {
		this.throwable = caught;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
