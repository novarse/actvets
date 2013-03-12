package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class DeleteUtilReturned implements Event {

	private final Integer dataDeleted;

	public DeleteUtilReturned(Integer result) {
		this.dataDeleted = result;
	}

	public Integer getDataDeleted() {
		return dataDeleted;
	}

}
