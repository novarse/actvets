package com.actvc.parse;

import java.text.ParseException;

import com.actvc.client.Gender;
import com.actvc.client.entities.TR;
import com.actvc.server.AppServiceImpl;

public class ParseRider implements ParseBase {

	@Override
	public TR getEntity(String[] items) {
		TR rider = new TR();
		if (!items[0].isEmpty()) {
			rider.setId(Long.parseLong(items[0]));
		}
		rider.setLastName(items[1].substring(0, 1)
				+ items[1].substring(1).toLowerCase());
		rider.setFirstName(items[2]);
		rider.setActive(false);
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
		rider.setFirstAid(items[12].equals("1"));
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
		rider.setSubGrade(1);

		return rider;
	}
}
