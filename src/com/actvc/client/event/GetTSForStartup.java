package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TS;

public class GetTSForStartup implements Event {

	private TS system = null;

	public GetTSForStartup(TS system) {
		this.system = system;
	}

	public TS getSystem() {
		return system;
	}

	public GetTSForStartup() {
	}
}
