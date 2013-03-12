package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class DeleteEventReturned implements Event {

	private final Boolean deleteSuccessfull;

	public DeleteEventReturned(Boolean result) {
		this.deleteSuccessfull = result;
	}

	public Boolean getDeleteSuccessful() {
		return deleteSuccessfull;
	}

}
