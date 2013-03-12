package com.actvc.client.event;

import com.actvc.client.ContentWidget.UibAreas;
import com.actvc.client.controller.Event;

public class ShowOnly implements Event {

	private final UibAreas area;

	private final String arg;

	public ShowOnly(UibAreas area) {
		this.area = area;
		this.arg = null;
	}

	public ShowOnly(UibAreas area, String arg) {
		this.area = area;
		this.arg = arg;
	}

	public UibAreas getArea() {
		return area;
	}

	public String getArg() {
		return arg;
	}

}
