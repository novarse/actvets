package com.actvc.client.edit;

import com.actvc.client.common.MyDate;
import com.actvc.client.common.MyLog;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.edit.sections.DirectorReminderSection;
import com.actvc.client.entities.TS;
import com.actvc.client.entities.TSDao;
import com.actvc.client.event.GetTSDaoForSystem;
import com.actvc.client.event.GetTSForStartup;
import com.actvc.client.event.InitSystem;
import com.actvc.client.event.LoadingScreenHideIt;
import com.actvc.client.event.ResetBitlyFailed;
import com.actvc.client.event.ResetBitlyReturned;
import com.actvc.client.event.ResetTwitterFailed;
import com.actvc.client.event.ResetTwitterReturned;
import com.actvc.client.event.SaveBitlySettingsFailed;
import com.actvc.client.event.SaveBitlySettingsReturned;
import com.actvc.client.event.SaveDirectorReminderFailed;
import com.actvc.client.event.SaveDirectorReminderReturned;
import com.actvc.client.event.SaveGeneralSystemFailed;
import com.actvc.client.event.SaveGeneralSystemReturned;
import com.actvc.client.event.SaveResetFinancialsFailed;
import com.actvc.client.event.SaveResetFinancialsReturned;
import com.actvc.client.event.SaveTwitterFailed;
import com.actvc.client.event.SaveTwitterSettingsReturned;
import com.actvc.client.event.SubmitTweetFailed;
import com.actvc.client.event.SubmitTweetReturned;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EditSystem extends EditBase {
	private static final String TITLE_SERVERTIMEOFFSET = "This value is added to the server time to adjust for differences between server and local time.";
	private static final String BITLY_LINK = "https://docs.google.com/document/d/1gYyWNlhgNuoCNtIryZ8jPHhaBrXtyEqNivnC7JS0TQw/edit?hl=en&authkey=CPCeoJUK";
	private static final String TWEET_SETUP_MSG = "Click this link to see an explanation of these fields and how to obtain the required values";
	private static final String TWEET_SETUP_GUIDE = "https://docs.google.com/document/edit?id=1qzP6NOi-Yenmz3sb4L_fYSm2tTMIWGZ3O5Tkv0eTriE&hl=en&authkey=CIWYuYYB";
	private static final int TWEET_MAX = 140;
	private VerticalPanel twitterSetupPanel;
	private VerticalPanel bitlySetupPanel;
	private VerticalPanel submitPanel;
	private TextBox consumerTokenEd;
	private TextBox consumerSecretEd;
	private TextBox accessTokenEd;
	private TextBox accessTokenSecretEd;
	private FlexTable editTwitterSettingsTbl;
	private Button saveTwitterSettingsBtn;
	private TextBox bitlyUserNameEd;
	private TextBox bitlyApiKeyEd;
	private FlexTable editBitlySettingsTbl;
	private Button saveBitlySettingsBtn;
	private TextArea tweetEd;
	private Button submitTweetBtn;
	private Button resetTwitterBtn;
	private Label wordCount;
	private FlexTable tweetMsgTbl;
	private Anchor helpTweetSetup;
	private Anchor bitlyLink;
	private Button resetBitlyBtn;
	private VerticalPanel bitlyFieldsPanel;
	private VerticalPanel generalSetupPanel;
	private FlexTable bitlyResetTbl;
	private TextBox returnUrlEd;
	private Button saveGeneralBtn;
	private TextBox checkHourEd;
	private TextBox serverTimeOffsetEd;
	private TextBox urlHomeEd;
	private TextBox mainEmailEd;

	// 'reset financial' date of riders - used by cron job
	private VerticalPanel rfPanel;
	private FlexTable rfTbl;
	private CheckBox rfChk;
	private MyDate rfDate;
	private Button rfSaveBtn;

	private DirectorReminderSection directorReminderSection;
	private static TSDao systemDao = new TSDao();

	@Override
	void buildContent() {
		generalSetupPanel = new VerticalPanel();
		twitterSetupPanel = new VerticalPanel();
		bitlySetupPanel = new VerticalPanel();
		submitPanel = new VerticalPanel();
		consumerTokenEd = new TextBox();
		consumerSecretEd = new TextBox();
		accessTokenEd = new TextBox();
		accessTokenSecretEd = new TextBox();
		editTwitterSettingsTbl = new FlexTable();
		saveTwitterSettingsBtn = new Button("Save Twitter Details");
		bitlyUserNameEd = new TextBox();
		bitlyApiKeyEd = new TextBox();
		editBitlySettingsTbl = new FlexTable();
		saveBitlySettingsBtn = new Button("Save bit.ly Details");
		tweetEd = new TextArea();
		submitTweetBtn = new Button("Submit Tweet");
		resetTwitterBtn = new Button("Reset Twitter Authentication");
		wordCount = new Label("140");
		tweetMsgTbl = new FlexTable();
		helpTweetSetup = new Anchor(TWEET_SETUP_MSG, TWEET_SETUP_GUIDE);
		bitlyLink = new Anchor(
				"Click to see help on setting up authentication.", BITLY_LINK);
		resetBitlyBtn = new Button("Reset bit.ly Authentication");
		returnUrlEd = new TextBox();
		bitlyFieldsPanel = new VerticalPanel();
		serverTimeOffsetEd = new TextBox();
		checkHourEd = new TextBox();
		urlHomeEd = new TextBox();
		mainEmailEd = new TextBox();

		rfPanel = new VerticalPanel();
		rfTbl = new FlexTable();
		rfChk = new CheckBox();
		rfDate = new MyDate();
		rfSaveBtn = new Button("Save Details");

		buildGeneralSetupPanel();

		buildTwitterPanel();

		buildBitlyPanel();

		buildSubmitPanel();

		buildResetFinancial();

		directorReminderSection = new DirectorReminderSection(true, true);

		contentPanel.add(generalSetupPanel);
		contentPanel.add(twitterSetupPanel);
		contentPanel.add(submitPanel);
		contentPanel.add(bitlySetupPanel);
		contentPanel.add(directorReminderSection);
		contentPanel.add(rfPanel);
	}

	private void buildResetFinancial() {
		rfPanel.add(new HTML(
				"<b>Set date for Resetting Financial Status of Riders</b>"));
		rfTbl.setWidget(0, 0, new Label(
				"Reset Financial details on the given date?"));
		rfTbl.setWidget(0, 1, rfChk);
		rfTbl.setWidget(1, 0, rfDate);
		rfTbl.setWidget(2, 0, rfSaveBtn);
		rfPanel.add(rfTbl);
	}

	private void buildSubmitPanel() {
		submitPanel.add(new Label(
				"Enter a message to be sent to the tweet feed"));
		tweetMsgTbl.setWidget(0, 0, tweetEd);
		tweetMsgTbl.setWidget(0, 1, wordCount);
		submitPanel.add(tweetMsgTbl);
		submitPanel.add(submitTweetBtn);
		submitPanel.add(new HTML("&nbsp;"));
		submitPanel.add(resetTwitterBtn);
		submitPanel.add(new HTML("<hr>"));
	}

	private void buildTwitterPanel() {
		editTwitterSettingsTbl.setWidget(0, 0, new Label("Consumer Key"));
		editTwitterSettingsTbl.setWidget(0, 1, consumerTokenEd);
		editTwitterSettingsTbl.setWidget(1, 0, new Label("Consumer Secret"));
		editTwitterSettingsTbl.setWidget(1, 1, consumerSecretEd);
		editTwitterSettingsTbl.setWidget(2, 0, new Label("Access Token"));
		editTwitterSettingsTbl.setWidget(2, 1, accessTokenEd);
		editTwitterSettingsTbl.setWidget(3, 0, new Label("Access Secret"));
		editTwitterSettingsTbl.setWidget(3, 1, accessTokenSecretEd);

		twitterSetupPanel
				.add(new Label(
						"Enter the following details to authenticate with the Vikings twitter feed"));
		twitterSetupPanel.add(helpTweetSetup);
		twitterSetupPanel.add(editTwitterSettingsTbl);
		twitterSetupPanel.add(saveTwitterSettingsBtn);
		twitterSetupPanel.add(new HTML("<hr>"));
	}

	private void buildBitlyPanel() {
		editBitlySettingsTbl.setWidget(0, 0, new Label("bit.ly username"));
		editBitlySettingsTbl.setWidget(0, 1, bitlyUserNameEd);
		editBitlySettingsTbl.setWidget(1, 0, new Label("bit.ly API key"));
		editBitlySettingsTbl.setWidget(1, 1, bitlyApiKeyEd);
		bitlyFieldsPanel.add(bitlyLink);
		bitlyFieldsPanel.add(editBitlySettingsTbl);
		bitlyFieldsPanel.add(saveBitlySettingsBtn);

		bitlyResetTbl = new FlexTable();
		bitlyResetTbl.setWidget(0, 0, new Label("Currently setup ok"));
		bitlyResetTbl.setWidget(0, 1, resetBitlyBtn);

		bitlySetupPanel
				.add(new HTML(
						"<b>bit.ly Setup.</b><br>bit.ly is used to shorten URL links so that they fit into a tweet message."));
		bitlySetupPanel.add(bitlyFieldsPanel);
		bitlySetupPanel.add(bitlyResetTbl);

	}

	private void buildGeneralSetupPanel() {
		FlexTable generalTbl = new FlexTable();
		generalTbl.setWidget(0, 0, new Label("Home Page URL"));
		generalTbl.setWidget(0, 1, urlHomeEd);
		generalTbl.setWidget(1, 0, new Label("Main Email"));
		generalTbl.setWidget(1, 1, mainEmailEd);
		generalTbl.setWidget(2, 0, new Label("Return bit.ly Server name"));
		generalTbl.setWidget(2, 1, returnUrlEd);
		generalTbl.setWidget(3, 0, new Label("Server Time Offset"));
		generalTbl.setWidget(3, 1, serverTimeOffsetEd);
		generalTbl.setWidget(4, 0, new Label("Hour to Tweet race event"));
		generalTbl.setWidget(4, 1, checkHourEd);

		urlHomeEd
				.setTitle("Enter the URL for the Home Page link used on this site");
		mainEmailEd
				.setTitle("This is the main email address used by this site to represent the club");
		returnUrlEd
				.setTitle("If this is set then the twitter feed in the 'Next Race' message will link to the specified server. Typically, use the domain name of this site (without the 'http://').");
		serverTimeOffsetEd.setTitle(TITLE_SERVERTIMEOFFSET);
		saveGeneralBtn = new Button("Save General Settings");

		generalSetupPanel.add(new HTML("<h3>General System Settings</h3>"));
		generalSetupPanel.add(generalTbl);
		generalSetupPanel.add(saveGeneralBtn);
		generalSetupPanel.add(new HTML("<hr>"));
	}

	@Override
	void resetForm() {
		// TODO Auto-generated method stub

	}

	@Override
	void wireEtc() {
		submitPanel.setVisible(false);
		twitterSetupPanel.setVisible(false);
		bitlyFieldsPanel.setVisible(false);
		tweetEd.setStylePrimaryName("tweetmessageed");
		wordCount.setStylePrimaryName("wordcount");
		consumerTokenEd.setStylePrimaryName("twitterfields");
		consumerSecretEd.setStylePrimaryName("twitterfields");
		accessTokenEd.setStylePrimaryName("twitterfields");
		accessTokenSecretEd.setStylePrimaryName("twitterfields");
		bitlyApiKeyEd.setStylePrimaryName("twitterfields");
		bitlyUserNameEd.setStylePrimaryName("twitterfields");

		generalSetupPanel.setStylePrimaryName("width100percentg");
		submitPanel.setStylePrimaryName("width100percentg");
		twitterSetupPanel.setStylePrimaryName("width100percentg");
		bitlyFieldsPanel.setStylePrimaryName("width100percentg");
		submitPanel.setStylePrimaryName("width100percentg");

		rfDate.setYearVisible(false);

		directorReminderSection.getSaveBtn().addClickHandler(
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						TS reminder = new TS();
						TSDao reminderDao = new TSDao();
						reminderDao.setDirectorMessage(directorReminderSection
								.getMessage().getValue());

						reminder.setDirectorReminderMessageId(systemDao
								.getSystem().getDirectorReminderMessageId());
						try {
							int daysBefore = Integer
									.parseInt(directorReminderSection
											.getDaysBeforeEvent().getText());
							if (daysBefore < 0) {
								throw new RuntimeException();
							}
							reminder.setDirectorReminderDaysBefore(daysBefore);
						} catch (RuntimeException e) {
							Window.alert("Enter the number of days before the event for when to email the director.");
							directorReminderSection.getDaysBeforeEvent()
									.setFocus(true);

							return;
						}
						directorReminderSection.getSaveBtn().setEnabled(false);
						reminderDao.setSystem(reminder);
						service.saveDirectorReminder(reminderDao);
					}
				});

		rfChk.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (rfChk.getValue()) {
					rfDate.setEnabled(true);
				} else {
					rfDate.setEnabled(false);
				}
			}
		});

		rfSaveBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				TS rf = new TS();
				rf.setResetFinancialStatus(rfChk.getValue());
				rf.setResetFinancialMonth(rfDate.getDate().getMonth());
				rf.setResetFinancialDay(rfDate.getDate().getDate());
				rfSaveBtn.setEnabled(false);

				service.saveResetFinancialDate(rf);

			}
		});

		saveGeneralBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				checkGeneralSettings();
			}

		});

		resetTwitterBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (Window
						.confirm("Are you sure? Authentication codes will be required to re-connect to the twitter feed.")) {
					resetTwitterBtn.setEnabled(false);
					service.resetTwitter();
				}
			}
		});

		tweetEd.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				wordCount.setText(Integer.toString(TWEET_MAX
						- tweetEd.getText().length()));
			}
		});

		resetBitlyBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (Window
						.confirm("Are you sure you want to clear authentication details for bit.ly?")) {

					resetBitlyBtn.setEnabled(false);
					service.resetBitly();
				}
			}
		});

		submitTweetBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (tweetEd.getText().trim().length() == 0) {
					Window.alert("Enter a tweet message");
					tweetEd.setFocus(true);
					return;
				}
				if (tweetEd.getText().trim().length() > 140) {
					tweetEd.setText(tweetEd.getText().trim().substring(0, 139));
				}
				submitTweetBtn.setEnabled(false);
				loadingScreen.center("Sending tweet...");
				service.submitTweet(tweetEd.getText().trim());
			}
		});

		saveBitlySettingsBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (bitlyUserNameEd.getText().trim().length() == 0) {
					Window.alert("Enter a Bit.ly User Name");
					bitlyUserNameEd.setFocus(true);
					return;
				}
				if (bitlyApiKeyEd.getText().trim().length() == 0) {
					Window.alert("Enter a Bit.ly API Key");
					bitlyApiKeyEd.setFocus(true);
					return;
				}

				TS bitly = new TS();
				bitly.setBitlyUserName(bitlyUserNameEd.getText().trim());
				bitly.setBitlyAPIKey(bitlyApiKeyEd.getText().trim());
				saveBitlySettingsBtn.setEnabled(false);
				service.saveBitlySettings(bitly);
			}
		});

		saveTwitterSettingsBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (consumerTokenEd.getText().trim().length() == 0) {
					Window.alert("Enter a Consumer Token");
					consumerTokenEd.setFocus(true);
					return;
				}
				if (consumerSecretEd.getText().trim().length() == 0) {
					Window.alert("Enter a Consumer Secret");
					consumerSecretEd.setFocus(true);
					return;
				}
				if (accessTokenEd.getText().trim().length() == 0) {
					Window.alert("Enter a Access Token");
					accessTokenEd.setFocus(true);
					return;
				}
				if (accessTokenSecretEd.getText().trim().length() == 0) {
					Window.alert("Enter a Access Token Secret");
					accessTokenSecretEd.setFocus(true);
					return;
				}

				TS twitter = new TS(consumerTokenEd.getText().trim(),
						consumerSecretEd.getText().trim(), accessTokenEd
								.getText().trim(), accessTokenSecretEd
								.getText().trim());
				saveTwitterSettingsBtn.setEnabled(false);
				service.saveTwitterSettings(twitter);
			}
		});

		controller.addListener(SaveDirectorReminderFailed.class,
				new ControllerListener<SaveDirectorReminderFailed>() {

					@Override
					public void event(SaveDirectorReminderFailed event) {
						Window.alert("Saving 'Director Reminder failed");
						controller.event(new LoadingScreenHideIt());
						directorReminderSection.getSaveBtn().setEnabled(true);
					}

				});

		controller.addListener(SaveDirectorReminderReturned.class,
				new ControllerListener<SaveDirectorReminderReturned>() {

					@Override
					public void event(SaveDirectorReminderReturned event) {
						Window.alert("Saving 'Director Reminder' succeeded");
						controller.event(new LoadingScreenHideIt());
						directorReminderSection.getSaveBtn().setEnabled(true);
					}
				});

		controller.addListener(SaveResetFinancialsFailed.class,
				new ControllerListener<SaveResetFinancialsFailed>() {

					@Override
					public void event(SaveResetFinancialsFailed event) {
						Window.alert("Saving 'Reset Financial' failed");
						controller.event(new LoadingScreenHideIt());
						rfSaveBtn.setEnabled(true);
					}

				});

		controller.addListener(SaveResetFinancialsReturned.class,
				new ControllerListener<SaveResetFinancialsReturned>() {

					@Override
					public void event(SaveResetFinancialsReturned event) {
						Window.alert("Saving 'Reset Financial' succeeded");
						controller.event(new LoadingScreenHideIt());
						rfSaveBtn.setEnabled(true);
					}
				});

		controller.addListener(ResetTwitterFailed.class,
				new ControllerListener<ResetTwitterFailed>() {

					@Override
					public void event(ResetTwitterFailed result) {
						resetTwitterBtn.setEnabled(true);
						service.getTS(new GetTSDaoForSystem());
					}
				});

		controller.addListener(ResetTwitterReturned.class,
				new ControllerListener<ResetTwitterReturned>() {

					@Override
					public void event(ResetTwitterReturned result) {
						resetTwitterBtn.setEnabled(true);
						service.getTS(new GetTSDaoForSystem());
					}
				});

		controller.addListener(InitSystem.class,
				new ControllerListener<InitSystem>() {

					@Override
					public void event(InitSystem event) {
						service.getTS(new GetTSDaoForSystem());
					}

				});

		controller.addListener(GetTSDaoForSystem.class,
				new ControllerListener<GetTSDaoForSystem>() {

					@Override
					public void event(GetTSDaoForSystem result) {

						if (result.getSystemDao() != null) {
							systemDao = result.getSystemDao();

							// general settings
							{
								urlHomeEd.setText(systemDao.getSystem()
										.getUrlHome());

								mainEmailEd.setText(systemDao.getSystem()
										.getMainEmail());
								returnUrlEd.setText(systemDao.getSystem()
										.getReturnBitlyUrl());
								serverTimeOffsetEd.setText(Integer
										.toString(systemDao.getSystem()
												.getServerTimeOffset()));
								checkHourEd.setText(Integer.toString(systemDao
										.getSystem()
										.getHourToCheckForNextRace()));
							}

							// twitter
							{
								if (!systemDao.getSystem().hasAllTweetFields()) {
									showSetupPanel();
								} else {
									showSubmitPanel();
								}
							}

							// bit.ly
							{
								if (result.getSystemDao().getSystem()
										.hasBitlyFields()) {
									showBitlySetup(false);
								} else {
									showBitlySetup(true);
								}
							}

							// director reminder
							{
								directorReminderSection.getMessage().setText(
										systemDao.getDirectorMessage());
								directorReminderSection
										.getDaysBeforeEvent()
										.setText(
												Integer.toString(systemDao
														.getSystem()
														.getDirectorReminderDaysBefore()));
								directorReminderSection.getMessage()
										.setCursorPos(0);
							}

							// reset financials
							{
								rfChk.setValue(systemDao.getSystem()
										.isResetFinancialStatus());
								rfDate.setMonth(systemDao.getSystem()
										.getResetFinancialMonth());
								rfDate.setDayOfMonth(systemDao.getSystem()
										.getResetFinancialDay());
								rfDate.setEnabled(rfChk.getValue());
							}

						}
					}

				});

		controller.addListener(SaveTwitterFailed.class,
				new ControllerListener<SaveTwitterFailed>() {

					@Override
					public void event(SaveTwitterFailed event) {
						saveTwitterSettingsBtn.setEnabled(true);
						Window.alert("Error occured saving Twitter details. Check authorization values.");
					}
				});

		controller.addListener(SaveTwitterSettingsReturned.class,
				new ControllerListener<SaveTwitterSettingsReturned>() {

					@Override
					public void event(SaveTwitterSettingsReturned event) {
						saveTwitterSettingsBtn.setEnabled(true);
						showSubmitPanel();
					}
				});

		controller.addListener(SaveBitlySettingsFailed.class,
				new ControllerListener<SaveBitlySettingsFailed>() {

					@Override
					public void event(SaveBitlySettingsFailed event) {
						Window.alert("Error occured saving Bit.ly details");
						saveBitlySettingsBtn.setEnabled(true);
					}
				});

		controller.addListener(SaveBitlySettingsReturned.class,
				new ControllerListener<SaveBitlySettingsReturned>() {

					@Override
					public void event(SaveBitlySettingsReturned event) {
						saveBitlySettingsBtn.setEnabled(true);
						service.getTS(new GetTSDaoForSystem());
					}
				});

		controller.addListener(SubmitTweetFailed.class,
				new ControllerListener<SubmitTweetFailed>() {

					@Override
					public void event(SubmitTweetFailed result) {
						submitTweetBtn.setEnabled(true);
						loadingScreen.hide();
						Window.alert("Tweet Failed");
					}
				});

		controller.addListener(SubmitTweetReturned.class,
				new ControllerListener<SubmitTweetReturned>() {

					@Override
					public void event(SubmitTweetReturned result) {
						submitTweetBtn.setEnabled(true);
						loadingScreen.hide();
						tweetEd.setText("");
						Window.alert("Tweet sent");
					}
				});

		controller.addListener(ResetBitlyFailed.class,
				new ControllerListener<ResetBitlyFailed>() {

					@Override
					public void event(ResetBitlyFailed result) {
						resetBitlyBtn.setEnabled(true);
						MyLog.log(result.getCaught().getMessage());
					}
				});

		controller.addListener(ResetBitlyReturned.class,
				new ControllerListener<ResetBitlyReturned>() {

					@Override
					public void event(ResetBitlyReturned result) {
						resetBitlyBtn.setEnabled(true);
						service.getTS(new GetTSDaoForSystem());
					}
				});

		controller.addListener(SaveGeneralSystemFailed.class,
				new ControllerListener<SaveGeneralSystemFailed>() {

					@Override
					public void event(SaveGeneralSystemFailed result) {
						saveGeneralBtn.setEnabled(true);
					}
				});

		controller.addListener(SaveGeneralSystemReturned.class,
				new ControllerListener<SaveGeneralSystemReturned>() {

					@Override
					public void event(SaveGeneralSystemReturned result) {
						saveGeneralBtn.setEnabled(true);
						service.getTS(new GetTSForStartup());
						Window.alert("Settings saved");
					}
				});

	}

	protected void showBitlySetup(boolean show) {
		if (show) {
			bitlyUserNameEd.setText("");
			bitlyApiKeyEd.setText("");
		}
		bitlyFieldsPanel.setVisible(show);
		bitlyResetTbl.setVisible(!show);
	}

	protected void showSubmitPanel() {
		submitPanel.setVisible(true);
		twitterSetupPanel.setVisible(false);
	}

	protected void showSetupPanel() {
		submitPanel.setVisible(false);
		consumerTokenEd.setText("");
		consumerSecretEd.setText("");
		accessTokenEd.setText("");
		accessTokenSecretEd.setText("");

		twitterSetupPanel.setVisible(true);
	}

	private void checkGeneralSettings() {
		int utcOffset;
		int checkHour;
		if (urlHomeEd.getText().trim().isEmpty()) {
			Window.alert("Enter a URL for the Home Page link");
			urlHomeEd.setFocus(true);
			return;
		}
		if (mainEmailEd.getText().trim().isEmpty()) {
			Window.alert("Enter the main Email Address for this site");
			mainEmailEd.setFocus(true);
			return;
		}
		try {
			utcOffset = Integer.parseInt(serverTimeOffsetEd.getText());
		} catch (NumberFormatException e) {
			Window.alert("Invalid UTC Offset number. Valid numbers from -12 to +14");
			serverTimeOffsetEd.setFocus(true);
			return;
		}
		try {
			checkHour = Integer.parseInt(checkHourEd.getText());
		} catch (NumberFormatException e) {
			Window.alert("Invalid Check Hour. Valid numbers from 0 to 23");
			checkHourEd.setFocus(true);
			return;
		}
		saveGeneralBtn.setEnabled(false);
		service.saveGeneralSystem(urlHomeEd.getText().trim(), mainEmailEd
				.getText().trim(), returnUrlEd.getText().trim(), utcOffset,
				checkHour);
	}
}
