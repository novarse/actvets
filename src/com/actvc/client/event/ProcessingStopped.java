package com.actvc.client.event;

import com.actvc.client.controller.Event;

public class ProcessingStopped implements Event {

	private String msg = null;

	public ProcessingStopped() {

	}

	public ProcessingStopped(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

}
