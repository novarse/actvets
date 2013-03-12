package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TS;

public class GetTSForUtilities implements Event {

	private TS system = null;

	public GetTSForUtilities(TS system) {
		this.system = system;
	}

	public GetTSForUtilities() {
	}

	public TS getSystem() {
		return system;
	}

}
