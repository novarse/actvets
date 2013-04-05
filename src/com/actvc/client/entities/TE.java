package com.actvc.client.entities;

import java.io.Serializable;
import java.util.Date;

import com.actvc.client.util.Utils;
import com.google.code.twig.annotation.Id;

public class TE implements Serializable, TEntity {

	@Id
	private Long id;

	private Date date;

	private Long eventDescriptionId;

	private Long eventTypeId;

	private Long locationId;

	private Long seasonId;

	private Long directorId;

	public TE() {

	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Long getEventDescriptionId() {
		return eventDescriptionId;
	}

	public void setEventDescriptionId(Long eventDescriptionId) {
		this.eventDescriptionId = eventDescriptionId;
	}

	public Long getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(Long eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Long getDirectorId() {
		return directorId;
	}

	public void setDirectorId(Long directorId) {
		this.directorId = directorId;
	}

	public void setSeasonId(Long seasonId) {
		this.seasonId = seasonId;
	}

	public Long getSeasonId() {
		return seasonId;
	}

	@Override
	// public String toExportForm() {
	// String result = getId() + TAB + getDate() + TAB + getDirectorId() + TAB
	// + getSeasonId() + TAB + getLocationId() + TAB
	// + getEventDescriptionId() + TAB + getEventTypeId();
	// return result;
	// }
	public String toExportForm() {
		String result = getId() + TAB + Utils.formatDateForExport(getDate())
				+ TAB + Utils.formatIdForExport(getDirectorId()) + TAB
				+ Utils.formatIdForExport(getSeasonId()) + TAB
				+ getLocationId() + TAB + getEventDescriptionId() + TAB
				+ getEventTypeId();
		return result;
	}

	@Override
	public String toString() {
		return "TE [id=" + id + ", date=" + date + ", eventDescriptionId="
				+ eventDescriptionId + ", eventTypeId=" + eventTypeId
				+ ", locationId=" + locationId + ", seasonId=" + seasonId
				+ ", directorId=" + directorId + "]";
	}

}