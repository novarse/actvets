package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TED;

public class ShowEventDescReturned implements Event {

	private List<TED> EventDescList;

	public ShowEventDescReturned(List<TED> result) {
		this.setEventDescList(result);
	}

	public void setEventDescList(List<TED> eventDescList) {
		EventDescList = eventDescList;
	}

	public List<TED> getEventDescList() {
		return EventDescList;
	}

}
