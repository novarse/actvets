package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;

public class LoadRidersReturned implements Event {

	private final List<String> riders;

	public List<String> getRiderList() {
		return this.riders;
	}

	public LoadRidersReturned(List<String> riders) {
		this.riders = riders;
	}

}
