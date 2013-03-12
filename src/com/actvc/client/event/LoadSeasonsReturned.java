package com.actvc.client.event;

import java.util.List;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.TSe;

public class LoadSeasonsReturned implements Event {

	private final List<TSe> seasonList;

	public LoadSeasonsReturned(List<TSe> result) {
		this.seasonList = result;
	}

	public List<TSe> getSeasonList() {
		return seasonList;
	}

}
