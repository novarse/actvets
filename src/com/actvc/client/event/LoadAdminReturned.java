package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TU;

public class LoadAdminReturned implements Event {

	private final List<TU> userList;

	public LoadAdminReturned(List<TU> result) {
		this.userList = result;
	}

	public List<TU> getAdminList() {
		return userList;
	}

}
