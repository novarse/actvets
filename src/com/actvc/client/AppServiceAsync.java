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
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface AppServiceAsync {

	void loadEventDesc(AsyncCallback<List<TED>> asyncCallback);

	void getEventDescByEventId(Long eventId, AsyncCallback<String> asyncCallback);

	void getRaceHistoryByEventId(long eventId,
			AsyncCallback<List<TRH>> asyncCallback);

	void getRiderById(Long riderId, AsyncCallback<TR> asyncCallback);

	void login(String string, AsyncCallback<LoginInfo> asyncCallback);

	void registerUser(String email, String name,
			AsyncCallback<Void> asyncCallback);

	void saveRider(TR r, AsyncCallback<Void> asyncCallback);

	void loadEventTypes(AsyncCallback<List<TET>> asyncCallback);

	void getEventDetails(long eventId, AsyncCallback<TEDTO> asyncCallback);

	void deleteEvent(Long riderId, AsyncCallback<Boolean> asyncCallback);

	void getRaceHistory(Long eventId, long riderId,
			AsyncCallback<TRH> asyncCallback);

	void saveRaceHistory(TRH rh, AsyncCallback<Void> asyncCallback);

	void saveEventDesc(TED ed, AsyncCallback<Void> asyncCallback);

	void saveEventType(TET et, AsyncCallback<Void> asyncCallback);

	void getEventDescById(Long eventDescId, AsyncCallback<TED> asyncCallback);

	void loadUsers(AsyncCallback<List<TU>> asyncCallback);

	void getUser(String email, AsyncCallback<TU> asyncCallback);

	void saveUser(TU a, AsyncCallback<Void> asyncCallback);

	void deleteRaceHistory(Long eventId, Long riderId,
			AsyncCallback<Void> asyncCallback);

	void loadRiderNamesAndIds(Boolean showOnlyActives,
			AsyncCallback<List<String>> asyncCallback);

	void loadRiderNumbersAndIds(AsyncCallback<List<String>> callback);

	void logClientMsg(String msg, AsyncCallback<Void> asyncCallback);

	void loadRecentEvents(boolean isSuperUser,
			AsyncCallback<List<TE>> asyncCallback);

	void loadPending(AsyncCallback<List<TPRH>> asyncCallback);

	void saveRiderAndCreateRaceHistory(TR r, long prhId,
			AsyncCallback<Boolean> asyncCallback);

	void loadFutureEvents(boolean smallList, AsyncCallback<TEDTO> asyncCallback);

	void loadHistoricEvents(boolean smallList,
			AsyncCallback<TEDTO> asyncCallback);

	void deleteRider(Long riderId, AsyncCallback<Void> asyncCallback);

	void deleteAdmin(String id, AsyncCallback<Void> callback);

	void delete(int clsIdx, AsyncCallback<Integer> asyncCallback);

	void saveEvent(TE e, AsyncCallback<Void> callback);

	void getRaceEventResults(long eventID, AsyncCallback<TEDTO> asyncCallback);

	void getRiderHistoryById(Long riderId, AsyncCallback<TRDTO> asyncCallback);

	void getEvent(long eventId, AsyncCallback<TE> asyncCallback);

	void loadRidersForEvent(Long eventId, AsyncCallback<List<TR>> asyncCallback);

	void getPendingRaceHistory(long prhId, AsyncCallback<TPRH> asyncCallback);

	void getNextNumber(AsyncCallback<Integer> asyncCallback);

	void getRiderByNumber(int checkedRiderNumber,
			AsyncCallback<TR> asyncCallback);

	void getRidersByNumber(int checkedRiderNumber,
			AsyncCallback<List<TR>> asyncCallback);

	void checkGradeChangeDetails(int number, String lastName, String firstName,
			Date dob, String newGrade, int subgrade, String criteriumGrade,
			AsyncCallback<Boolean> asyncCallback);

	void loadGradeChangeRequests(AsyncCallback<List<TGCR>> asyncCallback);

	void loadLocations(AsyncCallback<List<String>> asyncCallback);

	void getEditLocation(Long locationId, AsyncCallback<TEL> asyncCallback);

	void changeGrade(String newGrade, int newSubgrade,
			String newCriteriumGrade, Long id, AsyncCallback<Void> asyncCallback);

	void deleteLocation(Long locationId, AsyncCallback<Void> asyncCallback);

	void saveLocation(TEL l, AsyncCallback<Void> asyncCallback);

	void loadDirectors(AsyncCallback<List<String>> asyncCallback);

	void doTest2(AsyncCallback<String> asyncCallback);

	void setMaxIdVal(int maxIdVal, int maxIterationInterval,
			AsyncCallback<Long> asyncCallback);

	void doTest(TE e, AsyncCallback<Date> asyncCallback);

	void loadEventDatesAndIds(boolean showHistoricData,
			boolean showAllFutureEvents, AsyncCallback<List<TE>> asyncCallback);

	void submitTweet(String tweetMsg, AsyncCallback<Void> asyncCallback);

	void saveTwitterSettings(TS twitter, AsyncCallback<Void> asyncCallback);

	void resetTwitter(AsyncCallback<Void> asyncCallback);

	void saveBitlySettings(TS bitly, AsyncCallback<Void> asyncCallback);

	void getBitlyInfo(AsyncCallback<TS> asyncCallback);

	void resetBitly(AsyncCallback<Void> asyncCallback);

	void loadSeasons(AsyncCallback<List<TSe>> asyncCallback);

	void saveSeason(TSe season, AsyncCallback<Void> asyncCallback);

	void saveGeneralSystem(String urlHome, String mainEmail, String returnUrl,
			int utcOffset, int checkHour, AsyncCallback<Void> asyncCallback);

	void deletePendingRider(Long id, AsyncCallback<Void> asyncCallback);

	void doTest(AsyncCallback<Void> asyncCallback);

	void getRiderPoints(Long seasonId,
			AsyncCallback<Map<String, TRP>> asyncCallback);

	void getTSDao(String eventName, AsyncCallback<TSDao> asyncCallback);

	void deleteGradeChangeRequest(Long lookupId,
			AsyncCallback<Void> asyncCallback);

	void loadRiders(AsyncCallback<List<String>> asyncCallback);

	void loadActiveEventDesc(AsyncCallback<List<TED>> asyncCallback);

	void setTS(TS s, String eventName, AsyncCallback<String> asyncCallback);

	void saveResetFinancialDate(TS rf, AsyncCallback<Void> asyncCallback);

	void saveDirectorReminder(TSDao reminderDao,
			AsyncCallback<Void> asyncCallback);

	void loadFutureEventsByMonths(Integer months, AsyncCallback<TEDTO> callback);

}
