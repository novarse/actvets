package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class CountReturned implements Event {

	private final Integer count;

	public CountReturned(Integer result) {
		this.count = result;
	}

	public Integer getCount() {
		return count;
	}

}
