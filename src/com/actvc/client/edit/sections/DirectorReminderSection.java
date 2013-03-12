package com.actvc.client.edit.sections;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class DirectorReminderSection extends BasicSection {

	private final TextArea message;
	private final Button saveBtn;
	private final FlexTable tbl;
	private final TextBox daysBeforeEvent;

	public DirectorReminderSection(boolean hasTopSeparator,
			boolean hasBottomSeparator) {
		super(hasTopSeparator, hasBottomSeparator);

		message = new TextArea();
		tbl = new FlexTable();
		daysBeforeEvent = new TextBox();
		saveBtn = new Button("Save Message");

		tbl.setWidget(0, 0, new Label("Send Days Before Event"));
		tbl.setWidget(0, 1, daysBeforeEvent);

		content.add(new HTML(
				"<b>Director Event Email Message</b><br>To include the event description use '###' in the message. Use '@@@' to include a link to the event."));
		content.add(message);
		content.add(tbl);
		content.add(saveBtn);

		message.setSize("700px", "200px");
		daysBeforeEvent.setWidth("50px");
		daysBeforeEvent.setStylePrimaryName("numbertextbox");
	}

	public TextArea getMessage() {
		return message;
	}

	public Button getSaveBtn() {
		return saveBtn;
	}

	public TextBox getDaysBeforeEvent() {
		return daysBeforeEvent;
	}

}