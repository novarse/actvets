package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class CopyOfLoadingScreenHideIt implements Event {

	private String msg = null;

	public CopyOfLoadingScreenHideIt() {

	}

	public CopyOfLoadingScreenHideIt(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

}
