package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TSDao;

public class GetTSDaoForSystem implements Event {

	private TSDao systemDao = null;

	public GetTSDaoForSystem(TSDao result) {
		this.systemDao = result;
	}

	public GetTSDaoForSystem() {
		// TODO Auto-generated constructor stub
	}

	public TSDao getSystemDao() {
		return systemDao;
	}

}
