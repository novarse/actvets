package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class GeneralFailHideLoading implements Event {

	private final Throwable throwable;

	public GeneralFailHideLoading(Throwable caught) {
		this.throwable = caught;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
