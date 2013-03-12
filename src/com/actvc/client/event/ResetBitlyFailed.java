package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class ResetBitlyFailed implements Event {

	private final Throwable caught;

	public ResetBitlyFailed(Throwable caught) {
		this.caught = caught;
	}

	public Throwable getCaught() {
		return caught;
	}

}
