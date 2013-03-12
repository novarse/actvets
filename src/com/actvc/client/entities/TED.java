package com.actvc.client.entities;

import java.io.Serializable;

import com.google.code.twig.annotation.Id;

public class TED implements Serializable, TEntity {
	// TED == Event Description

	@Id
	private Long id;

	private String description;

	String distLong;

	String distShort;

	private boolean isActive;

	public TED() {
	}

	public TED(Long id, String description, String distLong, String distShort) {
		this.id = id;
		this.description = description;
		this.distLong = distLong;
		this.distShort = distShort;
		this.setActive(true);
	};

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDistLong() {
		return distLong;
	}

	public void setDistLong(String distLong) {
		this.distLong = distLong;
	}

	public String getDistShort() {
		return distShort;
	}

	public void setDistShort(String distShort) {
		this.distShort = distShort;
	}

	public String toExportForm() {
		return getId() + TAB + getDescription() + TAB + getDistLong() + TAB
				+ getDistShort() + TAB + isActive();
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

}
