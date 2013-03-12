package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class ResultsPopupReturned implements Event {

	private final Long tag;

	public ResultsPopupReturned(Long tag) {
		this.tag = tag;
	}

	public Long getTag() {
		return tag;
	}

}
