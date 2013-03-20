package com.actvc.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.actvc.client.common.MyConst;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TED;
import com.actvc.client.entities.TEL;
import com.actvc.client.entities.TET;
import com.actvc.client.entities.TEntity;
import com.actvc.client.entities.TPRH;
import com.actvc.client.entities.TR;
import com.actvc.client.entities.TRH;
import com.actvc.client.entities.TS;
import com.actvc.client.entities.TSe;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Index;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.code.twig.ObjectDatastore;

/**
 * <p>
 * Extracts data from the database. Can pass in entity name to only obtain that
 * data or no parameters return all data.
 * <p/>
 * <p>
 * e.g. http://localhost/getdata?entity=te
 * </p>
 * 
 * @author stephen
 * 
 */
public class GetDataServlet extends HttpServlet implements Servlet {

	private static final Logger log = Logger.getLogger(FileServlet.class
			.getName());

	private final ObjectDatastore datastore = Util.getDatastore();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {

			int entity = Integer.parseInt(req.getQueryString().substring(11));
			res.setContentType("text/plain");
			res.setHeader("Content-Disposition", "attachment; filename=entity"
					+ entity + ".txt");

			PrintWriter out = res.getWriter();

			QueryResultIterator<? extends TEntity> iterator = new QueryResultIterator<TEntity>() {

				@Override
				public Cursor getCursor() {
					return null;
				}

				@Override
				public boolean hasNext() {
					return false;
				}

				@Override
				public TEntity next() {
					return null;
				}

				@Override
				public void remove() {

				}

				@Override
				public List<Index> getIndexList() {
					// TODO Auto-generated method stub
					return null;
				}
			};

			if (entity == MyConst.EVENTIDX) {
				iterator = datastore.find().type(TE.class).now();
			} else if (entity == MyConst.EVENTDESCIDX) {
				iterator = datastore.find().type(TED.class).now();
			} else if (entity == MyConst.EVENTTYPEIDX) {
				iterator = datastore.find().type(TET.class).now();
			} else if (entity == MyConst.EVENTLOCATIONIDX) {
				iterator = datastore.find().type(TEL.class).now();
			} else if (entity == MyConst.RIDERIDX) {
				iterator = datastore.find().type(TR.class).now();
			} else if (entity == MyConst.RACEHISTIDX) {
				iterator = datastore.find().type(TRH.class)
						.addSort("date", SortDirection.DESCENDING).now();
				while (iterator.hasNext()) {
					out.println(iterator.next().toExportForm());
				}
				// Calendar cal = Calendar.getInstance();
				// cal.set(2000, 0, 1);
				// Date start = cal.getTime();
				// cal.set(2005, 11, 31);
				// Date end = cal.getTime();
				// log.info("start: " + start);
				// log.info("end: " + end);
				// while (iterator.hasNext()) {
				// TRH o = (TRH) iterator.next();
				// if (o.getDate().compareTo(start) >= 0
				// && o.getDate().compareTo(end) <= 0) {
				// out.println("start: " + start + " end: " + end + " ;"
				// + o.getDate());
				// }
				// }
				return;
			} else if (entity == MyConst.PENDINGRACEHISTORYIDX) {
				iterator = datastore.find().type(TPRH.class).now();
			} else if (entity == MyConst.SEASONIDX) {
				iterator = datastore.find().type(TSe.class).now();
			} else if (entity == MyConst.SYSTEMIDX) {
				iterator = datastore.find().type(TS.class).now();
			}

			while (iterator.hasNext()) {
				out.println(iterator.next().toExportForm());
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
