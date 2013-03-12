package com.actvc.client.entities;

import java.io.Serializable;

public class TSDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1557640625064736767L;

	String event;

	private String directorMessage;

	TS system;

	public TSDao() {

	}

	public String getDaoEvent() {
		return event;
	}

	public void setDaoEvent(String daoEvent) {
		this.event = daoEvent;
	}

	public TS getSystem() {
		return system;
	}

	public void setSystem(TS system) {
		this.system = system;
	}

	public String getDirectorMessage() {
		return directorMessage;
	}

	public void setDirectorMessage(String directorMessage) {
		this.directorMessage = directorMessage;
	}
}
