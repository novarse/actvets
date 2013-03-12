package com.actvc.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.actvc.client.common.MyConst;
import com.actvc.client.common.MyUtils;
import com.actvc.client.entities.Person;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TED;
import com.actvc.client.entities.TEL;
import com.actvc.client.entities.TET;
import com.actvc.client.entities.TGCR;
import com.actvc.client.entities.TPRH;
import com.actvc.client.entities.TR;
import com.actvc.client.entities.TRDTO;
import com.actvc.client.entities.TRH;
import com.actvc.client.entities.TRP;
import com.actvc.client.entities.TS;
import com.actvc.client.entities.TSDao;
import com.actvc.client.entities.TSe;
import com.actvc.client.entities.TText;
import com.actvc.client.entities.TU;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.code.twig.FindCommand.RootFindCommand;
import com.google.code.twig.ObjectDatastore;
import com.rosaloves.bitlyj.Bitly;
import com.rosaloves.bitlyj.Bitly.Provider;

public class GetDataDAO {
	private final ObjectDatastore datastore = Util.getDatastore();

	private static final Logger log = Logger.getLogger(GetDataDAO.class
			.getName());

	public <T> T getById(Class<T> cls, Long id) {
		T result = null;
		result = datastore.load(cls, id);
		return result;
	}

	public <T> T getById(Class<T> cls, String id) {
		T result = null;
		result = datastore.load(cls, id);
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<TRH> getRaceHistoryByEventId(long eventId) {
		List<TRH> result = null;
		TE e = datastore.load(TE.class, eventId);
		result = (List<TRH>) datastore.find().type(TRH.class)
				.addFilter("event", FilterOperator.EQUAL, e).now();
		for (TRH rh : result) {
		}
		return result;
	}

	public Date getEventDateById(Long eventId) {
		TE e = datastore.load(TE.class, eventId);
		return e.getDate();
	}

	public TRH getRaceHistory(Long eventId, Long riderId) {
		return datastore.load(TRH.class,
				eventId.toString() + "_" + riderId.toString());
	}

	public TU getUser(String email) {
		return datastore.load(TU.class, email.toLowerCase());
	}

	public List<TE> loadEventDatesAndIds(boolean showHistoricData,
			boolean showAllFutureEvents) {
		List<TE> result = new ArrayList<TE>();
		RootFindCommand<TE> root = datastore.find().type(TE.class)
				.addSort("date", SortDirection.DESCENDING);

		if (!showHistoricData) {
			Calendar notAllHistoric = Calendar.getInstance();
			notAllHistoric.add(Calendar.YEAR, -1);
			root.addFilter("date", FilterOperator.GREATER_THAN_OR_EQUAL,
					notAllHistoric.getTime());
		}
		if (!showAllFutureEvents) {
			Calendar notAllFuture = Calendar.getInstance();
			notAllFuture.add(Calendar.MONTH, +2);
			root.addFilter("date", FilterOperator.LESS_THAN_OR_EQUAL,
					notAllFuture.getTime());
		}
		QueryResultIterator<TE> iterator = root.now();
		while (iterator.hasNext()) {
			TE e = iterator.next();
			result.add(e);
		}
		return result;

	}

	// public List<String> loadEventDateshAndIds(boolean showHistoricData,
	// boolean showAllFutureEvents) {
	// List<String> result = new ArrayList<String>();
	// RootFindCommand<TE> root = datastore.find().type(TE.class)
	// .addSort("date", SortDirection.DESCENDING);
	//
	// if (!showHistoricData) {
	// Calendar notAllHistoric = Calendar.getInstance();
	// notAllHistoric.add(Calendar.YEAR, -1);
	// root.addFilter("date", FilterOperator.GREATER_THAN_OR_EQUAL,
	// notAllHistoric.getTime());
	// }
	// if (!showAllFutureEvents) {
	// Calendar notAllFuture = Calendar.getInstance();
	// notAllFuture.add(Calendar.MONTH, +2);
	// root.addFilter("date", FilterOperator.LESS_THAN_OR_EQUAL,
	// notAllFuture.getTime());
	// }
	// QueryResultIterator<TE> iterator = root.now();
	// while (iterator.hasNext()) {
	// TE e = iterator.next();
	// result.add(MyUtils.getDateStr(e.getDate()) + ";" + e.getId());
	// }
	// return result;
	//
	// }

	public List<String> loadRiderNamesAndIds(Boolean showOnlyActives) {
		List<String> result = new ArrayList<String>();
		RootFindCommand<TR> root = datastore.find().type(TR.class)
				.addSort("uppercaseLastName").addSort("uppercaseFirstName");
		if (showOnlyActives) {
			root.addFilter("isActive", FilterOperator.EQUAL, showOnlyActives);
		}
		QueryResultIterator<TR> iterator = root.now();
		while (iterator.hasNext()) {
			Person r = iterator.next();
			result.add(r.getLastName() + ", " + r.getFirstName() + ";"
					+ r.getId());
		}
		return result;
	}

	public List<String> loadRiderNumbersAndIds() {
		List<String> result = new ArrayList<String>();
		QueryResultIterator<TR> iterator = datastore.find().type(TR.class)
				.addFilter("number", FilterOperator.NOT_EQUAL, -1)
				.addFilter("number", FilterOperator.NOT_EQUAL, 0)
				.addSort("number").addSort("lastName").now();
		while (iterator.hasNext()) {
			Person r = iterator.next();
			result.add(r.getNumber() + ";" + r.getId() + ";" + r.getLastName());
		}
		return result;
	}

	public List<TE> loadRecentEvents(boolean isSuperUser) {
		List<TE> result = new ArrayList<TE>();
		Calendar calendar = Calendar.getInstance();
		Date toDate = calendar.getTime();
		calendar.add(Calendar.MONTH, -3);
		Date fromDate = calendar.getTime();
		QueryResultIterator<TE> iterator;
		if (isSuperUser) {
			iterator = datastore
					.find()
					.type(TE.class)
					.addFilter("date", FilterOperator.LESS_THAN_OR_EQUAL,
							toDate).addSort("date", SortDirection.DESCENDING)
					.now();
		} else {
			iterator = datastore
					.find()
					.type(TE.class)
					.addFilter("date", FilterOperator.LESS_THAN_OR_EQUAL,
							toDate)
					.addFilter("date", FilterOperator.GREATER_THAN_OR_EQUAL,
							fromDate).addSort("date", SortDirection.DESCENDING)
					.now();
		}
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	public TRDTO getRiderHistoryById(Long riderId) {
		TRDTO trdto = new TRDTO();
		List<TRH> rhList = new ArrayList<TRH>();
		trdto.setRider(datastore.load(TR.class, riderId));
		QueryResultIterator<TRH> rhIterator = datastore.find().type(TRH.class)
				.addFilter("riderId", FilterOperator.EQUAL, riderId)
				.addSort("date", SortDirection.DESCENDING).now();
		int eventCount = 0;
		while (rhIterator.hasNext()) {
			eventCount += 1;
			rhList.add(rhIterator.next());
		}
		trdto.setRaceHistoryList(rhList);
		trdto.setEventCount(eventCount);

		return trdto;
	}

	public List<TET> loadEventTypes() {
		List<TET> result = new ArrayList<TET>();
		QueryResultIterator<TET> iterator = datastore.find().type(TET.class)
				.addSort("description").now();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	public List<TED> loadEventDesc() {
		List<TED> result = new ArrayList<TED>();
		QueryResultIterator<TED> iterator = datastore.find().type(TED.class)
				.addSort("description").now();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	public List<TED> loadActiveEventDesc() {
		List<TED> result = new ArrayList<TED>();
		QueryResultIterator<TED> iterator = datastore.find().type(TED.class)
				.addSort("description")
				.addFilter("isActive", FilterOperator.EQUAL, true).now();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	public List<TU> getUsers(Class<TU> class1, String string) {
		List<TU> result = new ArrayList<TU>();
		QueryResultIterator<TU> iterator = datastore.find().type(TU.class)
				.addSort("name").now();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	public List<TR> loadRidersForEvent(Long eventId) {
		List<TR> result = new ArrayList<TR>();
		QueryResultIterator<TRH> iterator = datastore.find().type(TRH.class)
				.addFilter("eventId", FilterOperator.EQUAL, eventId).now();
		while (iterator.hasNext()) {
			result.add(datastore.load(TR.class, iterator.next().getRiderId()));
		}
		Collections.sort(result, new Comparator<TR>() {

			@Override
			public int compare(TR t1, TR t2) {
				return ((t1.getLastName() + ", " + t1.getFirstName())
						.toUpperCase()).compareTo(((t2.getLastName() + ", " + t2
						.getFirstName())).toUpperCase());
			}
		});
		return result;
	}

	public List<TPRH> getPendingRiders() {
		List<TPRH> result = new ArrayList<TPRH>();
		QueryResultIterator<TPRH> iterator = datastore.find().type(TPRH.class)
				.addSort("uppercaseLastName").addSort("uppercaseFirstName")
				.now();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	public Integer getNextNumber() {
		Integer result = null;
		QueryResultIterator<TR> iterator = datastore.find().type(TR.class)
				.addSort("number", SortDirection.DESCENDING).now();
		while (iterator.hasNext()) {
			TR r = iterator.next();
			result = r.getNumber() + 1;
			break;
		}
		return result;
	}

	public TR getRiderByNumber(int checkedRiderNumber) {
		TR result = null;
		QueryResultIterator<TR> iterator = datastore.find().type(TR.class)
				.addFilter("number", FilterOperator.EQUAL, checkedRiderNumber)
				// .addSort("id", SortDirection.DESCENDING)
				.now();
		while (iterator.hasNext()) {
			result = iterator.next();
			break;
		}
		return result;
	}

	public List<TR> getRidersByNumber(int checkedRiderNumber) {
		List<TR> result = new ArrayList<TR>();
		QueryResultIterator<TR> iterator = datastore.find().type(TR.class)
				.addFilter("number", FilterOperator.EQUAL, checkedRiderNumber)
				.now();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	public List<TGCR> loadGradeChangeRequests() {
		List<TGCR> result = new ArrayList<TGCR>();
		QueryResultIterator<TGCR> iterator = datastore.find().type(TGCR.class)
				.addSort("lastName").addSort("firstName").now();
		if (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	public List<String> loadLocations() {
		List<String> result = new ArrayList<String>();
		QueryResultIterator<TEL> iterator = datastore.find().type(TEL.class)
				.addSort("location").now();
		while (iterator.hasNext()) {
			TEL el = iterator.next();
			result.add(el.getLocation() + ";" + el.getId());
		}
		return result;
	}

	public TEL getEditLocation(Long locationId) {
		return datastore.load(TEL.class, locationId);
	}

	public List<String> loadDirectors() {
		List<String> result = new ArrayList<String>();
		QueryResultIterator<TR> iterator = datastore.find().type(TR.class)
				.addFilter("isDirector", FilterOperator.EQUAL, true)
				.addFilter("isActive", FilterOperator.EQUAL, true)
				.addSort("uppercaseLastName").addSort("uppercaseFirstName")
				.now();
		while (iterator.hasNext()) {
			TR r = iterator.next();
			result.add(r.getLastName() + ", " + r.getFirstName() + ";"
					+ r.getId());
		}
		return result;
	}

	public List<String> loadRiders() {
		List<String> result = new ArrayList<String>();
		QueryResultIterator<TR> iterator = datastore.find().type(TR.class)
				.addSort("uppercaseLastName").addSort("uppercaseFirstName")
				.now();
		while (iterator.hasNext()) {
			TR r = iterator.next();
			result.add(r.getLastName() + ", " + r.getFirstName() + ";"
					+ r.getId());
		}
		return result;
	}

	public String getEventDescByEventId(Long eventId) {
		TE e = datastore.load(TE.class, eventId);
		TEL el = datastore.load(TEL.class, e.getLocationId());
		TET et = datastore.load(TET.class, e.getEventTypeId());
		return MyUtils.getDateStr(e.getDate()) + "\t" + el.getLocation() + ","
				+ et.getDescription();
	}

	public String getEventDescNoDate(Long eventId) {
		TE e = datastore.load(TE.class, eventId);
		TEL el = datastore.load(TEL.class, e.getLocationId());
		TET et = datastore.load(TET.class, e.getEventTypeId());
		return el.getLocation() + "," + et.getDescription();
	}

	public String getLocalDateStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE d/M/yyyy, h:mmaa");
		TS system = getTS();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, system.getServerTimeOffset());
		return sdf.format(c.getTime());
	}

	public TS getTS() {
		TS system = datastore.load(TS.class, MyConst.SINGLETONID);
		if (system == null) {
			system = new TS(MyConst.SINGLETONID);
			datastore.store(system);
			return system;
		} else {
			return system;
		}
	}

	public List<TSe> loadSeasons() {
		List<TSe> result = new ArrayList<TSe>();
		QueryResultIterator<TSe> iterator = datastore.find().type(TSe.class)
				.addSort("listOrder").now();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	/**
	 * Retrieve a list of {@link TRP} containing points and rider details for a
	 * given year and season.
	 * 
	 * @param year
	 * @param seasonId
	 * @return
	 */
	public Map<String, TRP> getRiderPoints(Long seasonId) {
		Map<String, TRP> result = new TreeMap<String, TRP>();

		QueryResultIterator<TE> iterator = datastore.find().type(TE.class)
				.addFilter("seasonId", FilterOperator.EQUAL, seasonId)
				.addSort("date").now();
		while (iterator.hasNext()) {
			TE event = iterator.next();
			getPointsRH(result, event.getId());
		}
		return result;
	}

	private void getPointsRH(Map<String, TRP> rpMap, Long eventId) {
		QueryResultIterator<TRH> iterator = datastore.find().type(TRH.class)
				.addFilter("eventId", FilterOperator.EQUAL, eventId).now();
		while (iterator.hasNext()) {
			TRH rh = iterator.next();
			TR r = datastore.load(TR.class, rh.getRiderId());
			if (r == null) {
				continue;
			}
			String nameStr = r.getLastName() + ", " + r.getFirstName();
			String nameId = nameStr + "&" + r.getId();
			TRP rp = rpMap.get(nameId);
			if (rp == null) {
				rpMap.put(
						nameId,
						new TRP(r.getId(), nameStr, r.getNumber(), rh
								.getPoints()));
			} else {
				rpMap.put(nameId, rp.add(rh.getPoints()));
			}
		}
	}

	public String getTweetMsg(TE event, String postedServerName) {
		return getTweetDescription(event) + " "
				+ getShortUrl(event, postedServerName);
	}

	public String getShortUrl(TE event, String postedServerName) {
		TS system = getTS();
		Provider provider = Bitly.as(system.getBitlyUserName(),
				system.getBitlyAPIKey());

		String serverName = system.getReturnBitlyUrl() == null
				|| system.getReturnBitlyUrl().isEmpty() ? postedServerName
				: system.getReturnBitlyUrl();

		String shortUrl = provider.call(
				Bitly.shorten("http://" + serverName + "/#eventid="
						+ Long.toString(event.getId()))).getShortUrl();
		return shortUrl;
	}

	private String getTweetDescription(TE event) {

		TET et = datastore.load(TET.class, event.getEventTypeId());
		TEL el = datastore.load(TEL.class, event.getLocationId());
		String result = null;
		result = et.getDescription() + " at " + el.getLocation() + ", "
				+ getLocalDateStr(event.getDate());

		return result;

	}

	public String getTText(Long directorReminderMessageId) {
		TText tT = datastore.load(TText.class, directorReminderMessageId);
		return (tT == null) ? null : tT.getText().getValue();
	}

	public TSDao getTSDao(String eventName) {
		TSDao tsDao = new TSDao();
		tsDao.setDaoEvent(eventName);
		tsDao.setSystem(getTS());
		if (tsDao.getSystem().getDirectorReminderMessageId() != null) {
			tsDao.setDirectorMessage(getTText(tsDao.getSystem()
					.getDirectorReminderMessageId()));
		}
		return tsDao;
	}
}
