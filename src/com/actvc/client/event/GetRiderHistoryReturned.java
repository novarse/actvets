package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TRH;

public class GetRiderHistoryReturned implements Event {

	private List<TRH> riderHistoryList;

	public GetRiderHistoryReturned(List<TRH> result) {
		this.riderHistoryList = result;
	}

	public void setRiderHistoryList(List<TRH> riderHistoryList) {
		this.riderHistoryList = riderHistoryList;
	}

	public List<TRH> getRiderHistoryList() {
		return riderHistoryList;
	}

}
