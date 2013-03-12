package com.actvc.client.common;

import com.google.gwt.user.client.ui.Label;

public class MyLabel extends Label {
	private Long tag;

	public MyLabel(String text, Long tag) {
		this.setText(text);
		this.setTag(tag);
	}

	public void setTag(Long tag) {
		this.tag = tag;
	}

	public Long getTag() {
		return tag;
	}

}
