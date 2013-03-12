package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TR;

public class LoadRidersForAnEventReturned implements Event {

	private final List<TR> riderList;

	public LoadRidersForAnEventReturned(List<TR> result) {
		this.riderList = result;
	}

	public List<TR> getRiderList() {
		return riderList;
	}

}
