package com.actvc.client.edit;

import com.actvc.client.ContentEventDetail;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.TEL;
import com.actvc.client.event.DeleteLocationReturned;
import com.actvc.client.event.GetLocationReturned;
import com.actvc.client.event.LoadLocationsReturned;
import com.actvc.client.event.SaveLocationFailed;
import com.actvc.client.event.SaveLocationReturned;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class EditEventLocation extends LookupEditBase {
	private enum State {
		ACT, NSW, NT, QLS, SA, TAS, VIC, WA;
	}

	TextBox location;
	ListBox state;
	TextArea directions;
	TextBox latitude;
	TextBox longitude;

	@Override
	void doDelete() {
		if (lookup.getSelectedIndex() == 0) {
			Window.alert("Select a Location to delete");
			return;
		}
		service.deleteLocation(getLookupId());
	}

	@Override
	void doLookupChange(ChangeEvent event) {
		service.getEditLocation(getLookupId());
	}

	@Override
	void doSave() {
		Float lat = 0F;
		Float lon = 0F;
		if (location.getText().length() == 0) {
			Window.alert("Enter a Location description");
			location.setFocus(true);
		} else if (directions.getText().length() == 0) {
			Window.alert("Enter Directions");
			directions.setFocus(true);
		} else if (latitude.getText().length() == 0
				&& longitude.getText().length() != 0) {
			Window.alert("Enter the Latitude");
			latitude.setFocus(true);
		} else if (longitude.getText().length() == 0
				&& latitude.getText().length() != 0) {
			Window.alert("Enter the Longitude");
			longitude.setFocus(true);
		} else {
			try {
				lat = latitude.getText().length() == 0 ? 0F : Float
						.parseFloat(latitude.getText());
			} catch (Exception e) {
				Window.alert("A number should be given for the Latitude or this should be left blank");
				latitude.setFocus(true);
				return;
			}
			try {
				lon = longitude.getText().length() == 0 ? 0F : Float
						.parseFloat(longitude.getText());
			} catch (Exception e) {
				Window.alert("A number should be given for the Longitude or this should be left blank");
				longitude.setFocus(true);
				return;
			}
			TEL l = new TEL();
			if (getLookupId() != 0) {
				l.setId(getLookupId());
			}
			l.setLocation(location.getText());
			l.setDirections(directions.getText());
			l.setState(state.getItemText(state.getSelectedIndex()));
			if (lat != 0F) {
				l.setLatitude(lat);
			}
			if (lon != 0F) {
				l.setLongitude(lon);
			}
			service.saveLocation(l);
			saveBtn.setEnabled(false);
		}
	}

	@Override
	void buildContent() {
		location = new TextBox();
		state = new ListBox();
		directions = new TextArea();
		latitude = new TextBox();
		longitude = new TextBox();

		contentTbl = new FlexTable();

		contentTbl.setWidget(0, 0, new Label("Location"));
		contentTbl.setWidget(0, 1, location);
		contentTbl.setWidget(1, 0, new Label("State"));
		contentTbl.setWidget(1, 1, state);
		contentTbl.setWidget(2, 0, new Label("Directions"));
		contentTbl.setWidget(2, 1, directions);
		contentTbl.setWidget(3, 0, new Label("Latitude"));
		contentTbl.setWidget(3, 1, latitude);
		contentTbl.setWidget(4, 0, new Label("Longitude"));
		contentTbl.setWidget(4, 1, longitude);

		contentPanel.add(contentTbl);

	}

	@Override
	void resetForm() {
		location.setText("");
		state.setSelectedIndex(0);
		directions.setText("");
		latitude.setText("");
		longitude.setText("");
	}

	@Override
	void doFocusHandler() {
		if (!isLookupLoaded()) {
			service.loadLocations();
		}
	}

	@Override
	void wireEtc() {
		deleteBtn.setVisible(false);
		directions.setSize("400px", "100px");
		lookup.addItem("Select a Location");
		for (State s : State.values()) {
			state.addItem(s.toString());
		}

		controller.addListener(LoadLocationsReturned.class,
				new ControllerListener<LoadLocationsReturned>() {

					@Override
					public void event(LoadLocationsReturned result) {
						lookup.clear();
						if (result.getLocationList() != null) {
							lookup.addItem("Select a Location");
							for (String s : result.getLocationList()) {
								String[] items = s.split(";");
								lookup.addItem(items[0], items[1]);
							}
							setLookupLoaded(true);
						}

					}
				});

		controller.addListener(GetLocationReturned.class,
				new ControllerListener<GetLocationReturned>() {

					@Override
					public void event(GetLocationReturned result) {
						if (result.getEventLocation() != null) {
							TEL l = result.getEventLocation();
							location.setText(l.getLocation());
							state.setSelectedIndex(getStateFromList(l
									.getState()));
							directions.setText(l.getDirections());
							if (l.getLatitude() != null) {
								latitude.setText(Float.toString(l.getLatitude()));
							}
							if (l.getLongitude() != null) {
								longitude.setText(Float.toString(l
										.getLongitude()));
							}
						}
					}
				});

		controller.addListener(DeleteLocationReturned.class,
				new ControllerListener<DeleteLocationReturned>() {

					@Override
					public void event(DeleteLocationReturned event) {
						lookup.setSelectedIndex(0);
						Window.alert("Location deleted");
						resetForm();
						service.loadLocations();
					}
				});

		controller.addListener(SaveLocationReturned.class,
				new ControllerListener<SaveLocationReturned>() {

					@Override
					public void event(SaveLocationReturned event) {

						ContentEventDetail.setLastEventID(null);
						Window.alert("Location saved");
						saveBtn.setEnabled(true);
						resetForm();
						service.loadLocations();
					}
				});

		controller.addListener(SaveLocationFailed.class,
				new ControllerListener<SaveLocationFailed>() {

					@Override
					public void event(SaveLocationFailed result) {
						saveBtn.setEnabled(true);
					}
				});
	}

	private int getStateFromList(String state) {
		State s = State.valueOf(state);
		Integer index = null;
		for (int i = 0; i < State.values().length; i++)
			if (State.values()[i].equals(s))
				index = i;
		return index;
	}

}
