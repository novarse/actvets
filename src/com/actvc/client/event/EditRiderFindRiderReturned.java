package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.Person;

public class EditRiderFindRiderReturned implements Event {

	private final Person rider;

	public EditRiderFindRiderReturned(Person result) {
		this.rider = result;
	}

	public Person getRider() {
		return rider;
	}

}
