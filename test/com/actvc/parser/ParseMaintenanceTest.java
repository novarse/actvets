package com.actvc.parser;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;

import com.actvc.client.Gender;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TR;
import com.actvc.parse.ParseMaintenance;
import com.actvc.server.AppServiceImpl;

/**
 * Tests {@link ParseMaintenance}.
 * 
 * @author stephen
 * 
 */
public class ParseMaintenanceTest {

	private static final String SUMMER = "1	S";

	private static final String WINTER = "1	W";

	// Number LAST NAME FIRST NAME ADDRESS SUBURB STATE PCODE DOB HOME
	// WORK/MOBILE EMAIL GENDER
	// FIRSTAID EMERGENCY CONTACT EMERGENCY CONTACT Home EMERGENCY CONTACT
	// Mobile
	private static final String BLANK_NUMBER_LINE = "	Alain	41 Cobby Street	Campbell	ACT	2612	"
			+ "26/10/1958	(02) 62491618	(mob) 0400493148	arbaut@grapevine.net.au	Male	"
			+ "yes	Francoise Arbaut-Zaalan	(02) 62491618	(02) 61215425	Arbaut";

	private static final String NUMBER_LINE = "142	John	7 Nairn Place	Macquarie	ACT	2614	"
			+ "12/08/1966	62510175	0411097900	john@eldersbelconnen.com.au	Male	"
			+ "no	Lena Barlow	62302382		Barlow";

	private static final String FIRSTAID = "224	Alain	41 Cobby Street	Campbell	ACT	2612	"
			+ "26/10/1958	(02) 62491618	(mob) 0400493148	arbaut@grapevine.net.au	Male	"
			+ "N	Francoise Arbaut-Zaalan	(02) 62491618	(02) 61215425	Arbaut";

	private static final String BLANK_DOB = "224	Alain	41 Cobby Street	Campbell	ACT	2612	"
			+ "	(02) 62491618	(mob) 0400493148	arbaut@grapevine.net.au	Male	"
			+ "N	Francoise Arbaut-Zaalan	(02) 62491618	(02) 61215425	Arbaut";

	private static final String GENDER_M = "224	Alain	41 Cobby Street	Campbell	ACT	2612	"
			+ "	(02) 62491618	(mob) 0400493148	arbaut@grapevine.net.au	M	"
			+ "N	Francoise Arbaut-Zaalan	(02) 62491618	(02) 61215425	Arbaut";

	private static final String GENDER_FEMALE = "224	Alain	41 Cobby Street	Campbell	ACT	2612	"
			+ "	(02) 62491618	(mob) 0400493148	arbaut@grapevine.net.au	female	"
			+ "N	Francoise Arbaut-Zaalan	(02) 62491618	(02) 61215425	Arbaut";

	private static final String BLANK_ADDRESS = "224	Alain		Campbell	ACT	2612	"
			+ "	(02) 62491618	(mob) 0400493148	arbaut@grapevine.net.au	female	"
			+ "N	Francoise Arbaut-Zaalan	(02) 62491618	(02) 61215425	Arbaut";

	private static final ParseMaintenance parser = new ParseMaintenance();

	/**
	 * Setup parser
	 */

	/**
	 * Tests parsing a typical line.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testTypicalLineWithNumber() throws ParseException {
		TR dummyrider = new TR();
		dummyrider.setId(1L);
		String[] items = NUMBER_LINE.split("\t");
		// private static final String NUMBER_LINE =
		// "142	Barlow	John	7 Nairn Place	Macquarie	ACT	2614	"
		// + "12/08/1966	62510175	0411097900	john@eldersbelconnen.com.au	Male	"
		// + "no	Lena Barlow	62302382		Barlow	";

		TR rider = parser.doParseUpdateRiderDetails(dummyrider, items);
		assertEquals(Integer.parseInt(items[0]), rider.getNumber());
		assertEquals(items[1], rider.getFirstName());
		assertEquals(items[2], rider.getStreet());
		assertEquals(items[3], rider.getSuburb());
		assertEquals(items[4], rider.getState());
		assertEquals(items[5], rider.getPostcode());
		assertEquals(AppServiceImpl.processDate(items[6]), rider.getDob());
		assertEquals(items[7], rider.getPhoneHome());
		assertEquals(items[8], rider.getPhoneWorkOrMobile());
		assertEquals(items[9], rider.getEmail());
		assertEquals(Gender.getGender(items[10]), rider.getGender());
		assertEquals(false, rider.isFirstAid());
		assertEquals(items[12], rider.getEmergencyContact());
		assertEquals(items[13], rider.getPhoneEmergencyContact());
		assertEquals(items[14], rider.getPhoneEmergencyContact2());

		assertEquals(items[15], rider.getLastName());
	}

	/**
	 * Tests firstaid.
	 */
	@Test
	public void testFirstAid() {
		TR dummyrider = new TR();
		dummyrider.setId(1L);

		TR rider = parser.doParseUpdateRiderDetails(dummyrider, FIRSTAID
				.split("\t"));
		assertEquals(false, rider.isFirstAid());
	}

	/**
	 * Tests blank date.
	 */
	@Test
	public void testBlankDob() {
		TR dummyrider = new TR();
		dummyrider.setId(1L);

		TR rider = parser.doParseUpdateRiderDetails(dummyrider, BLANK_DOB
				.split("\t"));
		assertEquals(null, rider.getDob());
	}

	/**
	 * Tests for blank Number is handled and returns a new {@link TR}.
	 */
	@Test
	public void testBlankNumber() {
		TR dummyrider = new TR();
		dummyrider.setId(-1L);

		TR rider = parser.doParseUpdateRiderDetails(dummyrider,
				BLANK_NUMBER_LINE.split("\t"));
		assertEquals(-1, rider.getNumber());
	}

	/**
	 * Tests for missing Grade so not grade = "" if input grade is "Z"
	 */
	@Test
	public void testMissingGrade() {
		TR dummyrider = new TR();
		dummyrider.setId(1L);

		TR rider = parser.doParseUpdateRiderDetails(dummyrider, NUMBER_LINE
				.split("\t"));
		assertEquals(null, rider.getGrade());
	}

	/**
	 * Tests for various forms of the text for gender. i.e. that Male, M, m ->
	 * M, and Female, F, f -> F
	 */
	@Test
	public void testGender() {
		TR dummyrider = new TR();
		dummyrider.setId(1L);

		TR rider = parser.doParseUpdateRiderDetails(dummyrider, GENDER_FEMALE
				.split("\t"));
		assertEquals(Gender.F, rider.getGender());

		TR dummyrider2 = new TR();
		dummyrider.setId(2L);
		TR rider2 = parser.doParseUpdateRiderDetails(dummyrider2, GENDER_M
				.split("\t"));
		assertEquals(Gender.M, rider2.getGender());
	}

	/**
	 * Tests that a new blank address overwrites the old non-blank address
	 */
	@Test
	public void testOverwritesAddress() {

		TR dummyrider = new TR();
		dummyrider.setId(1L);

		TR rider = parser.doParseUpdateRiderDetails(dummyrider, NUMBER_LINE
				.split("\t"));
		assertEquals("7 Nairn Place", rider.getStreet());

		rider = parser.doParseUpdateRiderDetails(dummyrider, BLANK_ADDRESS
				.split("\t"));
		assertEquals("", rider.getStreet());
	}

	/**
	 * Tests updating events with seasons.
	 */
	@Test
	public void test() {
		TE dummyevent = new TE();
		dummyevent.setId(1L);

		TE event = parser.doParseUpdateEventSeason(dummyevent, WINTER
				.split("\t"));
		assertEquals(1, event.getId().longValue());
		assertEquals(2, event.getSeasonId().longValue());

		event = parser.doParseUpdateEventSeason(dummyevent, SUMMER.split("\t"));
		assertEquals(1, event.getSeasonId().longValue());
	}

}
