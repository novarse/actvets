package com.actvc.client.edit;

import com.actvc.client.ContentEventDetail;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.TET;
import com.actvc.client.event.EditEventTypeDoIntialLoad;
import com.actvc.client.event.LoadEventTypesReturned;
import com.actvc.client.event.SaveEventTypeFailed;
import com.actvc.client.event.SaveEventTypeReturned;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class EditEventType extends LookupEditBase {
	TextBox description;

	@Override
	void buildContent() {
		description = new TextBox();

		contentTbl = new FlexTable();
		contentTbl.setWidget(0, 0, new Label("Description"));
		contentTbl.setWidget(0, 1, description);

		contentPanel.add(contentTbl);

		description.setWidth("290px");

	}

	@Override
	void resetForm() {
		resetLookup();
		description.setText("");
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
		description.setText(lookup.getItemText(lookup.getSelectedIndex()));
	}

	@Override
	void doSave() {
		if (description.getText().trim().length() == 0) {
			Window.alert("Enter a Description");
			return;
		}
		TET et = new TET();
		if (editRb.getValue()) {
			et.setId(getLookupId());
		}
		et.setDescription(description.getText());
		saveBtn.setEnabled(false);
		service.saveEventType(et);
	}

	@Override
	void wireEtc() {
		lookup.addItem("Select an Event Type");
		deleteBtn.setVisible(false);

		controller.addListener(EditEventTypeDoIntialLoad.class,
				new ControllerListener<EditEventTypeDoIntialLoad>() {

					@Override
					public void event(EditEventTypeDoIntialLoad event) {
					}
				});

		controller.addListener(LoadEventTypesReturned.class,
				new ControllerListener<LoadEventTypesReturned>() {

					@Override
					public void event(LoadEventTypesReturned result) {
						lookup.clear();
						lookup.addItem("Select an Event Type");
						for (TET et : result.getEventTypeList()) {
							String s = et.getDescription();
							lookup.addItem(s, et.getId().toString());
						}
						setLookupLoaded(true);
					}
				});

		controller.addListener(SaveEventTypeReturned.class,
				new ControllerListener<SaveEventTypeReturned>() {

					@Override
					public void event(SaveEventTypeReturned event) {
						ContentEventDetail.setLastEventID(null);
						resetForm();
						Window.alert("Data Saved");
						saveBtn.setEnabled(true);
						service.loadEventTypes();
					}
				});

		controller.addListener(SaveEventTypeFailed.class,
				new ControllerListener<SaveEventTypeFailed>() {

					@Override
					public void event(SaveEventTypeFailed result) {
						saveBtn.setEnabled(true);
					}
				});
	}

	@Override
	void doFocusHandler() {
		if (!isLookupLoaded()) {
			service.loadEventTypes();
		}
	}

}
