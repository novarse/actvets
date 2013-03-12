package com.actvc.client.common;

import com.google.gwt.user.client.ui.Anchor;

public class MyAnchor extends Anchor {
	private Long tag;

	public MyAnchor(String text, Long tag) {
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
