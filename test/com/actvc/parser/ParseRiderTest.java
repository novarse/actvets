package com.actvc.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.text.ParseException;
import java.util.logging.Logger;

import org.junit.Test;

import com.actvc.client.Gender;
import com.actvc.client.entities.TR;
import com.actvc.parse.ParseRider;
import com.actvc.server.FileServlet;

/**
 * Tests parsing the loading of data from rider files.
 * 
 * @author stephen
 * 
 */
public class ParseRiderTest {

	private static final Logger log = Logger.getLogger(FileServlet.class
			.getName());

	private final String TYPICAL_RIDER_LINE = "1	Bentley	Michael	31 Bingley Crescent	Fraser	ACT	2615	02 6258 0607	0417 216 664	"
			+ "20/12/1949 0:00:00	M	Michael.Bentley@anu.edu.au	0	Kerr-Ann Smith	0414 874 652		54	B";
	private static final String MISSING_GENDER = "1	Bentley	Michael	31 Bingley Crescent	Fraser	ACT	2615	02 6258 0607	0417 216 664	"
			+ "20/12/1949 0:00:00		Michael.Bentley@anu.edu.au	0	Kerr-Ann Smith	0414 874 652		54	B";
	private static final String INVALID_GENDER = "1	Bentley	Michael	31 Bingley Crescent	Fraser	ACT	2615	02 6258 0607	0417 216 664	"
			+ "20/12/1949 0:00:00	S	Michael.Bentley@anu.edu.au	0	Kerr-Ann Smith	0414 874 652		54	B";
	private static final String LOWERCASE_GENDER = "1	Bentley	Michael	31 Bingley Crescent	Fraser	ACT	2615	02 6258 0607	0417 216 664	"
			+ "20/12/1949 0:00:00	f	Michael.Bentley@anu.edu.au	0	Kerr-Ann Smith	0414 874 652		54	B";
	private final String MISSING_NUMBER = "1	Bentley	Michael	31 Bingley Crescent	Fraser	ACT	2615	02 6258 0607	0417 216 664	"
			+ "20/12/1949 0:00:00	M	Michael.Bentley@anu.edu.au	0	Kerr-Ann Smith	0414 874 652			B";

	/**
	 * Tests that an {@link TR} is correctly created for a typical good case.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testGetRider() throws ParseException {
		ParseRider parser = new ParseRider();
		TR rider = parser.getEntity(TYPICAL_RIDER_LINE.split("\t"));
		assertEquals(1L, rider.getId().longValue());
		assertEquals("Bentley", rider.getLastName());
		assertEquals("Michael", rider.getFirstName());
		assertEquals(true, rider.isActive());
		assertEquals("31 Bingley Crescent", rider.getStreet());
		assertEquals("Fraser", rider.getSuburb());
		assertEquals("ACT", rider.getState());
		assertEquals("2615", rider.getPostcode());
		assertEquals("02 6258 0607", rider.getPhoneHome());
		assertEquals("0417 216 664", rider.getPhoneWorkOrMobile());
		assertEquals("Tue Dec 20 00:00:00 EST 1949", rider.getDob().toString());
		assertEquals(Gender.M, rider.getGender());
		assertEquals("Michael.Bentley@anu.edu.au", rider.getEmail());
		assertFalse(rider.isFirstAid());
		assertEquals("Kerr-Ann Smith", rider.getEmergencyContact());
		assertEquals("0414 874 652", rider.getPhoneEmergencyContact());
		assertEquals("", rider.getPhoneEmergencyContact2());
		assertEquals(54, rider.getNumber());
		assertEquals("B", rider.getGrade());
	}

	/**
	 * Tests that the gender is set to U for missing or non "M" or "F".
	 */
	@Test
	public void testEmptyGender() {
		ParseRider parser = new ParseRider();
		TR rider = parser.getEntity(MISSING_GENDER.split("\t"));
		assertEquals(Gender.U, rider.getGender());

		rider = parser.getEntity(INVALID_GENDER.split("\t"));
		assertEquals(Gender.U, rider.getGender());

		rider = parser.getEntity(LOWERCASE_GENDER.split("\t"));
		assertEquals(Gender.F, rider.getGender());
	}

	/**
	 * Tests that the rider number is set to -1 for a missing number
	 */
	@Test
	public void testRiderNumber() {
		ParseRider parser = new ParseRider();
		TR rider = parser.getEntity(MISSING_NUMBER.split("\t"));
		assertEquals(-1, rider.getNumber());
	}
}
