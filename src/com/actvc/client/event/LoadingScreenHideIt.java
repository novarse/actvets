package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class LoadingScreenHideIt implements Event {

	private String msg;

	public LoadingScreenHideIt() {

	}

	public LoadingScreenHideIt(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

}
