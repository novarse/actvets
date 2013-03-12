package com.actvc.client.event;

import java.util.Map;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TRP;

public class GetRiderPointsReturned implements Event {

	private final Map<String, TRP> pointsMap;

	public GetRiderPointsReturned(Map<String, TRP> result) {
		this.pointsMap = result;
	}

	public Map<String, TRP> getPointsMap() {
		return pointsMap;
	}

}
