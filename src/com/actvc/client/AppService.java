package com.actvc.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.actvc.client.entities.LoginInfo;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TED;
import com.actvc.client.entities.TEDTO;
import com.actvc.client.entities.TEL;
import com.actvc.client.entities.TET;
import com.actvc.client.entities.TGCR;
import com.actvc.client.entities.TPRH;
import com.actvc.client.entities.TR;
import com.actvc.client.entities.TRDTO;
import com.actvc.client.entities.TRH;
import com.actvc.client.entities.TRP;
import com.actvc.client.entities.TS;
import com.actvc.client.entities.TSDao;
import com.actvc.client.entities.TSe;
import com.actvc.client.entities.TU;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 * 
 * @param <T>
 */
@RemoteServiceRelativePath("app")
public interface AppService<T> extends RemoteService {

	List<TED> loadEventDesc();

	String getEventDescByEventId(Long eventId);

	List<TRH> getRaceHistoryByEventId(long eventId);

	TR getRiderById(Long riderId);

	LoginInfo login(String requestUri);

	void registerUser(String email, String name);

	void saveRider(TR r);

	List<TET> loadEventTypes();

	TEDTO getEventDetails(long eventId);

	boolean deleteEvent(Long riderId);

	TRH getRaceHistory(Long eventId, long riderId);

	void saveRaceHistory(TRH rh);

	void saveEventDesc(TED ed);

	void saveEventType(TET et);

	TED getEventDescById(Long eventDescId);

	List<TU> loadUsers();

	TU getUser(String email);

	void saveUser(TU a);

	void deleteRaceHistory(Long eventId, Long riderId);

	List<String> loadRiderNumbersAndIds();

	void deleteAdmin(String clsName);

	void logClientMsg(String msg);

	List<TE> loadRecentEvents(boolean isSuperUser);

	List<TPRH> loadPending();

	boolean saveRiderAndCreateRaceHistory(TR r, long prhId);

	TEDTO loadFutureEvents(boolean smallList);

	TEDTO loadFutureEventsByMonths(Integer months);

	TEDTO loadHistoricEvents(boolean smallList);

	void deleteRider(Long riderId);

	int delete(int clsIdx);

	void saveEvent(TE e);

	TEDTO getRaceEventResults(long eventID);

	TRDTO getRiderHistoryById(Long riderId);

	TE getEvent(long eventId);

	List<TR> loadRidersForEvent(Long eventId);

	TPRH getPendingRaceHistory(long prhId);

	Integer getNextNumber();

	TR getRiderByNumber(int checkedRiderNumber);

	List<TR> getRidersByNumber(int checkedRiderNumber);

	List<TGCR> loadGradeChangeRequests();

	List<String> loadLocations();

	TEL getEditLocation(Long locationId);

	void changeGrade(String newGrade, int newSubgrade,
			String newCriteriumGrade, Long id);

	void deleteLocation(Long locationId);

	void saveLocation(TEL l);

	List<String> loadDirectors();

	String doTest2();

	long setMaxIdVal(int maxIdVal, int maxIterationInterval);

	Date doTest(TE e);

	List<TE> loadEventDatesAndIds(boolean showHistoricData,
			boolean showAllFutureEvents);

	void submitTweet(String tweetMsg);

	void saveTwitterSettings(TS twitter);

	void resetTwitter();

	void saveBitlySettings(TS bitly);

	TS getBitlyInfo();

	void resetBitly();

	List<TSe> loadSeasons();

	void saveSeason(TSe season);

	void deletePendingRider(Long id);

	void doTest();

	Map<String, TRP> getRiderPoints(Long seasonId);

	void saveGeneralSystem(String urlHome, String mainEmail, String returnUrl,
			int utcOffset, int checkHour);

	TSDao getTSDao(String eventName);

	void deleteGradeChangeRequest(Long lookupId);

	List<String> loadRiders();

	List<TED> loadActiveEventDesc();

	List<String> loadRiderNamesAndIds(Boolean showOnlyActives);

	boolean checkGradeChangeDetails(int number, String lastName,
			String firstName, Date dob, String newGrade, int subgrade,
			String criteriumGrade);

	String setTS(TS s, String eventName);

	void saveResetFinancialDate(TS rf);

	void saveDirectorReminder(TSDao directorReminder);

}
