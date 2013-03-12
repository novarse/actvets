package com.actvc.client.common;

import java.util.Date;

import com.google.gwt.user.client.Window;

/**
 * A {@link MyDate} representing an event date.
 * 
 * @author stephen
 * 
 */
public class EventDate extends MyDate {

	private static final int MIN_HOUR = 7;
	private static final int MAX_HOUR = 19;

	public EventDate() {
		super(true, 1);
	}

	@Override
	protected void loadTime() {
		for (int i = 7; i < 12; i++)
			hour.addItem(Integer.toString(i));
		hour.addItem("12");
		for (int i = 1; i <= 7; i++)
			hour.addItem(Integer.toString(i));
		minute.addItem("00");
		minute.addItem("30");
	}

	@Override
	protected void setAMPM() {
		int h = hour.getSelectedIndex();
		ampmLbl.setText(h < 12 - MIN_HOUR ? "AM" : "PM");
	}

	@Override
	public Date getDate() {
		Date d = null;
		int y = currentDate.getYear() - year.getSelectedIndex()
				+ getYearOffset();
		try {
			d = new Date(y, month.getSelectedIndex(),
					day.getSelectedIndex() + 1, hour.getSelectedIndex() + 7,
					minute.getSelectedIndex() == 0 ? 0 : 30);
		} catch (Exception e) {
			Window.alert("Invalid date specified");
		}
		return d;
	}

	@Override
	public void setDate(Date date) {
		try {
			day.setItemSelected(date.getDate() - 1, true);
			month.setItemSelected(date.getMonth(), true);
			year.setItemSelected(currentDate.getYear() - date.getYear()
					+ getYearOffset(), true);
			setHour(date.getHours());
			setMinute(date.getMinutes());
			setAMPM();
		} catch (Exception e) {
			MyLog.log(e.getCause().getMessage());
		}
	}

	@Override
	public void setHour(int hour) {
		if (hour < MIN_HOUR) {
			this.hour.setSelectedIndex(0);
			// ampmLbl.setText("AM");
		} else if (hour > MAX_HOUR) {
			this.hour.setSelectedIndex(this.hour.getItemCount() - 1);
			// ampmLbl.setText("PM");
		} else {
			this.hour.setSelectedIndex(hour - MIN_HOUR);
			// ampmLbl.setText(hour < 12 ? "AM" : "PM");
		}
	}

	@Override
	public void setMinute(int minute) {
		this.minute.setItemSelected(minute >= 30 ? 1 : 0, true);
	}

	@Override
	public void reset() {
		Date d = new Date();
		setDate(d);
		setHour(11);
		setMinute(0);
		setAMPM();
	}
}
