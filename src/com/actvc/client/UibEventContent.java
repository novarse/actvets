package com.actvc.client;

import com.actvc.client.common.MyDate;
import com.actvc.client.entities.Person;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TED;
import com.actvc.client.entities.TEDTO;
import com.actvc.client.entities.TEL;
import com.actvc.client.entities.TET;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.maps3.client.LatLng;
import com.googlecode.maps3.client.MapJSO;
import com.googlecode.maps3.client.MapOptions;
import com.googlecode.maps3.client.MapWidget;
import com.googlecode.maps3.client.Marker;

public class UibEventContent extends Composite {

	private static UibEventContentUiBinder uiBinder = GWT
			.create(UibEventContentUiBinder.class);

	interface UibEventContentUiBinder extends UiBinder<Widget, UibEventContent> {
	}

	@UiField
	Label titleField;

	@UiField
	Label dateField;

	@UiField
	HTML descriptionField;

	@UiField
	HTML directorField;

	@UiField
	HTML directionsField;

	@UiField
	VerticalPanel mapField;

	private static Long lastEventID = null;
	private Float latitude;
	private Float longitude;

	public UibEventContent() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void loadDetails(TEDTO dto) {
		TE event = (TE) dto.getEventMap().values().toArray()[0];
		TEL l = (TEL) dto.getLocationMap().values().toArray()[0];
		TED desc = (TED) dto.getDescriptionMap().values().toArray()[0];
		TET t = (TET) dto.getTypeMap().values().toArray()[0];
		Person dir = null;
		String dirName = "";
		if (!dto.getDirectorMap().isEmpty()) {
			dir = (Person) dto.getDirectorMap().values().toArray()[0];
			dirName = dir.getFirstName() + " " + dir.getLastName();
		}

		titleField.setText(l.getLocation()
				+ " "
				+ t.getDescription()
				+ (((dirName).length() != 0) ? " (" + dirName + ") Dist("
						: " Dist(")
				+ desc.getDistLong()
				+ "km"
				+ (desc.getDistShort().equals(desc.getDistLong()) ? ")" : "/"
						+ desc.getDistShort() + "km)"));
		dateField.setText(MyDate.getDateStr(event.getDate()));
		descriptionField.setHTML(desc.getDescription());

		String directorStr = (dirName != null && dirName.length() > 0) ? "Race Director: "
				+ dirName
				: "";
		if (directorStr.length() != 0) {
			directorStr += (dir.getEmail() != null) ? ", " + dir.getEmail()
					: "";
			directorStr += (dir.getPhoneHome() != null) ? ", "
					+ dir.getPhoneHome() : "";
			directorField.setHTML(directorStr);
		} else {
			directorField.setText("");
		}

		directionsField.setText(l.getDirections());

		mapField.clear();
		if (l.getLatitude() != null) {
			latitude = l.getLatitude();
			longitude = l.getLongitude();
			String url = "http://maps.google.com/maps/api/staticmap?center="
					+ l.getLatitude() + ", " + l.getLongitude()
					+ "&zoom=15&size=400x300&maptype=roadmap"
					+ "&markers=color:blue|label:E|" + l.getLatitude() + ", "
					+ l.getLongitude() + "&sensor=false";
			Image mapImg = new Image(url);
			mapField.add(mapImg);
			mapImg.setTitle("Click to display an interactive map");
			mapImg.setStylePrimaryName("cursorpointer");
			mapImg.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					showMap();
				}
			});
		}

		lastEventID = event.getId();
	}

	public boolean isLoaded(Long eventID) {
		return (lastEventID != null && eventID.equals(lastEventID)) ? true
				: false;
	}

	private void showMap() {
		final MapOptions options = MapOptions.newInstance();
		options.setMapTypeId();
		LatLng latLng = LatLng.newInstance(latitude, longitude);
		options.setCenter(latLng);
		options.setZoom(15);

		MapWidget map = new MapWidget(options);
		map.setSize("400px", "300px");

		MapJSO mapJso = map.getMapJSO();
		Marker marker = Marker.newInstance();
		marker.setPosition(latLng);
		marker.setMap(mapJso);

		mapField.clear();
		mapField.add(map);
	}

	public static void setLastEventID(Long lastEventID) {
		UibEventContent.lastEventID = lastEventID;
	}

}