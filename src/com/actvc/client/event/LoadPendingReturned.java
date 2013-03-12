package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TPRH;

public class LoadPendingReturned implements Event {

	private final List<TPRH> pendingList;

	public LoadPendingReturned(List<TPRH> result) {
		this.pendingList = result;
	}

	public List<TPRH> getPendingList() {
		return pendingList;
	}
}
