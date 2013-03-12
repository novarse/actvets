package com.actvc.client;

// QUESTIONS
// task lists

import java.util.List;
import java.util.Map;

import com.actvc.client.ContentWidget.UibAreas;
import com.actvc.client.common.LoadingScreen;
import com.actvc.client.common.MyLog;
import com.actvc.client.controller.Controller;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.LoginInfo;
import com.actvc.client.event.GeneralFailHideLoading;
import com.actvc.client.event.GeneralFailure;
import com.actvc.client.event.LoadingScreenHideIt;
import com.actvc.client.event.LoadingScreenShowIt;
import com.actvc.client.event.ShowFutureEvent;
import com.actvc.client.event.ShowOnly;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ACTVC implements EntryPoint, ValueChangeHandler<String> {

	private final static String EVENTIDSTR = "eventid";
	private final static String CALENDAR = "calendar";
	private static ACTVC actVC;
	private static LoadingScreen loadingScreen = new LoadingScreen();
	private static LoginInfo loginInfo = null;

	public synchronized static ACTVC getInstance() {
		if (actVC == null)
			actVC = new ACTVC();
		return actVC;
	}

	private static final Controller controller = new Controller();
	private AppServiceDelegate service;
	private static long eventId = 0L;
	private static boolean showAllFutureEvents = false;
	private static boolean showHistoricData = false;
	private static boolean riderSearchLoaded = false;
	private static boolean eventsSearchLoaded = false;

	public void onModuleLoad() {
		preInit();

		service = new AppServiceDelegate();
		showAllFutureEvents = false;
		showHistoricData = false;

		History.addValueChangeHandler(this);
		History.fireCurrentHistoryState();

		UibACTVC uibACTVC = new UibACTVC();
		RootPanel.get().add(uibACTVC);

		wireEventsEtc();

		Map<String, List<String>> paramList = Window.Location.getParameterMap();
		if (paramList.containsKey(CALENDAR)) {
			controller.event(new ShowOnly(UibAreas.CALENDAR, Window.Location
					.getParameter(CALENDAR)));
		}
	}

	private void wireEventsEtc() {
		service.login();

	}

	private void preInit() {
		controller.addListener(GeneralFailure.class,
				new ControllerListener<GeneralFailure>() {

					@Override
					public void event(GeneralFailure event) {
						MyLog.log(event.getThrowable().getMessage());
						Window.alert("An error occurred. Try reloading the page");
					}
				});

		controller.addListener(GeneralFailHideLoading.class,
				new ControllerListener<GeneralFailHideLoading>() {

					@Override
					public void event(GeneralFailHideLoading event) {
						controller.event(new LoadingScreenHideIt());
						MyLog.log(event.getThrowable().getMessage());
						Window.alert("An error occurred. Try reloading the page");
					}
				});

		controller.addListener(LoadingScreenShowIt.class,
				new ControllerListener<LoadingScreenShowIt>() {

					@Override
					public void event(LoadingScreenShowIt event) {
						if (event.getMsg() != null) {
							loadingScreen.showIt(event.getMsg());
						} else {
							loadingScreen.showIt();
						}
					}
				});

		controller.addListener(LoadingScreenHideIt.class,
				new ControllerListener<LoadingScreenHideIt>() {

					@Override
					public void event(LoadingScreenHideIt event) {
						if (event.getMsg() != null) {
							MyLog.log(event.getMsg());
						}
						loadingScreen.hideIt();
					}
				});

	}

	public Controller getController() {
		return controller;
	}

	public static LoadingScreen getLoadingScreen() {
		return loadingScreen;
	}

	public static void setShowHistoricData(boolean showHistoricData) {
		ACTVC.showHistoricData = showHistoricData;
	}

	public static boolean isShowHistoricData() {
		return showHistoricData;
	}

	public static void setShowAllFutureEvents(boolean showAllFutureEvents) {
		ACTVC.showAllFutureEvents = showAllFutureEvents;
	}

	public static boolean isShowAllFutureEvents() {
		return showAllFutureEvents;
	}

	public static void setRiderSearchLoaded(boolean riderSearchLoaded) {
		ACTVC.riderSearchLoaded = riderSearchLoaded;
	}

	public static boolean isRiderSearchLoaded() {
		return riderSearchLoaded;
	}

	public static void setLoginInfo(LoginInfo loginInfo) {
		ACTVC.loginInfo = loginInfo;
	}

	public static LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public static void setEventsSearchLoaded(boolean eventsSearchLoaded) {
		ACTVC.eventsSearchLoaded = eventsSearchLoaded;
	}

	public static boolean isEventsSearchLoaded() {
		return eventsSearchLoaded;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		if (event.getValue() != null) {
			String[] inputArgs = event.getValue().split("&");
			for (String arg : inputArgs) {
				String[] input = arg.split("=");
				if (EVENTIDSTR.equalsIgnoreCase(input[0]) && input.length == 2) {
					try {
						eventId = Long.parseLong(input[1]);
						controller
								.event(new ShowFutureEvent(ACTVC.getEventId()));
						break;
					} catch (NumberFormatException e) {
						MyLog.log("Invalid Number Exception: " + input[1]);
					}
				}
			}

		}
	}

	public static String getEVENTIDSTR() {
		return EVENTIDSTR;
	}

	public static long getEventId() {
		return eventId;
	}

}
