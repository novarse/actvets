package com.actvc.client.entities;

import java.io.Serializable;

import com.google.code.twig.annotation.Id;

public class TSe implements Serializable, TEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String season;

	private int listOrder;

	public TSe() {

	}

	public String getSeason() {
		return season;
	}

	public TSe(Long id, String season, int listOrder) {
		super();
		this.id = id;
		this.season = season;
		this.listOrder = listOrder;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;

	}

	@Override
	public String toExportForm() {
		return getId() + TAB + getSeason() + TAB + getListOrder();
	}

	public void setListOrder(int listOrder) {
		this.listOrder = listOrder;
	}

	public int getListOrder() {
		return listOrder;
	}
}
