package com.actvc.client.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TEDTO implements Serializable {
	// twig event dto

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	LinkedHashMap<Long, TE> eventMap = new LinkedHashMap<Long, TE>();

	HashMap<Long, TED> decriptionMap = new HashMap<Long, TED>();

	HashMap<Long, TET> typeMap = new HashMap<Long, TET>();

	HashMap<Long, TEL> locationMap = new HashMap<Long, TEL>();

	HashMap<Long, TR> directorMap = new HashMap<Long, TR>();

	LinkedHashMap<String, TRH> raceHistoryMap = new LinkedHashMap<String, TRH>();

	HashMap<Long, TR> riderMap = new HashMap<Long, TR>();

	public TEDTO() {

	}

	public LinkedHashMap<Long, TE> getEventMap() {
		return eventMap;
	}

	public void setEventMap(LinkedHashMap<Long, TE> eventMap) {
		this.eventMap = eventMap;
	}

	public HashMap<Long, TED> getDescriptionMap() {
		return decriptionMap;
	}

	public void setDecriptionMap(HashMap<Long, TED> decriptionMap) {
		this.decriptionMap = decriptionMap;
	}

	public HashMap<Long, TET> getTypeMap() {
		return typeMap;
	}

	public void setTypeMap(HashMap<Long, TET> typeMap) {
		this.typeMap = typeMap;
	}

	public HashMap<Long, TEL> getLocationMap() {
		return locationMap;
	}

	public void setLocationMap(HashMap<Long, TEL> locationMap) {
		this.locationMap = locationMap;
	}

	public HashMap<Long, TR> getDirectorMap() {
		return directorMap;
	}

	public void setDirectorMap(HashMap<Long, TR> directorMap) {
		this.directorMap = directorMap;
	}

	public LinkedHashMap<String, TRH> getRaceHistoryMap() {
		return raceHistoryMap;
	}

	public void setRaceHistoryMap(LinkedHashMap<String, TRH> raceHistoryMap) {
		this.raceHistoryMap = raceHistoryMap;
	}

	public HashMap<Long, TR> getRiderMap() {
		return riderMap;
	}

	public void setRiderMap(HashMap<Long, TR> riderMap) {
		this.riderMap = riderMap;
	}

}
