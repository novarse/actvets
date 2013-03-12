package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class LoadingScreenShowIt implements Event {

	private String msg;

	public LoadingScreenShowIt(String msg) {
		this.msg = msg;
	}

	public LoadingScreenShowIt() {
	}

	public String getMsg() {
		return msg;
	}

}
