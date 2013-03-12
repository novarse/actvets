package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TS;

public class SetTSForEditUtilitiesReturned implements Event {

	private TS system = null;

	public SetTSForEditUtilitiesReturned(TS system) {
		this.system = system;
	}

	public TS getSystem() {
		return system;
	}

	public SetTSForEditUtilitiesReturned() {
	}
}
