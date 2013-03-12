package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class Correcting0PlacesReturned implements Event {

	private final Integer count;

	public Correcting0PlacesReturned(Integer result) {
		this.count = result;
	}

	public Integer getCount() {
		return count;
	}

}
