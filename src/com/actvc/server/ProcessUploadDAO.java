package com.actvc.server;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.actvc.client.Gender;
import com.actvc.client.common.MyConst;
import com.actvc.client.entities.Person;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TPRH;
import com.actvc.client.entities.TR;
import com.actvc.client.entities.TRH;
import com.actvc.client.entities.TS;
import com.google.code.twig.ObjectDatastore;

/**
 * <p>
 * Processes a line of data from a "Race Day Results" file.
 * </p>
 * <p>
 * The line can be processed in one of two ways depending on whether the
 * {@link TS}.{@code raceDayFileUploadUpdatesRiders} field is set to true
 * </p>
 * <p>
 * If false (the default) then data will be used to create race history records
 * for riders found by id or used to create pending riders if not found.
 * </p>
 * <p>
 * If true then data is used to update existing rider information if a rider is
 * found by id.
 * </p>
 * 
 * @author stephen
 * 
 */
public class ProcessUploadDAO {
	private final ObjectDatastore datastore = Util.getDatastore();
	private static final Logger log = Logger.getLogger(ProcessUploadDAO.class
			.getName());
	private final GetDataDAO getDataDAO = new GetDataDAO();

	public void processLine(TE event, String[] seg,
			boolean isRaceDayFileUploadUpdatesRiders) {
		final String NUMBER = "NUMBER";
		final int maxParts = 26;
		try {
			if ((isRaceDayFileUploadUpdatesRiders || !seg[6].isEmpty())
					&& !seg[0].toUpperCase().equals(NUMBER)) {
				// "Number\tSurname\tFirstName\tGrade\tSubGrade\tCriterium\tRace Grade\tPosition\tOverTheLine\tTime\tPoints\tAVCCNumber\tDOB\tGender\tStreet"
				// "\tSuburb\tState\tPostcode\tHome Phone\tWork or Mobile\tEmail\tFirst Aid\tEmergency Contact\tEmergency Contact No\tEmergency Contact No2\tID"

				Person r = null;
				String idStr = (seg.length >= maxParts ? seg[maxParts - 1]
						.trim() : "");
				long id = idStr.isEmpty() ? 0 : Long.parseLong(idStr);

				if (id != 0) {
					r = datastore.load(TR.class, id);
				}

				if (r != null) {
					if (!isRaceDayFileUploadUpdatesRiders) {
						TRH rh = new TRH();

						rh.setId(event.getId(), r.getId());
						rh.setDate(event.getDate());
						rh.setRaceGrade(seg[6].isEmpty() ? "" : seg[6]);
						try {
							rh.setPlace(seg[7].isEmpty() ? MyConst
									.getPlacenotset() : Integer
									.parseInt(seg[7]));
						} catch (RuntimeException e) {
							rh.setPlace(MyConst.getPlacenotset());
						}
						try {
							rh.setOverTheLine(seg[8].isEmpty() ? MyConst
									.getPlacenotset() : Integer
									.parseInt(seg[8]));
						} catch (RuntimeException e) {
							rh.setOverTheLine(MyConst.getPlacenotset());
						}
						rh.setTime(seg[9].isEmpty() ? "" : seg[9]);
						try {
							rh.setPoints(seg[10].isEmpty() ? MyConst
									.getPointsnotset() : Integer
									.parseInt(seg[10]));
						} catch (RuntimeException e) {
							rh.setPoints(MyConst.getPointsnotset());
						}

						datastore.store(rh);
					} else {
						// System.out.println(((TR) processDetails(r, event,
						// seg))
						// .toExportForm());
						datastore.storeOrUpdate(processDetails(r, event, seg));
					}
				} else {
					if (!isRaceDayFileUploadUpdatesRiders) {
						datastore.store(processDetails(new TPRH(), event, seg));
					}
				}
			}
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, e.getMessage() + ": " + Arrays.toString(seg),
					e);
		}
	}

	private Person processDetails(Person person, TE event, String[] seg) {

		boolean doForPending = person instanceof TPRH;

		if (doForPending) {
			((TPRH) person).setEventId(event.getId());
			((TPRH) person).setDate(event.getDate());
			((TPRH) person).setRaceGrade(seg[6].isEmpty() ? "" : seg[6]);
			try {
				((TPRH) person).setPlace(seg[7].isEmpty() ? MyConst
						.getPlacenotset() : Integer.parseInt(seg[7]));
			} catch (RuntimeException e) {
				((TPRH) person).setPlace(MyConst.getPlacenotset());
			}
			try {
				((TPRH) person).setOverTheLine(seg[8].isEmpty() ? MyConst
						.getPlacenotset() : Integer.parseInt(seg[8]));
			} catch (RuntimeException e) {
				((TPRH) person).setOverTheLine(MyConst.getPlacenotset());
			}
			((TPRH) person).setTime(seg[9].isEmpty() ? "" : seg[9]);
			try {
				((TPRH) person).setPoints(seg[10].isEmpty() ? MyConst
						.getPointsnotset() : Integer.parseInt(seg[10]));
			} catch (RuntimeException e) {
				((TPRH) person).setPoints(MyConst.getPointsnotset());
			}

			person.setActive(true);
			person.setDob(seg[12].isEmpty() ? "" : seg[12]);

			if (seg[0].isEmpty()) {
				throw new RuntimeException("Missing rider number: ");
			} else {
				person.setNumber(Integer.parseInt(seg[0]));
			}

			if (seg[1].isEmpty()) {
				throw new RuntimeException("Missing last name: ");
			} else {
				person.setLastName(seg[1]);
			}
			if (seg[2].isEmpty()) {
				throw new RuntimeException("Missing first name: ");
			} else {
				person.setFirstName(seg[2]);
			}

			person.setGrade(seg[3].isEmpty() ? "" : seg[3]);
			try {
				person.setSubGrade(seg[4].isEmpty() ? 0 : Integer
						.parseInt(seg[4]));
			} catch (NumberFormatException e) {
				person.setSubGrade(0);
			}
			person.setCriteriumGrade(seg[5].isEmpty() ? "" : seg[5]);
			person.setAVCCNumber(seg[11].isEmpty() ? "" : seg[11]);
			if (seg.length > 13) {
				person.setGender(Gender.getGender(seg[13]));
			}
			person.setStreet(seg[14].isEmpty() ? "" : seg[14]);
			person.setSuburb(seg[15].isEmpty() ? "" : seg[15]);
			person.setState(seg[16].isEmpty() ? "" : seg[16]);
			person.setPostcode(seg[17].isEmpty() ? "" : seg[17]);
			person.setPhoneHome(seg[18].isEmpty() ? "" : seg[18]);
			person.setPhoneWorkOrMobile(seg[19].isEmpty() ? "" : seg[19]);
			person.setEmail(seg.length > 20 && !seg[20].isEmpty() ? seg[20]
					: "");
			if (seg.length > 21) {
				person.setFirstAid(seg[21].trim().equals("1"));
			}
			person.setEmergencyContact(seg.length > 22 && !seg[22].isEmpty() ? seg[22]
					: "");
			person.setPhoneEmergencyContact(seg.length > 23
					&& !seg[23].isEmpty() ? seg[23] : "");
			person.setPhoneEmergencyContact2(seg.length > 24
					&& !seg[24].isEmpty() ? seg[24] : "");

		} else {
			// updating person from file

			// if (!seg[0].isEmpty()) {
			// person.setNumber(Integer.parseInt(seg[0]));
			// }

			if (!seg[3].isEmpty())
				person.setGrade(seg[3]);
			if (!seg[4].isEmpty()) {
				try {
					person.setSubGrade(seg[4].isEmpty() ? 0 : Integer
							.parseInt(seg[4]));
				} catch (NumberFormatException e) {
					person.setSubGrade(0);
				}
			}
			if (!seg[5].isEmpty())
				person.setCriteriumGrade(seg[5]);
			if (!seg[11].isEmpty())
				person.setAVCCNumber(seg[11]);
			if (!seg[13].isEmpty()) {
				person.setGender(Gender.getGender(seg[13]));
			}
			if (!seg[14].isEmpty())
				person.setStreet(seg[14]);
			if (!seg[15].isEmpty())
				person.setSuburb(seg[15]);
			if (!seg[16].isEmpty())
				person.setState(seg[16]);
			if (!seg[17].isEmpty())
				person.setPostcode(seg[17]);
			if (!seg[18].isEmpty())
				person.setPhoneHome(seg[18]);
			if (!seg[19].isEmpty())
				person.setPhoneWorkOrMobile(seg[19]);
			if (seg.length > 20 && !seg[20].isEmpty())
				person.setEmail(seg[20]);
			if (seg.length > 21 && seg[21].trim().equals("1")) {
				person.setFirstAid(true);
			}
			if (seg.length > 22 && !seg[22].isEmpty())
				person.setEmergencyContact(seg[22]);
			if (seg.length > 23 && !seg[23].isEmpty())
				person.setPhoneEmergencyContact(seg[23]);
			if (seg.length > 24 && !seg[24].isEmpty())
				person.setPhoneEmergencyContact2(seg[24]);
		}

		return person;
	}

}
