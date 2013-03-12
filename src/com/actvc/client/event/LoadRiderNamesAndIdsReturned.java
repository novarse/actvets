package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;

public class LoadRiderNamesAndIdsReturned implements Event {

	private final List<String> ridersList;

	public LoadRiderNamesAndIdsReturned(List<String> result) {
		this.ridersList = result;
	}

	public List<String> getRidersList() {
		return ridersList;
	}

}
