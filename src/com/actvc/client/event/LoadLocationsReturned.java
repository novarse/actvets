package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;

public class LoadLocationsReturned implements Event {

	private final List<String> locationList;

	public LoadLocationsReturned(List<String> result) {
		this.locationList = result;
	}

	public List<String> getLocationList() {
		return locationList;
	}

}
