package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TED;

public class LoadActiveEventDescReturned implements Event {

	private final List<TED> activeDescList;

	public LoadActiveEventDescReturned(List<TED> result) {
		this.activeDescList = result;
	}

	public List<TED> getActiveDescList() {
		return activeDescList;
	}

}
