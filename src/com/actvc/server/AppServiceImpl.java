package com.actvc.server;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;

import com.actvc.client.AppService;
import com.actvc.client.common.MyConst;
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
import com.actvc.client.entities.TText;
import com.actvc.client.entities.TU;
import com.actvc.client.entities.Temp;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.code.twig.ObjectDatastore;
import com.google.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AppServiceImpl extends RemoteServiceServlet implements
		AppService<Object> {
	private final static ObjectDatastore datastore = Util.getDatastore();
	private static final Logger log = Logger.getLogger(AppServiceImpl.class
			.getName());

	private final GetDataDAO getDataDAO = new GetDataDAO();
	private final GetTEDTO getTEDTO = new GetTEDTO();

	private <T> Integer delete(Class<T> cls) {
		try {
			Integer dataToDelete = 0;
			QueryResultIterator<T> iterator = datastore.find().type(cls)
					.fetchMaximum(200).now();
			while (iterator.hasNext()) {
				datastore.delete(iterator.next());
				dataToDelete++;
			}
			log.info("deleted: " + dataToDelete);
			return dataToDelete;
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<TED> loadEventDesc() {
		try {
			return getDataDAO.loadEventDesc();
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public String getEventDescByEventId(Long eventId) {
		try {
			return getDataDAO.getEventDescByEventId(eventId);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<TRH> getRaceHistoryByEventId(long eventId) {
		try {
			return getDataDAO.getRaceHistoryByEventId(eventId);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public TR getRiderById(Long riderId) {
		try {
			return getDataDAO.getById(TR.class, riderId);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public LoginInfo login(String requestUri) {
		try {
			log.info(requestUri);
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();

			LoginInfo loginInfo = new LoginInfo();

			if (user != null) {
				TU ru = getUser(user.getEmail());
				if (ru == null) {
					loginInfo.setLoggedIn(false);
					loginInfo.setLoginUrl(userService
							.createLoginURL(requestUri));
				} else {
					loginInfo.setLoggedIn(true);
					loginInfo.setEmailAddress(user.getEmail());
					loginInfo.setNickname(user.getNickname());
					loginInfo.setLogoutUrl(userService
							.createLogoutURL(requestUri));
					loginInfo.setName(ru.getName());
					loginInfo.setRoleSuperUser(ru.isRoleSuperUser());
					loginInfo.setRoleCommittee(ru.isRoleCommittee());
					loginInfo.setRoleHandicapper(ru.isRoleHandicapper());
					loginInfo.setRoleMembershipTreasurer(ru
							.isRoleMembershipTreasurer());
					loginInfo.setRoleRaceDirector(ru.isRoleRaceDirector());
					loginInfo.setRoleResults(ru.isRoleResults());
					loginInfo.setRoleSystem(ru.isRoleSystem());
				}
			} else {
				loginInfo.setLoggedIn(false);
				loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
			}

			return loginInfo;
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	private RuntimeException handleException(RuntimeException e) {
		log.log(Level.SEVERE, e.getMessage(), e);
		return e;
	}

	@Override
	public TU getUser(String email) {
		try {
			return getDataDAO.getUser(email);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void registerUser(String email, String name) {
		try {
			TU ru = new TU(email, name);
			datastore.store(ru);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<TET> loadEventTypes() {
		try {
			return getDataDAO.loadEventTypes();
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public TEDTO getEventDetails(long eventId) {
		try {
			return getTEDTO.getEventDetails(eventId);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void saveEvent(TE ev) {
		try {
			datastore.storeOrUpdate(ev);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void saveLocation(TEL l) {
		try {
			datastore.store(l);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@Override
	public void saveRider(TR r) {
		try {
			int number = r.getNumber();

			datastore.store(r);

			// first check if a non-active has the same number. If one has, make
			// that number 0.
			QueryResultIterator<TR> it = datastore.find().type(TR.class)
					.addFilter("number", FilterOperator.EQUAL, number)
					.addFilter("isActive", FilterOperator.EQUAL, false).now();
			while (it.hasNext()) {
				TR tr = it.next();
				if (!r.getId().equals(tr.getId())) {
					tr.setNumber(0);
					datastore.update(tr);
				}
			}

		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@Override
	public void saveRaceHistory(TRH rh) {
		try {
			if (rh.getDate() == null) {
				rh.setDate(getDataDAO.getEventDateById(rh.getEventId()));
			}
			datastore.store(rh);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void saveEventDesc(TED j) {
		try {
			datastore.store(j);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void saveEventType(TET j) {
		try {
			datastore.store(j);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void saveUser(TU a) {
		try {
			datastore.store(a);
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@Override
	public boolean saveRiderAndCreateRaceHistory(TR r, long prhId) {

		try {
			boolean savedOk = false;
			datastore.store(r);

			TRH rh = new TRH();
			TPRH prh = datastore.load(TPRH.class, prhId);
			rh.setDate(prh.getDate());
			rh.setId(prh.getEventId(), r.getId());
			rh.setRaceGrade(prh.getRaceGrade());
			rh.setOverTheLine(prh.getOverTheLine());
			rh.setPlace(prh.getPlace());
			rh.setPoints(prh.getPoints());
			rh.setTime(prh.getTime());
			datastore.store(rh);

			datastore.delete(datastore.load(TPRH.class, prhId));
			savedOk = true;
			return savedOk;
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@Override
	public boolean deleteEvent(Long eventId) {
		try {
			boolean result = false;
			TE j = datastore.load(TE.class, eventId);
			if (j != null) {
				QueryResultIterator<TRH> iterator = datastore.find()
						.type(TRH.class)
						.addFilter("eventId", FilterOperator.EQUAL, eventId)
						.now();
				List<TRH> rhList = Lists.newArrayList(iterator);
				datastore.deleteAll(rhList);

				datastore.delete(j);

				result = true;
			}

			return result;
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public TRH getRaceHistory(Long eventId, long riderId) {
		try {
			return getDataDAO.getRaceHistory(eventId, riderId);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public TED getEventDescById(Long eventDescId) {
		try {
			return getDataDAO.getById(TED.class, eventDescId);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<TU> loadUsers() {
		try {
			return getDataDAO.getUsers(TU.class, "");
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void deleteRaceHistory(Long eventId, Long riderId) {
		try {
			QueryResultIterator<TRH> iterator = datastore.find()
					.type(TRH.class)
					.addFilter("eventId", FilterOperator.EQUAL, eventId)
					.addFilter("riderId", FilterOperator.EQUAL, riderId).now();
			List<TRH> rhList = Lists.newArrayList(iterator);
			datastore.deleteAll(rhList);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<String> loadRiderNamesAndIds(Boolean showOnlyActives) {
		try {
			return getDataDAO.loadRiderNamesAndIds(showOnlyActives);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<String> loadRiderNumbersAndIds() {
		try {
			return getDataDAO.loadRiderNumbersAndIds();
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public int delete(int clsIdx) {
		try {
			Integer dataToDelete = 0;
			if (clsIdx == MyConst.EVENTIDX) {
				dataToDelete = delete(com.actvc.client.entities.TE.class);
			} else if (clsIdx == MyConst.EVENTDESCIDX) {
				dataToDelete = delete(com.actvc.client.entities.TED.class);
			} else if (clsIdx == MyConst.EVENTTYPEIDX) {
				dataToDelete = delete(com.actvc.client.entities.TET.class);
			} else if (clsIdx == MyConst.RACEHISTIDX) {
				dataToDelete = delete(com.actvc.client.entities.TRH.class);
			} else if (clsIdx == MyConst.RIDERIDX) {
				dataToDelete = delete(com.actvc.client.entities.TR.class);
			} else if (clsIdx == MyConst.EVENTLOCATIONIDX) {
				dataToDelete = delete(com.actvc.client.entities.TEL.class);
			} else if (clsIdx == MyConst.PENDINGRACEHISTORYIDX) {
				dataToDelete = delete(com.actvc.client.entities.TPRH.class);
			}
			return dataToDelete;
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void logClientMsg(String msg) {
		try {
			log.info(msg);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<TE> loadRecentEvents(boolean isSuperUser) {
		try {
			return getDataDAO.loadRecentEvents(isSuperUser);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<TPRH> loadPending() {
		try {
			return getDataDAO.getPendingRiders();
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void deleteAdmin(String key) {
		try {
			TU j = datastore.load(TU.class, key);
			if (j != null) {
				datastore.delete(j);
			}
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	public <T> void delete(Class<T> cls, Long id) {
		try {
			T j = datastore.load(cls, id);
			if (j != null) {
				datastore.delete(j);
			}
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	// public <T> void delete(Class<T> cls, Long id) {
	// try {
	// T j = datastore.load(cls, id);
	// if (j != null) {
	// datastore.delete(j);
	// }
	// } catch (RuntimeException e) {
	// throw handleException(e);
	// }
	// }

	@Override
	public void deleteRider(Long riderId) {
		try {
			TR j = datastore.load(TR.class, riderId);
			if (j != null) {
				QueryResultIterator<TRH> iterator = datastore.find()
						.type(TRH.class)
						.addFilter("riderId", FilterOperator.EQUAL, riderId)
						.now();
				List<TRH> rhList = Lists.newArrayList(iterator);
				datastore.deleteAll(rhList);

				datastore.delete(j);
			}
		} catch (RuntimeException e) {
			throw handleException(e);
		}

	}

	@Override
	public TEDTO loadFutureEvents(boolean smallList) {
		try {
			return getTEDTO.loadEvents(true, smallList);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public TEDTO loadHistoricEvents(boolean smallList) {
		try {
			return getTEDTO.loadEvents(false, smallList);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public TEDTO getRaceEventResults(long eventID) {
		try {
			return getTEDTO.getRaceEventResults(eventID);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public TRDTO getRiderHistoryById(Long riderId) {
		try {
			return getDataDAO.getRiderHistoryById(riderId);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public TE getEvent(long eventId) {
		try {
			TE e = getDataDAO.getById(TE.class, eventId);
			return e;
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<TR> loadRidersForEvent(Long eventId) {
		try {
			return getDataDAO.loadRidersForEvent(eventId);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public TPRH getPendingRaceHistory(long prhId) {
		try {
			return getDataDAO.getById(TPRH.class, prhId);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public Integer getNextNumber() {
		try {
			return getDataDAO.getNextNumber();
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public TR getRiderByNumber(int checkedRiderNumber) {
		try {
			return getDataDAO.getRiderByNumber(checkedRiderNumber);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<TR> getRidersByNumber(int checkedRiderNumber) {
		try {
			return getDataDAO.getRidersByNumber(checkedRiderNumber);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public boolean checkGradeChangeDetails(int number, String lastName,
			String firstName, Date dob, String newGrade, int newSubgrade,
			String newCriteriumGrade) {
		try {
			boolean result = false;
			QueryResultIterator<TR> iterator = datastore.find().type(TR.class)
					.addFilter("number", FilterOperator.EQUAL, number)
					.addFilter("isActive", FilterOperator.EQUAL, true).now();
			TR r = null;
			while (iterator.hasNext()) {
				r = iterator.next();
				break;
			}
			if (r != null) {
				result = r.getUppercaseLastName()
						.equals(lastName.toUpperCase())
						&& r.getUppercaseFirstName().equals(
								firstName.toUpperCase())
						&& r.getDob().equals(dob);

			}

			if (result) {
				TGCR gcr = new TGCR(r.getId(), number, lastName, firstName,
						dob, r.getGrade(), r.getSubGrade(),
						r.getCriteriumGrade(), newGrade, newSubgrade,
						newCriteriumGrade, new Date());
				datastore.store(gcr);

				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);

				String msgBody = "Rider: "
						+ number
						+ "\nName: "
						+ firstName
						+ " "
						+ lastName
						+ "\nDate of Birth: "
						+ dob
						+ "\nCurrent Grade: "
						+ r.getGrade()
						+ "\nCurrent Sub-grade: "
						+ r.getSubGrade()
						+ "\nCurrent Criterium Grade: "
						+ r.getCriteriumGrade()
						+ "\nNew Grade: "
						+ newGrade
						+ (r.getSubGrade() == 0 ? "" : "\nNew Sub-grade: "
								+ r.getSubGrade()) + "\nNew Criterium Grade: "
						+ r.getCriteriumGrade() + "\n";
				log.info(msgBody);
				try {
					Message msg = new MimeMessage(session);
					msg.setFrom(new InternetAddress(MyConst
							.getHandicapperemail(), "Sender"));
					msg.addRecipient(Message.RecipientType.TO,
							new InternetAddress(MyConst.getHandicapperemail(),
									"Handicapper"));
					msg.setSubject("Grade Change Request");
					msg.setText(msgBody);
					Transport.send(msg);

				} catch (AddressException e) {
					log.info(e.getMessage());
				} catch (MessagingException e) {
					log.info(e.getMessage());
				} catch (UnsupportedEncodingException e) {
					log.info(e.getMessage());
				}
			}
			return result;
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<TGCR> loadGradeChangeRequests() {
		try {
			return getDataDAO.loadGradeChangeRequests();
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<String> loadLocations() {
		try {
			return getDataDAO.loadLocations();
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public TEL getEditLocation(Long locationId) {
		try {
			return getDataDAO.getEditLocation(locationId);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void changeGrade(String newGrade, int newSubgrade,
			String newCriteriumGrade, Long id) {
		try {
			TGCR gcr = datastore.load(TGCR.class, id);
			TR r = datastore.load(TR.class, gcr.getRiderId());
			if (!newGrade.isEmpty()) {
				r.setGrade(newGrade);
			}
			if (newSubgrade != 0) {
				r.setSubGrade(newSubgrade);
			}
			if (!newCriteriumGrade.isEmpty()) {
				r.setCriteriumGrade(newCriteriumGrade);
			}
			datastore.update(r);
			datastore.delete(gcr);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void deleteLocation(Long locationId) {
		try {
			datastore.delete(datastore.load(TEL.class, locationId));
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public List<String> loadDirectors() {
		return getDataDAO.loadDirectors();
	}

	@Override
	public List<String> loadRiders() {
		return getDataDAO.loadRiders();
	}

	@Override
	public void doTest() {
		log.info("in doTest");
	}

	@Override
	public String doTest2() {
		try {
			TE te = datastore.load(TE.class, -470);
			String result = "retreived date = " + te.getDate()
					+ "; directorId = " + te.getDirectorId();
			log.info(result);
			return result;
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public long setMaxIdVal(int maxIdVal, int maxIterationInterval) {
		Long result = 0l;
		for (int i = 0; i < maxIterationInterval; i++) {
			Temp t = new Temp();
			result = datastore.store(t).getId();
			if (result >= maxIdVal) {
				break;
			}
		}
		System.out.println("maxId = " + result);
		return result;
	}

	@Override
	public Date doTest(TE e) {
		e.setId(1L);
		log.info("on server: e = " + e.getDate().toString());
		datastore.storeOrUpdate(e);
		TE e2 = datastore.load(TE.class, 1L);
		return e2.getDate();
	}

	@Override
	public List<TE> loadEventDatesAndIds(boolean showHistoricData,
			boolean showAllFutureEvents) {
		try {
			return getDataDAO.loadEventDatesAndIds(showHistoricData,
					showAllFutureEvents);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void submitTweet(String tweetMsg) {
		try {
			TS s = getDataDAO.getTS();
			if (!s.hasAllTweetFields()) {
				throw new RuntimeException("Missing Tweet fields");
			}
			AccessToken accessToken = new AccessToken(s.getToken(),
					s.getTokenSecret());
			Twitter twitter = new TwitterFactory().getOAuthAuthorizedInstance(
					s.getConsumerToken(), s.getConsumerSecret(), accessToken);
			twitter.updateStatus(tweetMsg);
		} catch (TwitterException te) {
			if (401 == te.getStatusCode()) {
				log.info("Unable to get the access token.");
				throw new RuntimeException(te);
			} else {
				log.info(te.getMessage());
				throw new RuntimeException(te);
			}
		}

	}

	@Override
	public void saveTwitterSettings(TS twitter) {
		AccessToken accessToken = new AccessToken(twitter.getToken(),
				twitter.getTokenSecret());
		Twitter t = new TwitterFactory().getOAuthAuthorizedInstance(
				twitter.getConsumerToken(), twitter.getConsumerSecret(),
				accessToken);
		try {
			t.verifyCredentials();

			TS system = getDataDAO.getTS();
			system.setConsumerToken(twitter.getConsumerToken());
			system.setConsumerSecret(twitter.getConsumerSecret());
			system.setToken(twitter.getToken());
			system.setTokenSecret(twitter.getTokenSecret());

			datastore.storeOrUpdate(system);

		} catch (TwitterException e) {
			log.info(e.getMessage());
			throw new RuntimeException("Authorization details are incorrect.");
		}

	}

	@Override
	public void resetTwitter() {
		TS system = getDataDAO.getTS();
		system.setConsumerToken(null);
		system.setConsumerSecret(null);
		system.setToken(null);
		system.setToken(null);

		datastore.storeOrUpdate(system);
	}

	@Override
	public void saveBitlySettings(TS bitly) {
		TS system = getDataDAO.getTS();
		system.setBitlyUserName(bitly.getBitlyUserName());
		system.setBitlyAPIKey(bitly.getBitlyAPIKey());

		datastore.storeOrUpdate(system);
	}

	@Override
	public TS getBitlyInfo() {
		return getDataDAO.getTS();

	}

	@Override
	public void resetBitly() {
		TS system = getDataDAO.getTS();
		system.setBitlyUserName("");
		system.setBitlyAPIKey("");
		datastore.storeOrUpdate(system);
	}

	@Override
	public List<TSe> loadSeasons() {
		try {
			return getDataDAO.loadSeasons();
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	@Override
	public void saveSeason(TSe j) {
		try {
			datastore.store(j);
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	/**
	 * Parses a date string of the form dd/MM/yyyy, returning a {@link Date}
	 * 
	 * @throws ParseException
	 */
	public static Date processDate(String dateStr) throws ParseException {
		return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(dateStr);
	}

	@Override
	public void deletePendingRider(Long id) {
		delete(TPRH.class, id);
	}

	@Override
	public Map<String, TRP> getRiderPoints(Long seasonId) {
		return getDataDAO.getRiderPoints(seasonId);
	}

	@Override
	public void saveGeneralSystem(String urlHome, String mainEmail,
			String returnUrl, int utcOffset, int checkHour) {
		TS system = getDataDAO.getTS();
		system.setUrlHome(urlHome);
		system.setMainEmail(mainEmail);
		system.setReturnBitlyUrl(returnUrl);
		system.setServerTimeOffset(utcOffset);
		system.setHourToCheckForNextRace(checkHour);
		datastore.update(system);
	}

	@Override
	public TSDao getTSDao(String eventName) {
		return getDataDAO.getTSDao(eventName);
	}

	@Override
	public String setTS(TS s, String event) {
		datastore.store(s);
		return event;
	}

	@Override
	public void deleteGradeChangeRequest(Long lookupId) {
		TGCR gcr = datastore.load(TGCR.class, lookupId);
		datastore.delete(gcr);
	}

	@Override
	public List<TED> loadActiveEventDesc() {
		try {
			return getDataDAO.loadActiveEventDesc();
		} catch (RuntimeException e) {
			throw handleException(e);
		}
	}

	public void sendEmail(String subject, String message) {
		TS system = getDataDAO.getTS();
		String siteFields = system.getMainEmail();
		sendEmail(siteFields, siteFields, siteFields, siteFields, subject,
				message);
	}

	public static void sendEmail(String fromAddress, String fromName,
			String toAddress, String toName, String subject, String message) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(fromAddress, fromName));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					toAddress, toName));

			msg.setSubject(subject);
			msg.setText(message);
			Transport.send(msg);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void saveResetFinancialDate(TS rf) {
		TS system = getDataDAO.getTS();
		system.setResetFinancialStatus(rf.isResetFinancialStatus());
		system.setResetFinancialDay(rf.getResetFinancialDay());
		system.setResetFinancialMonth(rf.getResetFinancialMonth());

		datastore.storeOrUpdate(system);
	}

	public static void resetFinancials() {
		QueryResultIterator<TR> iterator = datastore.find().type(TR.class)
				.addFilter("isActive", FilterOperator.EQUAL, true).now();
		log.info("Resetting Financials");
		while (iterator.hasNext()) {
			TR rider = iterator.next();
			rider.setActive(false);
			datastore.storeOrUpdate(rider);
		}
	}

	@Override
	public void saveDirectorReminder(TSDao directorReminder) {
		TS system = getDataDAO.getTS();
		String message = directorReminder.getDirectorMessage();
		Long messageId = system.getDirectorReminderMessageId();
		if (messageId == null || messageId <= 0) {
			messageId = saveText(message);
		} else {
			saveText(messageId, message);
		}

		if (messageId == null) {
			throw new RuntimeException("Could not get messge Id");
		}
		system.setDirectorReminderMessageId(messageId);
		system.setDirectorReminderDaysBefore(directorReminder.getSystem()
				.getDirectorReminderDaysBefore());

		datastore.storeOrUpdate(system);
	}

	/**
	 * Save text to TText, use the given message Id to update TText.
	 * 
	 * @param messageId
	 * @param message
	 */
	private void saveText(Long messageId, String message) {
		TText tT = new TText();
		tT.setId(messageId);
		tT.setText(new Text(message));

		datastore.storeOrUpdate(tT);
	}

	/**
	 * Create new TText and return the message Id
	 * 
	 * @param message
	 * @return
	 */
	private Long saveText(String message) {
		TText tT = new TText();
		tT.setText(new Text(message));
		return datastore.store(tT).getId();
	}

	@Override
	public TEDTO loadFutureEventsByMonths(Integer months) {
		return getTEDTO.loadFutureEventsByMonths(months);
	}
}