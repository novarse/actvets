package com.actvc.parse;

import java.text.ParseException;

import com.actvc.client.Gender;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TEntity;
import com.actvc.client.entities.TR;
import com.actvc.server.AppServiceImpl;
import com.actvc.server.GetDataDAO;

/**
 * Contains methods used to parse the maintenance file for various updates made
 * upon the database.
 * 
 * @author stephen
 * 
 */
public class ParseMaintenance implements ParseBase {

	private final GetDataDAO dao = new GetDataDAO();

	/**
	 * <p>
	 * Updates the {@link TR} with changes to addresses etc and adding new
	 * members.
	 * </p>
	 * <p>
	 * Uses the Rider number to identify existing riders. If found details are
	 * updated. If not found then log the line
	 * </p>
	 * <p>
	 * For those that have no Number, create a new rider and assign a new Number
	 * </p>
	 * 
	 */
	@Override
	public TEntity getEntity(String[] items) {
		return getTEFromSeasonUpdate(items);
		// return getTRFromUpdateRiders(items);
	}

	private TEntity getTEFromSeasonUpdate(String[] items) {
		TE event = dao.getById(TE.class, Long.parseLong(items[0]));
		if (event != null) {
			return doParseUpdateEventSeason(event, items);
		} else {
			return null;
		}
	}

	private TR getTRFromUpdateRiders(String[] items) {
		TR rider = null;

		if (!items[0].isEmpty()) {
			int number = Integer.parseInt(items[0]);

			rider = dao.getRiderByNumber(number);
		}
		if (rider == null) {
			rider = new TR();
		}

		return doParseUpdateRiderDetails(rider, items);
	}

	public TR doParseUpdateRiderDetails(TR rider, String[] items) {
		if (!items[0].isEmpty()) {
			rider.setNumber(Integer.parseInt(items[0]));
		} else {
			rider.setNumber(-1);
		}
		rider.setFirstName(items[1]);
		rider.setStreet(items[2]);
		rider.setSuburb(items[3]);
		rider.setState(items[4]);
		rider.setPostcode(items[5]);
		if (!items[6].isEmpty()) {
			try {
				rider.setDob(AppServiceImpl.processDate(items[6]));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rider.setPhoneHome(items[7]);
		rider.setPhoneWorkOrMobile(items[8]);
		rider.setEmail(items[9]);
		rider.setGender(Gender.getGender(items[10]));
		rider.setFirstAid(items[11].equalsIgnoreCase("YES")
				|| items[11].equalsIgnoreCase("Y"));
		rider.setEmergencyContact(items[12]);
		rider.setPhoneEmergencyContact(items[13]);
		rider.setPhoneEmergencyContact2(items[14]);
		rider.setLastName(items[15]);
		return rider;
	}

	public TE doParseUpdateEventSeason(TE event, String[] items) {
		String s = items[1].toUpperCase();
		long season = s.equals("S") ? 1 : (s.equals("W") ? 2 : 0);
		event.setSeasonId(season);
		return event;
	}
}
