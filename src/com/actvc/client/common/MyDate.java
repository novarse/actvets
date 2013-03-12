package com.actvc.client.common;

import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

public class MyDate extends FlexTable {
	public static final String[] months = { "January", "February", "March",
			"April", "May", "June", "July", "August", "September", "October",
			"November", "December" };
	public static final String[] days = { "Sunday", "Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday" };
	private static final int AGEOFFSET = 0;// 18;
	protected Date currentDate = null;

	protected ListBox day = null;
	protected MyList month = null;
	protected ListBox year = null;

	protected ListBox hour = null;
	protected ListBox minute = null;
	protected Label ampmLbl;
	private int yearOffset = 0;

	public MyDate() {
		buildDate(false);
		wire();
	}

	public MyDate(Date d) {
		buildDate(false);
		setDate(d);
		wire();
	}

	public MyDate(boolean hasTime) {
		buildDate(hasTime);
		wire();
	}

	public MyDate(boolean hasTime, int yearOffset) {
		setYearOffSet(yearOffset);
		buildDate(hasTime);
		wire();
	}

	private void wire() {
		hour.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				setAMPM();
			}

		});

		setAMPM();
	}

	protected void setAMPM() {
		int h = hour.getSelectedIndex();
		ampmLbl.setText(h < 11 ? "AM" : h == 23 ? "AM" : "PM");
	}

	private void buildDate(boolean hasTime) {
		currentDate = new Date();
		day = new ListBox();
		month = new MyList(months);
		year = new ListBox();
		hour = new ListBox();
		minute = new ListBox();
		ampmLbl = new Label();

		loadDays();
		loadYears();
		loadTime();

		this.setWidget(0, 0, day);
		this.setWidget(0, 1, month);
		this.setWidget(0, 2, year);
		if (hasTime) {
			this.setWidget(0, 3, new HTML("&nbsp;"));
			this.setWidget(0, 4, hour);
			this.setWidget(0, 5, minute);
			this.setWidget(0, 6, ampmLbl);
		}

		this.setCellPadding(0);
		this.setCellSpacing(0);
	}

	protected void loadTime() {
		for (int i = 1; i < 12; i++)
			hour.addItem(Integer.toString(i));
		hour.addItem("12");
		for (int i = 1; i < 12; i++)
			hour.addItem(Integer.toString(i));
		hour.addItem("12");
		for (int i = 0; i < 60; i++) {
			minute.addItem(i < 10 ? "0" + Integer.toString(i) : Integer
					.toString(i));
		}
	}

	public int get24HourTime() {
		return hour.getSelectedIndex() + 1;
	}

	@SuppressWarnings("deprecation")
	private void loadYears() {
		int currentYear = 1900 + currentDate.getYear() + getYearOffset();
		for (int i = currentYear - AGEOFFSET; i >= currentYear - 100; i--) {
			year.addItem(Integer.toString(i));
		}
	}

	private void loadDays() {
		for (int i = 1; i <= 31; i++) {
			day.addItem(Integer.toString(i));
		}
	}

	@SuppressWarnings("deprecation")
	public Date getDate() {
		return getDate(false);
	}

	public Date getDate(boolean withTime) {
		Date d = null;
		int y = currentDate.getYear() - year.getSelectedIndex() - AGEOFFSET;
		try {
			if (withTime) {
				d = new Date(y, month.getSelectedIndex(),
						day.getSelectedIndex() + 1, hour.getSelectedIndex(),
						minute.getSelectedIndex());
			} else {
				d = new Date(y, month.getSelectedIndex(),
						day.getSelectedIndex() + 1);
			}
		} catch (Exception e) {
			Window.alert("Invalid date specified");
		}
		return d;
	}

	@SuppressWarnings("deprecation")
	public void setDate(Date date) {
		try {
			this.day.setItemSelected(date.getDate() - 1, true);
			this.month.setItemSelected(date.getMonth(), true);
			this.year.setItemSelected(currentDate.getYear() - date.getYear()
					- AGEOFFSET, true);
			this.hour.setItemSelected(date.getHours(), true);
			this.minute.setItemSelected(date.getMinutes(), true);
		} catch (Exception e) {
			System.out.println(date.toString());
			System.out.println(e.getCause().getMessage());
		}
	}

	public void setHour(int hour) {
		this.hour.setSelectedIndex(hour - 1);
	}

	public void setMinute(int minute) {
		this.minute.setSelectedIndex(minute);
	}

	public void reset() {
		Date d = new Date();
		setDate(d);
		setHour(11);
		setMinute(0);
		setAMPM();
	}

	public static String getDateStr(Date date) {
		int hour = date.getHours();
		int minute = date.getMinutes();
		String ampm, hourStr, minStr;
		if (hour < 13) {
			ampm = "am";
		} else {
			hour = hour - 12;
			ampm = "pm";
		}
		minStr = minute < 10 ? "0" + minute : minute + "";
		return MyDate.days[date.getDay()] + ", " + date.getDate() + " "
				+ months[date.getMonth()] + " " + hour + ":" + minStr + ampm;
	}

	public int getYearOffset() {
		return yearOffset;
	}

	public void setYearOffSet(int yearOffset) {
		this.yearOffset = yearOffset;
	}

	public void setEnabled(boolean setIt) {
		day.setEnabled(setIt);
		month.setEnabled(setIt);
		year.setEnabled(setIt);
		hour.setEnabled(setIt);
		minute.setEnabled(setIt);
	}

	public void setYearVisible(boolean isVisible) {
		year.setVisible(isVisible);
	}

	public void setMonthYearVisible(boolean isVisible) {
		month.setVisible(isVisible);
	}

	public void setDayVisible(boolean isVisible) {
		day.setVisible(isVisible);
	}

	public void setHourVisible(boolean isVisible) {
		hour.setVisible(isVisible);
	}

	public void setMinuteYearVisible(boolean isVisible) {
		minute.setVisible(isVisible);
	}

	public void setDayOfMonth(int dayOfMonth) {
		this.day.setItemSelected(dayOfMonth - 1, true);
	}

	public void setMonth(int month) {
		this.month.setItemSelected(month, true);
	}

}