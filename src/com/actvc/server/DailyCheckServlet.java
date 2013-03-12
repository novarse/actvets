package com.actvc.server;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.actvc.client.entities.TE;
import com.actvc.client.entities.TEL;
import com.actvc.client.entities.TET;
import com.actvc.client.entities.TR;
import com.actvc.client.entities.TS;
import com.actvc.client.entities.TSDao;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.code.twig.ObjectDatastore;

public class DailyCheckServlet extends HttpServlet implements Servlet {
	private static final String LINK_TAG = "@@@";
	private static final String RACEDESC_TAG = "###";
	private static final String DOUBLE_LINEFEED = "\n\n";
	// private static final String EMAIL_FROM = "stephenmwills@gmail.com";
	// private static final String EMAIL_FROM = "allan.bontjer@gmail.com";
	// private static final String EMAIL_TO = EMAIL_FROM;
	private static final long serialVersionUID = -4365276515360622514L;
	private static final Logger log = Logger.getLogger(DailyCheckServlet.class
			.getName());
	private final ObjectDatastore datastore = Util.getDatastore();

	private final GetDataDAO getDataDao = new GetDataDAO();

	private Exception handleException(Exception e) {
		log.log(Level.SEVERE, e.getMessage(), e);
		return e;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/html");
		PrintWriter out = null;
		try {
			TSDao systemDao = getDataDao.getTSDao(null);
			if (systemDao.getSystem() == null) {
				log.warning("TS not initialiized");
				return;
			}
			out = res.getWriter();

			checkEmailDirector(req.getServerName(), systemDao);

			if (systemDao.getSystem().isResetFinancialStatus()) {
				checkResetFinancials(systemDao.getSystem());
			}

		} catch (Exception e) {
			out.println(e.getMessage());
			handleException(e);
		}
	}

	private void checkResetFinancials(TS system) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, -system.getServerTimeOffset());
		int m = c.get(Calendar.MONTH);
		int d = c.get(Calendar.DATE);

		if (d == system.getResetFinancialDay()
				&& m == system.getResetFinancialMonth()) {
			AppServiceImpl.resetFinancials();
		}

	}

	private void checkEmailDirector(String serverName, TSDao systemDao) {
		String directorMessageTemplate = systemDao.getDirectorMessage();
		if (directorMessageTemplate == null) {
			throw new RuntimeException(
					"The director reminder message has not been created");
		}
		TS system = systemDao.getSystem();
		Calendar c = Calendar.getInstance();
		c.clear(Calendar.MILLISECOND);
		c.clear(Calendar.SECOND);
		c.clear(Calendar.MINUTE);
		c.set(Calendar.HOUR_OF_DAY, 0);

		c.add(Calendar.DATE, system.getDirectorReminderDaysBefore());
		c.add(Calendar.HOUR_OF_DAY, -system.getServerTimeOffset());
		Date dayBefore = c.getTime();

		c.add(Calendar.DATE, 1);
		Date dayLater = c.getTime();

		QueryResultIterator<TE> iterator = datastore
				.find()
				.type(TE.class)
				.addFilter("date", FilterOperator.GREATER_THAN_OR_EQUAL,
						dayBefore)
				.addFilter("date", FilterOperator.LESS_THAN, dayLater)
				.addSort("date").now();

		while (iterator.hasNext()) {
			TE event = iterator.next();
			if (event.getDirectorId() != null) {
				TR r = datastore.load(TR.class, event.getDirectorId());
				TET et = datastore.load(TET.class, event.getEventTypeId());
				TEL el = datastore.load(TEL.class, event.getLocationId());

				String message = directorMessageTemplate.replace(RACEDESC_TAG,
						getRaceDetails(event, et, el)).replace(LINK_TAG,
						getShortLink(serverName, event));
				log.info(message);
				emailMessage(message, r, system);

			}
		}
	}

	private String getShortLink(String serverName, TE event) {
		return getDataDao.getShortUrl(event, serverName);
	}

	private String getRaceDetails(TE event, TET et, TEL el) {
		log.info("Get date for director message - event.getDate() "
				+ event.getDate()); // smw
		log.info("Get date for director message - getDataDao.getLocalDateStr(event.getDate()) "
				+ getDataDao.getLocalDateStr(event.getDate())); // smw
		return getDataDao.getLocalDateStr(event.getDate()) + ", "
				+ el.getLocation() + ", " + et.getDescription();
	}

	private void emailMessage(String message, TR r, TS s) {
		AppServiceImpl.sendEmail(s.getMainEmail(),
				"ACT Veterans Cycling Club Website", r.getEmail(),
				r.getFirstName() + " " + r.getLastName(),
				"Upcoming race event", message);
	}

}
