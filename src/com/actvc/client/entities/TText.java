package com.actvc.client.entities;

import com.google.appengine.api.datastore.Text;
import com.google.code.twig.annotation.Id;

public class TText implements TEntity {

	@Id
	Long id;

	Text text;

	@Override
	public String toExportForm() {
		return getId() + TAB + getText();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

}
