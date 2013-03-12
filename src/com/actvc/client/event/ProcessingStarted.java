package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class ProcessingStarted implements Event {

	private String msg = null;

	public ProcessingStarted(String msg) {
		this.msg = msg;
	}

	public ProcessingStarted() {
	}

	public String getMsg() {
		return msg;
	}

}
