var CalendarItemDate = new Array();

var CalendarItemTime = new Array();

var CalendarItemDesc = new Array();

var CalendarItemEventId = new Array();

var CalendarItemCount = 0;

// Get Current Date
CurrentDate = new Date();
CurrentDate.setHours(4);

// Get the first day of the current week
StartofCurrentWeek = new Date();
StartofCurrentWeek.setTime(CurrentDate.getTime() - CurrentDate.getDay()
		* 86400000);

// returns the day count for the last day of the month 6 months from the
// starting day
// such that there is no more than 6 months displayed.
function calcEndDay(startDayDate, startDay, months) {
	var startMonth = startDayDate.getMonth();
	var startYear = startDayDate.getYear() + 1900;
	var endDay = startDay + daysInMonth(startYear, startMonth)
			- startDayDate.getDate();
	for ( var i = 1; i < months; i++) {
		endDay += daysInMonth(startYear, startMonth + i);
	}
	return endDay;
}

// months - Jan = 0
function daysInMonth(iYear, iMonth) {
	return 32 - new Date(iYear, iMonth, 32).getDate();
}

function createPublicHolidays(months) {
	var now = new Date();
	var startMonth = now.getMonth();
	var startYear = now.getFullYear();
	var checkMonth;

	if ((checkMonth = 0) < startMonth) {
		checkMonth += 12;
	}
	if ((checkMonth - startMonth) < months) {
		addCalendarItem("01 Jan " + startYear, "0", "New Years Day", 0);
		addCalendarItem("26 Jan " + (2012 + Math.floor(checkMonth / 12)), "0",
				"Australia Day", 0);
	}
	if ((checkMonth = 2) < startMonth) {
		checkMonth += 12;
	}
	if ((checkMonth - startMonth) < months) {
		addCalendarItem("12 Mar " + (2012 + Math.floor(checkMonth / 12)), "0",
				"Canberra Day", 0);
	}
	if ((checkMonth = 3) < startMonth) {
		checkMonth += 12;
	}
	if ((checkMonth - startMonth) < months) {
		addCalendarItem("06 Apr " + (2012 + Math.floor(checkMonth / 12)), "0",
				"Good Friday", 0);
		addCalendarItem("07 Apr " + (2012 + Math.floor(checkMonth / 12)), "0",
				"Easter Saturday", 0);
		addCalendarItem("08 Apr " + (2012 + Math.floor(checkMonth / 12)), "0",
				"Easter Sunday", 0);
		addCalendarItem("09 Apr " + (2012 + Math.floor(checkMonth / 12)), "0",
				"Easter Monday", 0);
		addCalendarItem("25 Apr " + (2012 + Math.floor(checkMonth / 12)), "0",
				"Anzac Day", 0);
	}
	if ((checkMonth = 5) < startMonth) {
		checkMonth += 12;
	}
	if ((checkMonth - startMonth) < months) {
		addCalendarItem("11 Jun " + (2012 + Math.floor(checkMonth / 12)), "0",
				"Queens Birthday", 0);
	}
	if ((checkMonth = 9) < startMonth) {
		checkMonth += 12;
	}
	if ((checkMonth - startMonth) < months) {
		addCalendarItem("01 Oct " + (2012 + Math.floor(checkMonth / 12)), "0",
				"Labour Day", 0);
		addCalendarItem("08 Oct " + (2012 + Math.floor(checkMonth / 12)), "0",
				"Famly Commty Day", 0);
	}
	if ((checkMonth = 11) < startMonth) {
		checkMonth += 12;
	}
	if ((checkMonth - startMonth) < months) {
		addCalendarItem("25 Dec " + (2012 + Math.floor(checkMonth / 12)), "0",
				"Christmas Day", 0);
		addCalendarItem("26 Dec " + (2012 + Math.floor(checkMonth / 12)), "0",
				"Boxing Day", 0);
	}
}

function addCalendarItem(ItemDate, ItemTime, ItemDesc, eventId)

{
	tempDate = new Date(ItemDate);

	tempDate.setHours(4);

	if (tempDate > StartofCurrentWeek)

	{
		CalendarItemDate[CalendarItemCount] = tempDate;

		CalendarItemTime[CalendarItemCount] = ItemTime;

		CalendarItemDesc[CalendarItemCount] = ItemDesc;

		CalendarItemEventId[CalendarItemCount] = eventId;

		CalendarItemCount++;

	}

}
