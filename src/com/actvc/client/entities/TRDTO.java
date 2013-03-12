package com.actvc.client.entities;

import java.io.Serializable;
import java.util.List;

public class TRDTO implements Serializable {

	private Person rider;

	private List<TRH> raceHistoryList;

	private int eventCount;

	public TRDTO() {

	}

	public Person getRider() {
		return rider;
	}

	public void setRider(Person rider) {
		this.rider = rider;
	}

	public List<TRH> getRaceHistoryList() {
		return raceHistoryList;
	}

	public void setRaceHistoryList(List<TRH> raceHistoryList) {
		this.raceHistoryList = raceHistoryList;
	}

	public void setEventCount(int eventCount) {
		this.eventCount = eventCount;
	}

	public int getEventCount() {
		return eventCount;
	}

}
