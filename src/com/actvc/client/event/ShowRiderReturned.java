package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TR;

public class ShowRiderReturned implements Event {

	private List<TR> getRiderList;

	public ShowRiderReturned(List<TR> result) {
		this.setGetRiderList(result);
	}

	public void setGetRiderList(List<TR> getRiderList) {
		this.getRiderList = getRiderList;
	}

	public List<TR> getGetRiderList() {
		return getRiderList;
	}

}
