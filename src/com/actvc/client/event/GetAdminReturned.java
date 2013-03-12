package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TU;

public class GetAdminReturned implements Event {

	private final TU user;

	public GetAdminReturned(TU result) {
		this.user = result;
	}

	public TU getUser() {
		return user;
	}

}
