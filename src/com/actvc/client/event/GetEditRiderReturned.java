package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.Person;

public class GetEditRiderReturned implements Event {

	private final Person rider;

	public GetEditRiderReturned(Person result) {
		this.rider = result;
	}

	public Person getRider() {
		return rider;
	}

}
