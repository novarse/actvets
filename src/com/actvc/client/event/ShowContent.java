package com.actvc.client.event;

import com.actvc.client.ContentFuture;
import com.actvc.client.controller.Event;

public class ShowContent implements Event {

	private final Class<ContentFuture> cls;

	public ShowContent(Class<ContentFuture> cls) {
		this.cls = cls;
	}

	public Class<ContentFuture> getCls() {
		return cls;
	}

}
