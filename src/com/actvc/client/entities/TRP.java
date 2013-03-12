package com.actvc.client.entities;

import java.io.Serializable;

/**
 * TRP == rider points. Used as transport for retrieving points scored for a
 * season during a year.
 * 
 * @author stephen
 * 
 */
public class TRP implements Serializable, Comparable<TRP> {

	private static final long serialVersionUID = -4720144258591062241L;

	private Long riderId;

	private String name;

	private int number;

	private Integer points;

	public TRP() {

	}

	public TRP(Long riderId, String name, int number, int points) {
		this.riderId = riderId;
		this.name = name;
		this.number = number;
		this.points = points;
	}

	public Long getRiderId() {
		return riderId;
	}

	public void setpId(Long riderId) {
		this.riderId = riderId;
	}

	public String getName() {
		return name;
	}

	public void setLastName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public TRP add(int points) {
		this.points += points;
		return this;
	}

	@Override
	public int compareTo(TRP rp) {
		return points.compareTo(rp.points);
	}

	@Override
	public String toString() {
		return "Points = " + this.points;

	}
}
