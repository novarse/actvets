package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class GetRiderPoints implements Event {

	private final long seasonId;

	public GetRiderPoints(int seasonId) {
		this.seasonId = seasonId;
	}

	public long getSeasonId() {
		return seasonId;
	}
}
