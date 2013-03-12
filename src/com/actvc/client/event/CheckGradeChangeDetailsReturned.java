package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class CheckGradeChangeDetailsReturned implements Event {

	private final Boolean detailsOk;

	public CheckGradeChangeDetailsReturned(Boolean result) {
		this.detailsOk = result;
	}

	public Boolean getDetailsOk() {
		return detailsOk;
	}

}
