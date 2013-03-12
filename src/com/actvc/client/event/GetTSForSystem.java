package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TS;

public class GetTSForSystem implements Event {

	private TS system = null;

	public GetTSForSystem(TS system) {
		this.system = system;
	}

	public GetTSForSystem() {
		// TODO Auto-generated constructor stub
	}

	public TS getSystem() {
		return system;
	}

}
