package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TET;

public class LoadEventTypesReturned implements Event {

	private final List<TET> eventTypeList;

	public LoadEventTypesReturned(List<TET> result) {
		this.eventTypeList = result;
	}

	public List<TET> getEventTypeList() {
		return eventTypeList;
	}
}
