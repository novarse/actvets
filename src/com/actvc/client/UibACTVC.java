package com.actvc.client;

import com.actvc.client.ContentWidget.UibAreas;
import com.actvc.client.common.LoadingScreen;
import com.actvc.client.common.MyUtils;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.TE;
import com.actvc.client.event.DoTest2Returned;
import com.actvc.client.event.DoTestReturned;
import com.actvc.client.event.GetTSForStartup;
import com.actvc.client.event.LoadEventDatesAndIdsReturned;
import com.actvc.client.event.LoadRiderNamesAndIdsReturned;
import com.actvc.client.event.LoadRiderNumbersAndIds;
import com.actvc.client.event.LoadingScreenHideIt;
import com.actvc.client.event.ShowFutureEvent;
import com.actvc.client.event.ShowOnly;
import com.actvc.client.event.ShowRaceResults;
import com.actvc.client.event.ShowRiderPoints;
import com.actvc.client.event.ShowRiderResults;
import com.actvc.client.event.TestReturned;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UibACTVC extends Composite {

	private static final String MISSING_HOME_URL_SET_IN_ADMIN_SYSTEM = "Missing home URL. Set in Admin/System";

	private static final String HTTP_PREFIX = "http://";

	private enum SearchTypes {
		EVENT, RIDERNAME, RIDERNUMBER
	};

	private static UibATCVCUiBinder uiBinder = GWT
			.create(UibATCVCUiBinder.class);

	interface UibATCVCUiBinder extends UiBinder<Widget, UibACTVC> {
	}

	protected static com.actvc.client.controller.Controller controller = ACTVC
			.getInstance().getController();

	private final LoadingScreen loadingScreen = ACTVC.getLoadingScreen();
	private final AppServiceDelegate service = new AppServiceDelegate();

	@UiField
	Anchor urlHomeBannerField;

	@UiField
	VerticalPanel contentPanel;

	@UiField
	ListBox eventSearchList;

	@UiField
	ListBox riderSearchList;

	@UiField
	ListBox numberSearchList;

	@UiField
	Anchor urlHomeField;

	@UiField
	Anchor gradeChangeField;

	@UiField
	Anchor eventCalendarField;

	@UiField
	Anchor adminAnchorField;

	@UiField
	Anchor riderPoints;

	@UiField
	static Anchor testField;

	@UiField
	static Anchor test2Field;

	@UiField
	CheckBox showAllFutureEventsField;

	@UiField
	CheckBox showHistoricDataField;

	protected boolean numberSearchLoaded = false;

	public UibACTVC() {
		initWidget(uiBinder.createAndBindUi(this));

		contentPanel.add(new ContentFuture("Future Events"));
		contentPanel.add(new ContentHistoric("Historic Events"));
		contentPanel.add(new ContentEventDetail("Event Details"));
		contentPanel.add(new ContentRaceResults("Race Results"));
		contentPanel.add(new ContentRiderResults("Rider Results"));
		contentPanel.add(new ContentAdmin("Administration"));
		contentPanel.add(new ContentGradeChange("Grade Change Request"));
		contentPanel.add(new ContentRiderPoints("Rider Seasonal Points"));
		contentPanel.add(new ContentCalendar("Event Calendar"));
		wire();
	}

	private void wire() {
		eventSearchList.setWidth("150px");
		eventSearchList.setStylePrimaryName("fontsize");
		riderSearchList.setWidth("150px");
		riderSearchList.setStylePrimaryName("fontsize");
		numberSearchList.setWidth("150px");
		numberSearchList.setStylePrimaryName("fontsize");
		testField.setVisible(false);
		test2Field.setVisible(false);
		contentPanel.setStylePrimaryName("content");
		showAllFutureEventsField
				.setTitle("Check this to add to the Event Search box the dates for all future events");
		showHistoricDataField
				.setTitle("Check this to add to the Event Search box the dates for all historic events");
		showAllFutureEventsField.setStylePrimaryName("cursorhelp");
		showHistoricDataField.setStylePrimaryName("cursorhelp");

		setURLHome("httphomeurl");

		adminAnchorField.setStylePrimaryName("anchor");
		gradeChangeField.setStylePrimaryName("anchor");

		adminAnchorField.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				controller.event(new ShowOnly(UibAreas.ADMIN));
			}
		});

		gradeChangeField.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				controller.event(new ShowOnly(UibAreas.CHANGEGRADE));
			}
		});

		eventCalendarField.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				controller.event(new ShowOnly(UibAreas.CALENDAR));
			}
		});

		riderPoints.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				controller.event(new ShowRiderPoints());
			}
		});

		testField.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				service.doTest();
			}
		});

		test2Field.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				service.doTest2();
			}
		});

		eventSearchList.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (!ACTVC.isEventsSearchLoaded()) {
					service.loadEventDatesAndIds(ACTVC.isShowHistoricData(),
							ACTVC.isShowAllFutureEvents());
				}
			}
		});

		riderSearchList.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (!ACTVC.isRiderSearchLoaded()) {
					service.loadRiderNamesAndIds();
				}
			}
		});

		numberSearchList.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				if (!numberSearchLoaded) {
					service.loadRiderNumbersAndIds();
				}
			}
		});

		eventSearchList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				selectedDate();
			}
		});

		eventSearchList.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					selectedDate();
				}
			}
		});

		riderSearchList.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				selectedRiderName();
			}
		});

		riderSearchList.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					selectedRiderName();
				}
			}
		});

		numberSearchList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				selectedRiderNumber();
			}
		});

		numberSearchList.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					selectedRiderNumber();
				}
			}
		});

		ClickHandler eventsHistoricAndFutureClickHandler = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ACTVC.setShowHistoricData(showHistoricDataField.getValue());
				ACTVC.setShowAllFutureEvents(showAllFutureEventsField
						.getValue());
				loadingScreen.center("Reloading Event Dates...");
				service.loadEventDatesAndIds(ACTVC.isShowHistoricData(),
						ACTVC.isShowAllFutureEvents());
			}
		};

		showHistoricDataField
				.addClickHandler(eventsHistoricAndFutureClickHandler);

		showAllFutureEventsField
				.addClickHandler(eventsHistoricAndFutureClickHandler);

		controller.addListener(TestReturned.class,
				new ControllerListener<TestReturned>() {

					@Override
					public void event(TestReturned result) {
						System.out.println("test returned");
					}
				});

		controller.addListener(LoadEventDatesAndIdsReturned.class,
				new ControllerListener<LoadEventDatesAndIdsReturned>() {

					@Override
					public void event(LoadEventDatesAndIdsReturned results) {
						eventSearchList.clear();
						eventSearchList.addItem("Select an Event");
						for (TE e : results.getEvents()) {
							eventSearchList.addItem(MyUtils.getDateStr(e
									.getDate()), e.getId().toString());
						}
						ACTVC.setEventsSearchLoaded(true);
					}
				});

		controller.addListener(LoadRiderNamesAndIdsReturned.class,
				new ControllerListener<LoadRiderNamesAndIdsReturned>() {

					@Override
					public void event(LoadRiderNamesAndIdsReturned results) {
						riderSearchList.clear();
						riderSearchList.addItem("Select a Rider");
						for (String s : results.getRidersList()) {
							String[] items = s.split(";");
							riderSearchList.addItem(items[0], items[1]);
						}

						ACTVC.setRiderSearchLoaded(true);
					}
				});

		controller.addListener(LoadRiderNumbersAndIds.class,
				new ControllerListener<LoadRiderNumbersAndIds>() {

					@Override
					public void event(LoadRiderNumbersAndIds result) {
						numberSearchList.clear();
						numberSearchList.addItem("Select a Number");
						for (String s : result.getRaceNumberList()) {
							String[] items = s.split(";");
							numberSearchList.addItem(items[0] + " - "
									+ items[2], items[1]);
						}

						numberSearchLoaded = true;
					}

				});

		controller.addListener(DoTestReturned.class,
				new ControllerListener<DoTestReturned>() {

					@Override
					public void event(DoTestReturned event) {
						controller.event(new LoadingScreenHideIt());
						if (event.getResult() != null) {
							Window.alert(event.getResult());
						} else {
							Window.alert("Test done");
						}
					}
				});

		controller.addListener(DoTest2Returned.class,
				new ControllerListener<DoTest2Returned>() {

					@Override
					public void event(DoTest2Returned event) {
						controller.event(new LoadingScreenHideIt());
						if (event.getResult() != null) {
							Window.alert(event.getResult());
						} else {
							Window.alert("Test2 done");
						}
					}
				});

		controller.addListener(GetTSForStartup.class,
				new ControllerListener<GetTSForStartup>() {

					@Override
					public void event(GetTSForStartup event) {
						setURLHome(event.getSystem().getUrlHome());
					}
				});

		init();

	}

	private void setURLHome(String urlHome) {

		if (urlHome == null || urlHome.isEmpty()) {
			Window.alert("Home URL is missing. Set this in Admin/System area.");
			urlHome = MISSING_HOME_URL_SET_IN_ADMIN_SYSTEM;
		}
		if (!urlHome.toLowerCase().contains(HTTP_PREFIX)) {
			urlHome = HTTP_PREFIX + urlHome;
		}

		urlHomeBannerField.setHref(urlHome);
		urlHomeBannerField.setTitle(urlHome);
		urlHomeField.setHref(urlHome);
		urlHomeField.setTitle(urlHome);
	}

	private void init() {
		eventSearchList.addItem("Select an Event");
		riderSearchList.addItem("Select a Rider");
		numberSearchList.addItem("Select a Number");
		service.getTS(new GetTSForStartup());
	}

	private void selectedDate() {
		resetOtherSearchs(SearchTypes.EVENT);
		if (eventSearchList.getSelectedIndex() == 0)
			return;

		if (MyUtils.futureDate(eventSearchList.getItemText(eventSearchList
				.getSelectedIndex()))) {
			controller.event(new ShowFutureEvent(Long.parseLong(eventSearchList
					.getValue(eventSearchList.getSelectedIndex()))));
		} else {
			controller.event(new ShowRaceResults(Long.parseLong(eventSearchList
					.getValue(eventSearchList.getSelectedIndex()))));
		}
	}

	protected void resetOtherSearchs(SearchTypes currentSearch) {
		if (!currentSearch.equals(SearchTypes.EVENT))
			eventSearchList.setSelectedIndex(0);
		if (!currentSearch.equals(SearchTypes.RIDERNAME))
			riderSearchList.setSelectedIndex(0);
		if (!currentSearch.equals(SearchTypes.RIDERNUMBER))
			numberSearchList.setSelectedIndex(0);
	}

	private void selectedRiderName() {
		resetOtherSearchs(SearchTypes.RIDERNAME);
		if (riderSearchList.getSelectedIndex() == 0)
			return;
		controller.event(new ShowRiderResults(Long.parseLong(riderSearchList
				.getValue(riderSearchList.getSelectedIndex()))));
	}

	private void selectedRiderNumber() {
		resetOtherSearchs(SearchTypes.RIDERNUMBER);
		if (numberSearchList.getSelectedIndex() == 0)
			return;
		controller.event(new ShowRiderResults(Long.parseLong(numberSearchList
				.getValue(numberSearchList.getSelectedIndex()))));
	}

}
