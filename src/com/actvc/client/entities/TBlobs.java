package com.actvc.client.entities;

import com.google.appengine.api.datastore.Blob;
import com.google.code.twig.annotation.Id;

public class TBlobs implements TEntity {

	@Id
	int id;

	Blob blob;

	@Override
	public String toExportForm() {
		return getId() + TAB + getBlob();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Blob getBlob() {
		return blob;
	}

	public void setBlob(Blob blob) {
		this.blob = blob;
	}

}
