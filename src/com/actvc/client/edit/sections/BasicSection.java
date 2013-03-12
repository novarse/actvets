package com.actvc.client.edit.sections;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BasicSection extends VerticalPanel {

	protected final VerticalPanel content = new VerticalPanel();

	public BasicSection(boolean hasTopSeparator, boolean hasBottomSeparator) {
		if (hasTopSeparator) {
			this.add(new HTML("<hr>"));
		}
		this.add(content);
		if (hasBottomSeparator) {
			this.add(new HTML("<hr>"));
		}

		init();

	}

	public BasicSection() {
		this.add(content);

		init();

	}

	private void init() {
		this.setStylePrimaryName("width100percentg");
	}

	public VerticalPanel getContent() {
		return content;
	}

}
