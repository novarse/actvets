package com.actvc.client.entities;

import java.io.Serializable;

import com.google.code.twig.annotation.Id;

public class TS implements Serializable, TEntity {

	private static final int DEFAULT_HOURTOCHECKFORNEXTRACE = 11;

	private static final int DEFAULT_SERVERTIMEOFFSET = 0;

	/**
	 * Stores system variables
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String consumerToken;
	private String consumerSecret;
	private String token;
	private String tokenSecret;
	private String bitlyUserName;
	private String bitlyAPIKey;
	private String returnBitlyUrl;
	private int serverTimeOffset;
	private int hourToCheckForNextRace;
	private String urlHome;
	private String mainEmail;
	private boolean raceDayFileUploadUpdatesRiders;
	private Long directorReminderMessageId;
	private int directorReminderDaysBefore;

	// 'reset financial' date of riders - used by cron job
	private int resetFinancialMonth;
	private int resetFinancialDay;
	private boolean resetFinancialStatus;

	public TS() {

	}

	public TS(String consumerToken, String consumerSecret, String token,
			String tokenSecret) {
		super();
		this.consumerToken = consumerToken;
		this.consumerSecret = consumerSecret;
		this.token = token;
		this.tokenSecret = tokenSecret;
	}

	public TS(long singletonId) {
		this.serverTimeOffset = DEFAULT_SERVERTIMEOFFSET;
		this.hourToCheckForNextRace = DEFAULT_HOURTOCHECKFORNEXTRACE;
		this.id = singletonId;
	}

	public String getConsumerToken() {
		return consumerToken;
	}

	public void setConsumerToken(String consumerToken) {
		this.consumerToken = consumerToken;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public void setBitlyUserName(String bitlyUserName) {
		this.bitlyUserName = bitlyUserName;
	}

	public String getBitlyUserName() {
		return bitlyUserName;
	}

	public void setBitlyAPIKey(String bitlyAPIKey) {
		this.bitlyAPIKey = bitlyAPIKey;
	}

	public String getBitlyAPIKey() {
		return bitlyAPIKey;
	}

	@Override
	public String toExportForm() {
		return getConsumerToken() + TAB + getConsumerSecret() + TAB
				+ getToken() + TAB + getTokenSecret() + TAB
				+ getBitlyUserName() + TAB + getBitlyAPIKey() + TAB
				+ getReturnBitlyUrl();
	}

	public void setReturnBitlyUrl(String returnBitlyUrl) {
		this.returnBitlyUrl = returnBitlyUrl.trim();
	}

	public String getReturnBitlyUrl() {
		return returnBitlyUrl;
	}

	public boolean hasBitlyFields() {
		return getBitlyUserName() != null && getBitlyAPIKey() != null
				&& getBitlyUserName().length() > 0
				&& getBitlyAPIKey().length() > 0;
	}

	public void setServerTimeOffset(int serverTimeOffset) {
		this.serverTimeOffset = serverTimeOffset;
	}

	public int getServerTimeOffset() {
		return serverTimeOffset;
	}

	public void setHourToCheckForNextRace(int hourToCheckForNextRace) {
		this.hourToCheckForNextRace = hourToCheckForNextRace;
	}

	public int getHourToCheckForNextRace() {
		return hourToCheckForNextRace;
	}

	public boolean hasAllTweetFields() {
		return (consumerSecret != null && consumerToken != null
				&& token != null && tokenSecret != null);
	}

	public void setUrlHome(String urlHome) {
		this.urlHome = urlHome;
	}

	public String getUrlHome() {
		return urlHome;
	}

	public void setMainEmail(String mainEmail) {
		this.mainEmail = mainEmail;
	}

	public String getMainEmail() {
		return mainEmail;
	}

	public void setRaceDayFileUploadUpdatesRiders(
			boolean raceDayFileUploadUpdatesRiders) {
		this.raceDayFileUploadUpdatesRiders = raceDayFileUploadUpdatesRiders;
	}

	public boolean isRaceDayFileUploadUpdatesRiders() {
		return raceDayFileUploadUpdatesRiders;
	}

	public void setResetFinancialMonth(int month) {
		this.resetFinancialMonth = month;

	}

	public int getResetFinancialMonth() {
		return resetFinancialMonth;
	}

	public void setResetFinancialDay(int day) {
		this.resetFinancialDay = day;

	}

	public int getResetFinancialDay() {
		return resetFinancialDay;
	}

	public boolean isResetFinancialStatus() {
		return resetFinancialStatus;
	}

	public void setResetFinancialStatus(boolean resetFinancialStatus) {
		this.resetFinancialStatus = resetFinancialStatus;
	}

	public Long getDirectorReminderMessageId() {
		return directorReminderMessageId;
	}

	public void setDirectorReminderMessageId(Long directorReminderMessageId) {
		this.directorReminderMessageId = directorReminderMessageId;
	}

	public int getDirectorReminderDaysBefore() {
		return directorReminderDaysBefore;
	}

	public void setDirectorReminderDaysBefore(int directorReminderDaysBefore) {
		this.directorReminderDaysBefore = directorReminderDaysBefore;
	}
}
