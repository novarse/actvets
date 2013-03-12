package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TED;

public class LoadEventDescReturned implements Event {

	private final List<TED> eventDescList;

	public LoadEventDescReturned(List<TED> result) {
		this.eventDescList = result;
	}

	public List<TED> getEventDescList() {
		return eventDescList;
	}

}
