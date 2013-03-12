package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class LoginFailed implements Event {

	private final Throwable throwable;

	public LoginFailed(Throwable caught) {
		this.throwable = caught;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
