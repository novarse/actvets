package com.actvc.client.entities;

import java.io.Serializable;
import java.util.Date;

import com.google.code.twig.annotation.Id;

public class TRH implements Serializable, TEntity {

	@Id
	private String id;

	private Long eventId;

	private Long riderId;

	private Date date;

	private String raceGrade;

	private int place;

	private int overTheLine;

	private String time;

	private int points;

	public TRH(String id, Long eventId, Long riderId, Date date,
			String raceGrade, int place, int overTheLine, String time,
			int points) {
		super();
		this.id = id;
		this.eventId = eventId;
		this.riderId = riderId;
		this.date = date;
		this.raceGrade = raceGrade;
		this.place = place;
		this.overTheLine = overTheLine;
		this.time = time;
		this.points = points;
	}

	public TRH() {

	}

	public void setId(long eventId, long riderId) {
		this.id = eventId + "_" + riderId;
		this.eventId = eventId;
		this.riderId = riderId;
	}

	public String getId() {
		return id;
	}

	public void setRaceGrade(String raceGrade) {
		this.raceGrade = raceGrade.trim().toUpperCase();
	}

	public String getRaceGrade() {
		return raceGrade;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getPoints() {
		return points;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return time;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public int getPlace() {
		return place;
	}

	public void setOverTheLine(int overTheLine) {
		this.overTheLine = overTheLine;
	}

	public int getOverTheLine() {
		return overTheLine;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getRiderId() {
		return riderId;
	}

	public void setRiderId(Long riderId) {
		this.riderId = riderId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// private String id;
	// private Long eventId;
	// private Long riderId;
	// private Date date;
	// private String raceGrade;
	// private int place;
	// private int overTheLine;
	// private String time;
	// private int points;
	@Override
	public String toExportForm() {
		return getEventId() + TAB + getDate() + TAB + getRaceGrade() + TAB
				+ getPlace() + TAB + getTime() + TAB + getOverTheLine() + TAB
				+ getPoints() + TAB + getRiderId();
	}

}
