package com.actvc.client.entities;

import java.io.Serializable;

import com.google.code.twig.annotation.Id;

public class TET implements Serializable, TEntity {

	@Id
	private Long id;

	private String description;

	public TET() {

	}

	public TET(Long id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toExportForm() {
		return getId() + TAB + getDescription();
	}

}
