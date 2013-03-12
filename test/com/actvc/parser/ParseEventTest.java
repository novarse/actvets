package com.actvc.parser;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.logging.Logger;

import org.junit.Test;

import com.actvc.client.entities.TE;
import com.actvc.parse.ParseEvent;
import com.actvc.server.FileServlet;

/**
 * Tests parsing the loading of data from files.
 * 
 * @author stephen
 * 
 */
public class ParseEventTest {

	private static final int SUMMER_ID = 1;

	private static final long WINTER_ID = 2L;

	private static final Logger log = Logger.getLogger(FileServlet.class
			.getName());

	private static final String TYPICAL_EVENT_LINE = "-468	21/8/1993 0:00:00	10	W	17	164	5";
	private static final String NO_DIRECTOR_LINE_AND_SUMMER = "-468	21/8/1993 0:00:00		S	17	164	5";
	private static final String MISSING_SEASON = "-468	21/8/1993 0:00:00	10		17	164	5";

	/**
	 * Tests that an {@link TE} is correctly created.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testGetEvent() throws ParseException {
		String[] items = TYPICAL_EVENT_LINE.split("\t");
		Long.parseLong(items[0]);

		ParseEvent parser = new ParseEvent();
		TE event = parser.getEntity(items);
		assertEquals(-468L, event.getId().longValue());
		assertEquals("Sat Aug 21 00:00:00 EST 1993", event.getDate().toString());
		assertEquals(10L, event.getDirectorId().longValue());
		assertEquals(WINTER_ID, event.getSeasonId().longValue());
		assertEquals(17L, event.getLocationId().longValue());
		assertEquals(164L, event.getEventDescriptionId().longValue());
		assertEquals(5L, event.getEventTypeId().longValue());
	}

	/**
	 * Tests that directorId is set to null for a blank director.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testNoDirector() throws ParseException {
		String[] items = NO_DIRECTOR_LINE_AND_SUMMER.split("\t");
		ParseEvent parser = new ParseEvent();
		TE event = parser.getEntity(items);
		assertEquals(null, event.getDirectorId());
		assertEquals(SUMMER_ID, event.getSeasonId().longValue());
	}

	/**
	 * Tests an exception is given if the season is not a "W" or "S"
	 * 
	 * @throws ParseException
	 */
	@Test(expected = RuntimeException.class)
	public void testInvalidSeason() throws ParseException {
		String[] items = MISSING_SEASON.split("\t");
		ParseEvent parser = new ParseEvent();
		parser.getEntity(items);
	}
}
