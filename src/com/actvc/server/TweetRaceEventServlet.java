package com.actvc.server;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.actvc.client.entities.TE;
import com.actvc.client.entities.TS;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.code.twig.ObjectDatastore;

/**
 * <p>
 * Checks for races on the next day and sends a tweet containing race
 * description and a link to website to display event details.
 * </p>
 * <p>
 * Uses {@link TS} variable returnBitlyUrl as the return host if this has been
 * set.
 * </p>
 * 
 * @author stephen
 * 
 */
public class TweetRaceEventServlet extends HttpServlet implements Servlet {
	private static final int ONEDAY = 24;
	private static final int EARLIEST_START = 6;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(TweetRaceEventServlet.class.getName());
	private final ObjectDatastore datastore = Util.getDatastore();

	private final GetDataDAO getDataDao = new GetDataDAO();
	private final AppServiceImpl service = new AppServiceImpl();

	private Exception handleException(Exception e) {
		log.log(Level.SEVERE, e.getMessage(), e);
		return e;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		try {
			TS system = getDataDao.getTS();
			if (system == null) {
				log.warning("TS not initialiized");
				return;
			}
			Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR_OF_DAY, system.getServerTimeOffset());
			if (c.get(Calendar.HOUR_OF_DAY) != system
					.getHourToCheckForNextRace()) {
				return;
			}

			if (!system.hasBitlyFields()) {
				log.info("bitly settings are incomplete.");
				return;
			}
			c = Calendar.getInstance();
			c.clear(Calendar.MILLISECOND);
			c.clear(Calendar.SECOND);
			c.clear(Calendar.MINUTE);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.add(Calendar.HOUR_OF_DAY,
					(EARLIEST_START + ONEDAY - system.getServerTimeOffset()));
			Date earliestStart = c.getTime();

			c.add(Calendar.HOUR_OF_DAY, 15);
			Date latestStart = c.getTime();
			QueryResultIterator<TE> iterator = datastore
					.find()
					.type(TE.class)
					.addFilter("date", FilterOperator.GREATER_THAN_OR_EQUAL,
							earliestStart)
					.addFilter("date", FilterOperator.LESS_THAN, latestStart)
					.addSort("date").now();

			while (iterator.hasNext()) {
				String message = getDataDao.getTweetMsg(iterator.next(),
						req.getServerName());

				service.submitTweet(message);
			}
		} catch (Exception e) {
			handleException(e);
		}

	}
}
