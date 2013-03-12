package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TGCR;

public class LoadGradeChangeRequests implements Event {

	private final List<TGCR> list;

	public LoadGradeChangeRequests(List<TGCR> result) {
		this.list = result;
	}

	public List<TGCR> getList() {
		return list;
	}

}
