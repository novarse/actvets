package com.actvc.client.common;

import com.google.gwt.user.client.ui.Button;

public class MyButton extends Button {
	private Long tag;

	public MyButton(String html, Long tag) {
		this.setHTML(html);
		this.setTag(tag);
	}

	public void setTag(Long tag) {
		this.tag = tag;
	}

	public Long getTag() {
		return tag;
	}

}
