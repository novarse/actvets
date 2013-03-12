package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class DoTest2Returned implements Event {

	private final String result;

	public DoTest2Returned(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

}
