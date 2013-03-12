package com.actvc.client;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Shuttle implements IsSerializable {

	private String val1;

	private String val2;

	private List<String> strList;

	public Shuttle() {

	}

	public Shuttle(String val1, String val2) {
		this.val1 = val1;
		this.val2 = val2;
	}

	public Shuttle(String[] args) {
		this.strList = Arrays.asList(args);
	}

	public void setValues(List<String> values) {
		this.strList = values;
	}

	public List<String> getValues() {
		return strList;
	}

	public void setVal2(String val2) {
		this.val2 = val2;
	}

	public String getVal2() {
		return val2;
	}

	public void setVal1(String val1) {
		this.val1 = val1;
	}

	public String getVal1() {
		return val1;
	}
}
