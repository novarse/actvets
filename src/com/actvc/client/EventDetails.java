package com.actvc.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EventDetails implements IsSerializable {

	private Long eventID;
	private Date date;

	private String eventType;

	private String location;
	private String directions;
	private Float latitude, longitude;

	private String description;
	private String distLong;
	private String distShort;

	private String director;
	private String directorEmail;
	private String directorPhone;

	public Long getEventID() {
		return eventID;
	}

	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public String getDistLong() {
		return distLong;
	}

	public void setDistLong(String distLong) {
		this.distLong = distLong;
	}

	public String getDistShort() {
		return distShort;
	}

	public void setDistShort(String distShort) {
		this.distShort = distShort;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDirections() {
		return directions;
	}

	public void setDirections(String directions) {
		this.directions = directions;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getDirectorEmail() {
		return directorEmail;
	}

	public void setDirectorEmail(String directorEmail) {
		this.directorEmail = directorEmail;
	}

	public String getDirectorPhone() {
		return directorPhone;
	}

	public void setDirectorPhone(String directorPhone) {
		this.directorPhone = directorPhone;
	}

	public EventDetails() {
	}

}