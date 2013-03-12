package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;

public class LoadDirectorsReturned implements Event {

	private final List<String> directorList;

	public LoadDirectorsReturned(List<String> result) {
		this.directorList = result;
	}

	public List<String> getDirectorList() {
		return directorList;
	}

}
