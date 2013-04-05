package com.actvc.server;

import java.util.Date;
import java.util.logging.Logger;

import com.actvc.client.ContentCalendar;
import com.actvc.client.common.MyConst;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TED;
import com.actvc.client.entities.TEDTO;
import com.actvc.client.entities.TEL;
import com.actvc.client.entities.TET;
import com.actvc.client.entities.TR;
import com.actvc.client.entities.TRH;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.code.twig.FindCommand.RootFindCommand;
import com.google.code.twig.ObjectDatastore;
import com.google.gwt.user.datepicker.client.CalendarUtil;

public class GetTEDTO {
	private final ObjectDatastore datastore = Util.getDatastore();
	private static final Logger logger = Logger.getLogger(GetTEDTO.class
			.getName());

	public synchronized TEDTO loadEvents(boolean isFuture, boolean smallList) {
		return isFuture ? loadFutureEvents(smallList)
				: loadHistoricEvents(smallList);
	}

	public TEDTO loadFutureEvents(boolean smallList) {
		TEDTO result = new TEDTO();

		Date fromDate = new Date();

		QueryResultIterator<TE> iterator = datastore
				.find()
				.type(TE.class)
				.addSort("date")
				.addFilter("date", FilterOperator.GREATER_THAN_OR_EQUAL,
						fromDate).fetchMaximum(10).now();

		int i = 1;
		while (iterator.hasNext()) {
			if (i > (smallList ? MyConst.MAXEVENTSMALL : MyConst.MAXEVENTLARGE)) {
				break;
			}
			if (i++ >= (smallList ? 1 : MyConst.MAXEVENTSMALL + 1)) {

				addRelatedFields(iterator.next(), result);

			} else {
				iterator.next();
			}
		}

		return result;
	}

	/**
	 * This will return event id, date, description (20 chars) in a
	 * {@link TEDTO}. Used by {@link ContentCalendar} for displaying events in a
	 * calendar.
	 * 
	 * @param months
	 *            - return events from now to end of now + "months"
	 * @return TEDTO
	 */
	public TEDTO loadFutureEventsByMonths(Integer months) {
		TEDTO result = new TEDTO();

		Date fromDate = new Date();
		Date toDate = getDateMonthsFromNow(new Date(), months);

		QueryResultIterator<TE> iterator = datastore
				.find()
				.type(TE.class)
				.addSort("date")
				.addFilter("date", FilterOperator.GREATER_THAN_OR_EQUAL,
						fromDate)
				.addFilter("date", FilterOperator.LESS_THAN_OR_EQUAL, toDate)
				.now();

		while (iterator.hasNext()) {
			addRelatedFieldsForCalendar(iterator.next(), result);
		}

		return result;
	}

	private void addRelatedFieldsForCalendar(TE event, TEDTO result) {
		result.getEventMap().put(event.getId(), event);

		if (!result.getDescriptionMap().containsKey(
				event.getEventDescriptionId())) {
			result.getDescriptionMap().put(event.getEventDescriptionId(),
					datastore.load(TED.class, event.getEventDescriptionId()));
		}
	}

	private Date getDateMonthsFromNow(Date date, Integer months) {
		CalendarUtil.addMonthsToDate(date, months);
		return date;
	}

	public TEDTO loadHistoricEvents(boolean smallList) {
		TEDTO result = new TEDTO();

		Date fromDate = new Date();

		QueryResultIterator<TE> iterator = datastore.find().type(TE.class)
				.addSort("date", SortDirection.DESCENDING)
				.addFilter("date", FilterOperator.LESS_THAN, fromDate)
				.fetchMaximum(10).now();

		int i = 1;
		int max = (smallList ? MyConst.MAXEVENTSMALL : MyConst.MAXEVENTLARGE);
		while (iterator.hasNext()) {
			if (i > max) {
				break;
			}
			if (i++ >= (smallList ? 1 : MyConst.MAXEVENTSMALL + 1)) {

				addRelatedFields(iterator.next(), result);

			} else {
				iterator.next();
			}
		}

		return result;
	}

	public TEDTO getEventDetails(long eventId) {
		TEDTO result = new TEDTO();

		TE event = datastore.load(TE.class, eventId);
		if (event == null) {
			logger.info("Could not find event with id = " + eventId);
			return null;
		}
		addRelatedFields(event, result);

		return result;
	}

	public TEDTO getRaceEventResults(long eventId) {
		TEDTO result = new TEDTO();

		addRelatedFields(datastore.load(TE.class, eventId), result);

		RootFindCommand<TRH> root = datastore.find().type(TRH.class)
				.addFilter("eventId", FilterOperator.EQUAL, eventId);

		QueryResultIterator<TRH> rhIterator;
		if ((Long) result.getTypeMap().keySet().toArray()[0] == MyConst
				.getHandicapId()) {
			rhIterator = root.addSort("overTheLine").now();
		} else {
			rhIterator = root.addSort("raceGrade").addSort("place").now();
		}
		while (rhIterator.hasNext()) {
			TRH rh = rhIterator.next();
			result.getRaceHistoryMap().put(rh.getId(), rh);
			result.getRiderMap().put(rh.getRiderId(),
					datastore.load(TR.class, rh.getRiderId()));
		}

		return result;
	}

	private void addRelatedFields(TE event, TEDTO result) {

		Long eventDescriptionId = event.getEventDescriptionId();
		if (!result.getDescriptionMap().containsKey(eventDescriptionId)) {
			TED desc = datastore.load(TED.class, eventDescriptionId);
			if (desc == null) {
				logger.warning("Could not find description: "
						+ eventDescriptionId);
				return;
			}
			result.getDescriptionMap().put(eventDescriptionId, desc);
		}

		Long eventTypeId = event.getEventTypeId();
		if (!result.getTypeMap().containsKey(eventTypeId)) {
			TET eventType = datastore.load(TET.class, eventTypeId);
			if (eventType == null) {
				logger.warning("Event Type not found: " + eventTypeId);
				return;
			}
			result.getTypeMap().put(eventTypeId, eventType);
		}

		Long locationId = event.getLocationId();
		if (!result.getLocationMap().containsKey(locationId)) {
			TEL location = datastore.load(TEL.class, locationId);
			if (location == null) {
				logger.warning("Could not find location: " + locationId);
				return;
			}
			result.getLocationMap().put(locationId, location);
		}

		Long directorId = event.getDirectorId();
		if (directorId != null
				&& !result.getDirectorMap().containsKey(directorId)) {
			TR director = datastore.load(TR.class, directorId);
			if (director == null) {
				logger.warning("Could not find director: " + directorId);
				return;
			}
			result.getDirectorMap().put(directorId, director);
		}

		result.getEventMap().put(event.getId(), event);
	}
}
