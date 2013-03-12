package com.actvc.client;

import java.util.Date;
import java.util.Map;

import com.actvc.client.common.MyLog;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TED;
import com.actvc.client.entities.TEDTO;
import com.actvc.client.event.LoadFutureEventsByMonthsReturned;
import com.actvc.client.event.ShowFutureEvent;
import com.actvc.client.event.ShowOnly;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.ui.HTML;

public class ContentCalendar extends ContentWidget {

	HTML html = new HTML();
	private boolean calendarCreated;
	private int months = 6;

	public ContentCalendar(String headerTitle) {
		super(headerTitle);

		this.getContent().add(html);

		calendarCreated = false;

		wire();
	}

	private void wire() {
		setShowTag(UibAreas.CALENDAR);
		this.setHeaderImage(CLOSE);
		show(false);

		controller.addListener(LoadFutureEventsByMonthsReturned.class,
				new ControllerListener<LoadFutureEventsByMonthsReturned>() {

					@Override
					public void event(LoadFutureEventsByMonthsReturned result) {
						TEDTO dto = result.getEventList();

						final Map<Long, TE> eventMap = dto.getEventMap();
						final Map<Long, TED> descMap = dto.getDescriptionMap();

						for (Map.Entry<Long, TE> entry : eventMap.entrySet()) {
							TE event = entry.getValue();
							TED desc = descMap.get(event
									.getEventDescriptionId());

							addCalendarItem(getDateStr(event.getDate()),
									getTimeStr(event.getDate()), desc
											.getDescription(), event.getId()
											.toString());
						}
						drawCalendar(html.getElement(), months);
						calendarCreated = true;
					}

					private String getDateStr(Date date) {
						DateTimeFormat fmt = DateTimeFormat
								.getFormat("dd MMM yyyy");
						return fmt.format(date);
					}

					private String getTimeStr(Date date) {
						DateTimeFormat fmt = DateTimeFormat
								.getFormat("hh:mm aa");
						return fmt.format(date);
					}

				});
	}

	native private void addCalendarItem(String date, String time, String desc,
			String id) /*-{
		$wnd.addCalendarItem(date, time, desc.substr(0, 50), id);
	}-*/;

	@Override
	void hbClickEvent(ClickEvent event) {
		controller.event(new ShowOnly(UibAreas.DUAL));
	}

	@Override
	protected void doExtraIfVisible(String months) {
		int m = 6;
		if (calendarCreated) {
			this.setVisible(true);
		} else {
			if (months != null) {
				try {
					m = Integer.parseInt(months);
					if (m < 1 || m > 12) {
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					MyLog.log("An invalid months argument was given. Value must be from 1 to 12 (given value: "
							+ months + ")");
				}
			}
			this.months = m;
			service.loadFutureEventsByMonth(this.months);
		}
	}

	native private void drawCalendar(Element el, int months) /*-{
		$wnd.createPublicHolidays(months);

		var MonthNames = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
		var dayNames = new Array("Sun", "Mon", "Tue", "Wed", "Thu", "Fri",
				"Sat");

		var DisplayDate = new Date();
		var DisplayMonth;
		var DisplayDay;
		var CellBGColor;

		var StartofCurrentYear = new Date($wnd.CurrentDate.getFullYear(), 0, 1);
		StartofCurrentYear.setHours(4);
		var CurrentDay = Math
				.ceil(($wnd.CurrentDate.getTime() - StartofCurrentYear
						.getTime()) / 86400000);
		var startDay = CurrentDay - $wnd.CurrentDate.getDay();
		var startDayDate = new Date();
		startDayDate.setTime(Number(StartofCurrentYear) + (startDay - 1)
				* 86400000);
		var nextYearDate = new Date(startDayDate.getFullYear() + 1, 0, 1);
		var daysInYear = Math.ceil((nextYearDate.getTime() - StartofCurrentYear
				.getTime()) / 86400000);
		var endDay = $wnd.calcEndDay(startDayDate, startDay, months);

		var tblCal = $doc.createElement("table");
		tblCal.id = "tblItems";

		el.appendChild(tblCal);

		// tbl title
		var trTitle = $doc.createElement("tr");
		trTitle.className = "title";

		tblCal.appendChild(trTitle);

		var thTitle = $doc.createElement("th");
		thTitle.className = "thTitle";
		thTitle.setAttribute("colspan", "7");

		trTitle.appendChild(thTitle);

		thTitle.innerHTML = "ACT Veterans Cycling "
				+ $wnd.CurrentDate.getFullYear() + " Calendar";

		// day names
		var trDName = $doc.createElement("tr");

		tblCal.appendChild(trDName);

		for ( var i = 0; i < 7; i++) {
			var thDName = $doc.createElement("th");
			thDName.className = "thDName";

			trDName.appendChild(thDName);

			thDName.innerHTML = dayNames[i];
		}

		// day items
		var trWeek;
		for ( var DayCount = startDay; DayCount <= endDay; DayCount++) {
			DisplayDate.setTime(Number(StartofCurrentYear) + (DayCount - 1)
					* 86400000);
			DisplayDate.setHours(4);
			DisplayDay = DisplayDate.getDate();
			DisplayMonth = DisplayDate.getMonth();

			// Alternate shading between months
			if ((DisplayMonth % 2) == 0)
				CellBGColor = "#E5ECF9"
			else
				CellBGColor = "#F8F8FF";

			if (DayCount == daysInYear + 1) {
				// tbl title
				var trNextYearTitle = $doc.createElement("tr");
				trNextYearTitle.className = "title";

				tblCal.appendChild(trNextYearTitle);

				var thNextYearTitle = $doc.createElement("th");
				thNextYearTitle.className = "thTitle";
				thNextYearTitle.setAttribute("colspan", "7");

				trNextYearTitle.appendChild(thNextYearTitle);

				thNextYearTitle.innerHTML = "ACT Veterans Cycling "
						+ (startDayDate.getFullYear() + 1) + " Calendar";

				trWeek = $doc.createElement("tr");
				trWeek.className = "trWeek";

				tblCal.appendChild(trWeek);

				for ( var d = 0; d < DisplayDate.getDay(); d++) {
					var tdFillDay = $doc.createElement("td");
					tdFillDay.className = "tdFillDay";

					trWeek.appendChild(tdFillDay);
				}
			} else if (DisplayDate.getDay() == 0) {
				trWeek = $doc.createElement("tr");
				trWeek.className = "trWeek";

				tblCal.appendChild(trWeek);
			}

			var tdItem = $doc.createElement("td");
			tdItem.setAttribute("bgColor", CellBGColor);
			tdItem.className = "tdItem";

			trWeek.appendChild(tdItem);

			var divDate = $doc.createElement("div");
			divDate.className = "divDate";

			tdItem.appendChild(divDate);

			divDate.innerHTML = DisplayDay + "-" + MonthNames[DisplayMonth];

			for (items = 0; items < $wnd.CalendarItemCount; items++) {
				if (Number($wnd.CalendarItemDate[items]) == Number(DisplayDate)) {
					var divEvent = $doc.createElement("div");
					var publicHoliday = $wnd.CalendarItemEventId[items] == 0;
					divEvent.className = publicHoliday ? "divEventPH"
							: "divEvent";

					tdItem.appendChild(divEvent);

					if (publicHoliday) {
						divEvent.innerHTML = $wnd.CalendarItemDesc[items];
					} else {
						var a = $doc.createElement("a");
						a.id = $wnd.CalendarItemEventId[items];
						a.className = "itemLink";
						a.onclick = function(e) {
							var id = e.target.getAttribute("id");
							@com.actvc.client.ContentCalendar::displayRaceEvent(Ljava/lang/String;)(id);
							window.location = "http://" + $doc.domain
									+ "/#eventid="
									+ e.target.getAttribute("id") + "&r="
									+ date.getTime();
						}
						a.setAttribute("href", "#");
						var textLink = $doc
								.createTextNode($wnd.CalendarItemTime[items]
										+ " - " + $wnd.CalendarItemDesc[items]);
						a.appendChild(textLink);

						divEvent.appendChild(a);
					}
				}
			}

		}

		function outMsg(msg) {
			var newMsg = $doc.createElement("div");
			newMsg.innerHTML = msg;
			el.appendChild(newMsg);
		}
	}-*/;

	/**
	 * Method called by link's onclick in calendarbuilder.js
	 */
	static public void displayRaceEvent(String id) {
		Long eventId;
		try {
			eventId = Long.parseLong(id);
		} catch (NumberFormatException e) {
			MyLog.log("Event id was not an integer");
			return;
		}
		controller.event(new ShowFutureEvent(eventId));
	}
}
