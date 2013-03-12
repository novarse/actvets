package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class CheckGradeChangeDetailsFailed implements Event {

	private final Throwable throwable;

	public CheckGradeChangeDetailsFailed(Throwable caught) {
		this.throwable = caught;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
