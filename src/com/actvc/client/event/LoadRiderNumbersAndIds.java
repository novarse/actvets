package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;

public class LoadRiderNumbersAndIds implements Event {

	private final List<String> raceNumberList;

	public LoadRiderNumbersAndIds(List<String> result) {
		this.raceNumberList = result;
	}

	public List<String> getRaceNumberList() {
		return raceNumberList;
	}

}
