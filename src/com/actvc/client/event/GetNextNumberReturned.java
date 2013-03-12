package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class GetNextNumberReturned implements Event {

	private final Integer nextNumber;

	public GetNextNumberReturned(Integer result) {
		this.nextNumber = result;
	}

	public Integer getNextNumber() {
		return nextNumber;
	}

}
