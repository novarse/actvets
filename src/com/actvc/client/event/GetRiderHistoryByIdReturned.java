package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TRDTO;

public class GetRiderHistoryByIdReturned implements Event {

	private final TRDTO riderDetails;

	public GetRiderHistoryByIdReturned(TRDTO result) {
		this.riderDetails = result;
	}

	public TRDTO getRiderDetails() {
		return riderDetails;
	}

}
