package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TE;

public class LoadEventDatesAndIdsForEditReturned implements Event {

	private List<TE> eventList;

	public LoadEventDatesAndIdsForEditReturned(List<TE> result) {
		this.eventList = result;
	}

	public void setEventList(List<TE> eventList) {
		this.eventList = eventList;
	}

	public List<TE> getEventList() {
		return eventList;
	}

}
