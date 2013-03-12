package com.actvc.client.entities;

import java.io.Serializable;

import com.google.appengine.api.datastore.Text;
import com.google.code.twig.annotation.Id;
import com.google.code.twig.annotation.Type;

public class TEL implements Serializable, TEntity {

	@Id
	private Long id;

	private String location;

	private String state;

	@Type(Text.class)
	private String directions;

	private Float latitude;

	private Float longitude;

	public TEL() {

	}

	public TEL(Long lid, String location, String state, String directions,
			Float latitude, Float longitude) {
		super();
		this.id = lid;
		this.location = location;
		this.state = state;
		this.directions = directions;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	public String getDirections() {
		return directions;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	@Override
	public String toExportForm() {
		return getId() + TAB + getLocation() + TAB + getDirections() + TAB
				+ getLatitude() + TAB + getLongitude() + TAB + getState();
	}

}
