package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TRH;

public class ShowRaceHistoryReturned implements Event {

	private List<TRH> raceHistoryList;

	public ShowRaceHistoryReturned(List<TRH> result) {
		this.setRaceHistoryList(result);
	}

	public void setRaceHistoryList(List<TRH> raceHistoryList) {
		this.raceHistoryList = raceHistoryList;
	}

	public List<TRH> getRaceHistoryList() {
		return raceHistoryList;
	}

}
