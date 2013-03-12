package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class ShowRiderResults implements Event {

	private final Long riderID;

	public ShowRiderResults(Long riderID) {
		this.riderID = riderID;
	}

	public Long getRiderID() {
		return riderID;
	}

}
