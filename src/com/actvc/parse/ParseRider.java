package com.actvc.parse;

import java.text.ParseException;

import com.actvc.client.Gender;
import com.actvc.client.entities.TR;
import com.actvc.server.AppServiceImpl;

public class ParseRider implements ParseBase {

	private static final String NULL = "null";
	private static final String TRUE = "true";

	@Override
	public TR getEntity(String[] items) {
		TR rider = new TR();
		if (!items[0].isEmpty()) {
			rider.setId(Long.parseLong(items[0]));
		}
		rider.setFirstName(items[1]);
		rider.setActive(items[2].equalsIgnoreCase(TRUE) ? true : false);
		rider.setStreet(items[3]);
		rider.setSuburb(items[4]);
		rider.setState(items[5]);
		rider.setPostcode(items[6]);
		rider.setPhoneHome(items[7]);
		rider.setPhoneWorkOrMobile(items[8]);
		if (!items[9].isEmpty()) {
			try {
				rider.setDob(AppServiceImpl.processDate(items[9]));
			} catch (ParseException e) {
				throw new RuntimeException("Failed to parse birthday: "
						+ items[9]);
			}
		}
		rider.setGender(Gender.getGender(items[10]));
		rider.setEmail(items[11]);
		rider.setFirstAid(items[12].equalsIgnoreCase(TRUE) ? true : false);
		rider.setEmergencyContact(items[13]);
		rider.setPhoneEmergencyContact(items[14]);
		rider.setPhoneEmergencyContact2(items[15]);
		if (!items[16].isEmpty()) {
			rider.setNumber(Integer.parseInt(items[16]));
		} else {
			rider.setNumber(-1);
		}
		if (!items[17].equals("Z")) {
			rider.setGrade(items[17]);
		}
		if (!items[18].isEmpty()) {
			rider.setSubGrade(Integer.parseInt(items[18]));
		} else {
			rider.setSubGrade(-1);
		}
		if (!items[19].isEmpty() && !NULL.equalsIgnoreCase(items[19])) {
			rider.setCriteriumGrade(items[19]);
		} else {
			rider.setCriteriumGrade("");
		}
		rider.setDirector(items[20].equalsIgnoreCase(TRUE) ? true : false);
		if (!items[21].isEmpty() && !NULL.equalsIgnoreCase(items[21])) {
			rider.setAVCCNumber(items[21]);
		} else {
			rider.setAVCCNumber("");
		}
		rider.setLastName(items[22].substring(0, 1)
				+ items[22].substring(1).toLowerCase());

		return rider;
	}
}
