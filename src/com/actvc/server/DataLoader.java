package com.actvc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.actvc.client.common.MyConst;
import com.actvc.client.entities.TEntity;
import com.actvc.parse.ParseBase;
import com.actvc.parse.ParseEvent;
import com.actvc.parse.ParseEventDesc;
import com.actvc.parse.ParseEventLocation;
import com.actvc.parse.ParseEventType;
import com.actvc.parse.ParseMaintenance;
import com.actvc.parse.ParseMarshall;
import com.actvc.parse.ParseRaceHistory;
import com.actvc.parse.ParseRider;
import com.actvc.parse.ParseSeason;
import com.actvc.parse.ParseSystem;
import com.actvc.parse.ParseUser;
import com.google.code.twig.ObjectDatastore;

/**
 * Processes a file containing lines of data that is parsed then persisted to
 * the appropriate entity based on the type parameter included in the query
 * string of the {@link HttpServletRequest}.
 * 
 * @author stephen
 * 
 */
public class DataLoader extends HttpServlet {
	private static final long serialVersionUID = -5204714940052104411L;
	private static final Logger log = Logger.getLogger(FileServlet.class
			.getName());

	private final ObjectDatastore datastore = Util.getDatastore();

	private static Exception handleException(Exception e) {
		log.log(Level.SEVERE, e.getMessage(), e);
		return e;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletInputStream is = req.getInputStream();

		String type = req.getQueryString().substring(5);

		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {

			ParseBase parser = null;
			if (type.equals(Integer.toString(MyConst.EVENTIDX))) {
				parser = new ParseEvent();
			} else if (type.equals(Integer.toString(MyConst.EVENTDESCIDX))) {
				parser = new ParseEventDesc();
			} else if (type.equals(Integer.toString(MyConst.EVENTTYPEIDX))) {
				parser = new ParseEventType();
			} else if (type.equals(Integer.toString(MyConst.EVENTLOCATIONIDX))) {
				parser = new ParseEventLocation();
			} else if (type.equals(Integer.toString(MyConst.RIDERIDX))) {
				parser = new ParseRider();
			} else if (type.equals(Integer.toString(MyConst.RACEHISTIDX))) {
				parser = new ParseRaceHistory();
			} else if (type.equals(Integer.toString(MyConst.REGISTEREDUSERIDX))) {
				parser = new ParseUser();
			} else if (type.equals(Integer.toString(MyConst.MARSHALLIDX))) {
				parser = new ParseMarshall();
			} else if (type.equals(Integer.toString(MyConst.SEASONIDX))) {
				parser = new ParseSeason();
			} else if (type.equals(Integer.toString(MyConst.SYSTEMIDX))) {
				parser = new ParseSystem();
			} else if (type.equals(Integer.toString(MyConst.MAINTENANCEIDX))) {
				parser = new ParseMaintenance();
			}

			if (parser == null) {
				log.info("Could not process data. Invalid type given: " + type);
				throw new RuntimeException(
						"Could not process data. Invalid type given: " + type);
			}
			while ((line = br.readLine()) != null) {
				processLine(line, parser);
			}
		} catch (RuntimeException e) {
			System.out.println(line);
			handleException(e);
		} finally {
			br.close();
		}
	}

	public void processLine(String line, ParseBase parser) {
		String[] items = line.split("\t");
		TEntity t = parser.getEntity(items);
		if (t != null) {
			datastore.storeOrUpdate(t);
		}
	}

}
