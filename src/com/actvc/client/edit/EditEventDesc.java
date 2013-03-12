package com.actvc.client.edit;

import com.actvc.client.ContentEventDetail;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.TED;
import com.actvc.client.event.GetEditEventDescReturned;
import com.actvc.client.event.LoadEventDescReturned;
import com.actvc.client.event.SaveEventDescFailed;
import com.actvc.client.event.SaveEventDescReturned;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class EditEventDesc extends LookupEditBase {
	private TextArea description;
	private TextBox distLong;
	private TextBox distShort;
	private CheckBox isActiveChk;

	@Override
	void buildContent() {
		description = new TextArea();
		distLong = new TextBox();
		distShort = new TextBox();
		isActiveChk = new CheckBox();
		contentTbl = new FlexTable();
		contentTbl.setWidget(0, 0, new Label("Description"));
		contentTbl.setWidget(0, 1, description);
		contentTbl.setWidget(1, 0, new Label("Short Distance"));
		contentTbl.setWidget(1, 1, distShort);
		contentTbl.setWidget(2, 0, new Label("Long Distance"));
		contentTbl.setWidget(2, 1, distLong);
		contentTbl.setWidget(3, 0, new Label("Is Active"));
		contentTbl.setWidget(3, 1, isActiveChk);
		contentPanel.add(contentTbl);

		description.setWidth("290px");
	}

	@Override
	void resetForm() {
		resetLookup();
		description.setText("");
		distLong.setText("");
		distShort.setText("");
		isActiveChk.setValue(true);
		if (editRb.getValue()) {
			enableContentFields(false);
		}
	}

	@Override
	void doDelete() {
		// TODO Auto-generated method stub

	}

	@Override
	void doLookupChange(ChangeEvent event) {
		service.getEditEventDesc(getLookupId());
	}

	@Override
	void doSave() {
		if (description.getText().trim().length() == 0) {
			Window.alert("Enter a Description");
			return;
		}
		TED ed = new TED();
		if (editRb.getValue()) {
			ed.setId(getLookupId());
		}
		ed.setDescription(description.getText());
		if (distLong.getText().length() != 0) {
			ed.setDistLong(distLong.getText());
		}
		if (distShort.getText().length() != 0) {
			ed.setDistShort(distShort.getText());
		}
		ed.setActive(isActiveChk.getValue());
		saveBtn.setEnabled(false);
		service.saveEventDesc(ed);
	}

	@Override
	void doFocusHandler() {
		if (!isLookupLoaded()) {
			service.loadEventDesc();
		}
	}

	@Override
	void wireEtc() {
		lookup.addItem("Select a Description");
		description.setSize("400px", "100px");
		deleteBtn.setVisible(false);
		distLong.setStylePrimaryName("numbertextbox");
		distShort.setStylePrimaryName("numbertextbox");

		controller.addListener(LoadEventDescReturned.class,
				new ControllerListener<LoadEventDescReturned>() {

					@Override
					public void event(LoadEventDescReturned result) {

						lookup.clear();
						lookup.addItem("Select a Description");
						for (TED ed : result.getEventDescList()) {
							String s = ed.getDescription();
							if (s.length() > 45)
								s = s.substring(0, 45);
							lookup.addItem(s, ed.getId().toString());
						}
						setLookupLoaded(true);
					}
				});

		controller.addListener(SaveEventDescReturned.class,
				new ControllerListener<SaveEventDescReturned>() {

					@Override
					public void event(SaveEventDescReturned event) {
						ContentEventDetail.setLastEventID(null);
						resetForm();
						if (Window
								.confirm("Data Saved. Reload descriptions now?")) {
							service.loadEventDesc();
							service.loadActiveEventDesc();
						}
						saveBtn.setEnabled(true);

					}
				});

		controller.addListener(GetEditEventDescReturned.class,
				new ControllerListener<GetEditEventDescReturned>() {

					@Override
					public void event(GetEditEventDescReturned result) {
						description.setText(result.getEventDesc()
								.getDescription());
						distLong.setText(result.getEventDesc().getDistLong());
						distShort.setText(result.getEventDesc().getDistShort());
						isActiveChk.setValue(result.getEventDesc().isActive());
					}
				});

		controller.addListener(SaveEventDescFailed.class,
				new ControllerListener<SaveEventDescFailed>() {

					@Override
					public void event(SaveEventDescFailed result) {
						saveBtn.setEnabled(true);
					}
				});

	}
}
