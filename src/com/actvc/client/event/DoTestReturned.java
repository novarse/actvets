package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class DoTestReturned implements Event {

	private final String result;

	public DoTestReturned(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

}
