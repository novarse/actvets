package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TR;

public class EditGetRidersByNumberReturned implements Event {

	private final List<TR> riders;

	public EditGetRidersByNumberReturned(List<TR> result) {
		this.riders = result;
	}

	public List<TR> getRiders() {
		return riders;
	}

}
