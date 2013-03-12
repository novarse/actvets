package com.actvc.client.entity;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.actvc.client.Gender;
import com.actvc.client.entities.TR;

/**
 * Tests for {@link TR}.
 * 
 * @author stephen
 * 
 */
public class TRTest {
	/**
	 * Tests toExportForm.
	 */
	@Test
	public void testToExportForm() {
		TR rider = new TR();
		rider.setId(1L);
		rider.setFirstName("firstName");
		rider.setActive(true);
		rider.setStreet("street");
		rider.setSuburb("suburb");
		rider.setState("state");
		rider.setPostcode("postcode");
		rider.setPhoneHome("phonehome");
		rider.setPhoneWorkOrMobile("phonemobile");
		Date d = new Date(110, 0, 1);
		rider.setDob(d);
		rider.setGender(Gender.M);
		rider.setEmail("email");
		rider.setFirstAid(true);
		rider.setEmergencyContact("emergencyContact");
		rider.setPhoneEmergencyContact("phoneEmergencyContact");
		rider.setPhoneEmergencyContact2("phoneEmergencyContact2");
		rider.setNumber(2);
		rider.setGrade("e");
		rider.setSubGrade(1);
		rider.setCriteriumGrade("d");
		rider.setLastName("lastName");

		System.out.println(rider.toExportForm());

		String expected = "1	Firstname	true	street	suburb	state	postcode	phonehome	phonemobile	"
				+ "Fri Jan 01 00:00:00 EST 2010	M	email	true	emergencyContact	phoneEmergencyContact	"
				+ "phoneEmergencyContact2	2	E	1	D	Lastname	false";
		Assert.assertEquals(expected, rider.toExportForm());
	}
}
