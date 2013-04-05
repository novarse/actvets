package com.actvc.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.actvc.client.common.MyLog;
import com.actvc.client.controller.Controller;
import com.actvc.client.controller.Event;
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
import com.actvc.client.event.ChangeGradeFailed;
import com.actvc.client.event.ChangeGradeReturned;
import com.actvc.client.event.CheckGradeChangeDetailsFailed;
import com.actvc.client.event.CheckGradeChangeDetailsReturned;
import com.actvc.client.event.DeleteAdminReturned;
import com.actvc.client.event.DeleteEventReturned;
import com.actvc.client.event.DeleteGradeChangeRequestReturned;
import com.actvc.client.event.DeleteLocationReturned;
import com.actvc.client.event.DeletePendingRiderReturned;
import com.actvc.client.event.DeleteRaceHistoryReturned;
import com.actvc.client.event.DeleteRiderReturned;
import com.actvc.client.event.DeleteUtilReturned;
import com.actvc.client.event.EditGetRidersByNumberReturned;
import com.actvc.client.event.GeneralFailHideLoading;
import com.actvc.client.event.GeneralFailure;
import com.actvc.client.event.GetAdminReturned;
import com.actvc.client.event.GetEditEventDescReturned;
import com.actvc.client.event.GetEditEventReturned;
import com.actvc.client.event.GetEditRiderReturned;
import com.actvc.client.event.GetEventDescByEventIdReturned;
import com.actvc.client.event.GetEventDescReturned;
import com.actvc.client.event.GetFutureEventReturned;
import com.actvc.client.event.GetLocationReturned;
import com.actvc.client.event.GetNextNumberReturned;
import com.actvc.client.event.GetPendingRaceHistoryReturned;
import com.actvc.client.event.GetRaceEventResultsReturned;
import com.actvc.client.event.GetRaceHistoryReturned;
import com.actvc.client.event.GetRiderHistoryByIdReturned;
import com.actvc.client.event.GetRiderPointsReturned;
import com.actvc.client.event.GetTSDaoForSystem;
import com.actvc.client.event.GetTSForGeneralCase;
import com.actvc.client.event.GetTSForStartup;
import com.actvc.client.event.GetTSForUtilities;
import com.actvc.client.event.LoadActiveEventDescReturned;
import com.actvc.client.event.LoadAdminReturned;
import com.actvc.client.event.LoadDirectorsReturned;
import com.actvc.client.event.LoadEventDatesAndIdsForEditReturned;
import com.actvc.client.event.LoadEventDatesAndIdsReturned;
import com.actvc.client.event.LoadEventDescReturned;
import com.actvc.client.event.LoadEventTypesReturned;
import com.actvc.client.event.LoadFutureEventsByMonthsReturned;
import com.actvc.client.event.LoadFutureEventsReturned;
import com.actvc.client.event.LoadGradeChangeRequests;
import com.actvc.client.event.LoadHistoricEventsReturned;
import com.actvc.client.event.LoadLocationsReturned;
import com.actvc.client.event.LoadPendingReturned;
import com.actvc.client.event.LoadRecentReturned;
import com.actvc.client.event.LoadRiderNamesAndIdsReturned;
import com.actvc.client.event.LoadRiderNumbersAndIds;
import com.actvc.client.event.LoadRidersForAnEventReturned;
import com.actvc.client.event.LoadRidersReturned;
import com.actvc.client.event.LoadSeasonsReturned;
import com.actvc.client.event.LoadingScreenHideIt;
import com.actvc.client.event.LoadingScreenShowIt;
import com.actvc.client.event.LoginFailed;
import com.actvc.client.event.LoginReturned;
import com.actvc.client.event.ResetBitlyFailed;
import com.actvc.client.event.ResetBitlyReturned;
import com.actvc.client.event.ResetTwitterFailed;
import com.actvc.client.event.ResetTwitterReturned;
import com.actvc.client.event.SaveAdminFailed;
import com.actvc.client.event.SaveAdminReturned;
import com.actvc.client.event.SaveBitlySettingsFailed;
import com.actvc.client.event.SaveBitlySettingsReturned;
import com.actvc.client.event.SaveDirectorReminderFailed;
import com.actvc.client.event.SaveDirectorReminderReturned;
import com.actvc.client.event.SaveEventDescFailed;
import com.actvc.client.event.SaveEventDescReturned;
import com.actvc.client.event.SaveEventFailed;
import com.actvc.client.event.SaveEventReturned;
import com.actvc.client.event.SaveEventTypeFailed;
import com.actvc.client.event.SaveEventTypeReturned;
import com.actvc.client.event.SaveGeneralSystemFailed;
import com.actvc.client.event.SaveGeneralSystemReturned;
import com.actvc.client.event.SaveLocationFailed;
import com.actvc.client.event.SaveLocationReturned;
import com.actvc.client.event.SaveRaceHistoryFailed;
import com.actvc.client.event.SaveRaceHistoryReturned;
import com.actvc.client.event.SaveResetFinancialsFailed;
import com.actvc.client.event.SaveResetFinancialsReturned;
import com.actvc.client.event.SaveRiderAndCreateRaceHistoryFailed;
import com.actvc.client.event.SaveRiderAndCreateRaceHistoryReturned;
import com.actvc.client.event.SaveRiderReturned;
import com.actvc.client.event.SaveSeasonFailed;
import com.actvc.client.event.SaveSeasonReturned;
import com.actvc.client.event.SaveTwitterFailed;
import com.actvc.client.event.SaveTwitterSettingsReturned;
import com.actvc.client.event.SetTSForEditUtilitiesReturned;
import com.actvc.client.event.SubmitTweetFailed;
import com.actvc.client.event.SubmitTweetReturned;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AppServiceDelegate {
	private static final int MAXITERATIONINTERVAL = 100; // for setting initial
	// id key value in
	// datastore
	private final AppServiceAsync service = GWT.create(AppService.class);
	private final static Controller controller = ACTVC.getInstance()
			.getController();

	public void loadFutureEvents(boolean smallList) {
		System.out.println("in DELEGATE loadFutureEvents");

		controller.event(new LoadingScreenShowIt());
		service.loadFutureEvents(smallList, new AsyncCallback<TEDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TEDTO result) {
				try {
					controller.event(new LoadFutureEventsReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void loadFutureEventsByMonth(int months) {
		controller.event(new LoadingScreenShowIt());
		service.loadFutureEventsByMonths(months, new AsyncCallback<TEDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TEDTO result) {
				try {
					controller.event(new LoadFutureEventsByMonthsReturned(
							result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void loadHistoricEvents(boolean smallList) {
		System.out.println("in loadHistoricEvents");
		controller.event(new LoadingScreenShowIt());
		service.loadHistoricEvents(smallList, new AsyncCallback<TEDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TEDTO result) {
				try {
					controller.event(new LoadHistoricEventsReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	// ======================================================================================
	public void loadEventDatesAndIds(boolean showHistoricData,
			boolean showAllFutureEvents) {
		controller.event(new LoadingScreenShowIt());
		service.loadEventDatesAndIds(showHistoricData, showAllFutureEvents,
				new AsyncCallback<List<TE>>() {

					@Override
					public void onFailure(Throwable caught) {
						controller.event(new GeneralFailHideLoading(caught));
					}

					@Override
					public void onSuccess(List<TE> result) {
						try {
							controller.event(new LoadEventDatesAndIdsReturned(
									result));
						} finally {
							controller.event(new LoadingScreenHideIt());
						}
					}
				});

	}

	public void getEventDesc(Long eventId) {
		controller.event(new LoadingScreenShowIt());
		service.getEventDescByEventId(eventId, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(String result) {
				try {
					controller.event(new GetEventDescReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void loadRiderNamesAndIds() {
		loadRiderNamesAndIds(false);
	}

	public void loadRiderNamesAndIds(Boolean showOnlyActives) {
		controller.event(new LoadingScreenShowIt());
		service.loadRiderNamesAndIds(showOnlyActives,
				new AsyncCallback<List<String>>() {

					@Override
					public void onFailure(Throwable caught) {
						controller.event(new GeneralFailHideLoading(caught));

					}

					@Override
					public void onSuccess(List<String> result) {
						try {
							controller.event(new LoadRiderNamesAndIdsReturned(
									result));
						} finally {
							controller.event(new LoadingScreenHideIt());
						}

					}
				});
	}

	public void loadRiderNumbersAndIds() {
		controller.event(new LoadingScreenShowIt());
		service.loadRiderNumbersAndIds(new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(List<String> result) {
				try {
					controller.event(new LoadRiderNumbersAndIds(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void getRiderHistoryById(Long riderId) {
		controller.event(new LoadingScreenShowIt());
		service.getRiderHistoryById(riderId, new AsyncCallback<TRDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TRDTO result) {
				try {
					controller.event(new GetRiderHistoryByIdReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void login() {
		service.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			@Override
			public void onFailure(Throwable caught) {
				controller.event(new LoginFailed(caught));
			}

			@Override
			public void onSuccess(LoginInfo result) {
				controller.event(new LoginReturned(result));
			}

		});
	}

	public void registerUser(String email, String name) {
		service.registerUser(email, name, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void getEditRider(Long riderId) {
		controller.event(new LoadingScreenShowIt());
		service.getRiderById(riderId, new AsyncCallback<TR>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TR result) {
				try {
					controller.event(new GetEditRiderReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void saveRider(TR r) {
		controller.event(new LoadingScreenShowIt("Saving..."));
		service.saveRider(r, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new SaveRiderReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void getRidersByNumber(int checkedRiderNumber) {
		controller.event(new LoadingScreenShowIt());
		service.getRidersByNumber(checkedRiderNumber,
				new AsyncCallback<List<TR>>() {

					@Override
					public void onFailure(Throwable caught) {
						controller.event(new GeneralFailHideLoading(caught));
					}

					@Override
					public void onSuccess(List<TR> result) {
						try {
							controller.event(new EditGetRidersByNumberReturned(
									result));
						} finally {
							controller.event(new LoadingScreenHideIt());
						}
					}
				});
	}

	public void deleteRider(Long riderId) {
		controller.event(new LoadingScreenShowIt("Deleting..."));
		service.deleteRider(riderId, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new DeleteRiderReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void loadEventDesc() {
		controller.event(new LoadingScreenShowIt());
		service.loadEventDesc(new AsyncCallback<List<TED>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(List<TED> result) {
				try {
					controller.event(new LoadEventDescReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void loadActiveEventDesc() {
		controller.event(new LoadingScreenShowIt());
		service.loadActiveEventDesc(new AsyncCallback<List<TED>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(List<TED> result) {
				try {
					controller.event(new LoadActiveEventDescReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void loadEventTypes() {
		controller.event(new LoadingScreenShowIt());
		service.loadEventTypes(new AsyncCallback<List<TET>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(List<TET> result) {
				try {
					controller.event(new LoadEventTypesReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void getEditEvent(long eventId) {
		controller.event(new LoadingScreenShowIt());
		service.getEvent(eventId, new AsyncCallback<TE>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TE result) {
				try {
					controller.event(new GetEditEventReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void saveEvent(TE e) {
		controller.event(new LoadingScreenShowIt("Saving..."));
		service.saveEvent(e, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new SaveEventFailed());
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new SaveEventReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void deleteEvent(Long riderId) {
		controller.event(new LoadingScreenShowIt("Deleting..."));
		service.deleteEvent(riderId, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Boolean result) {
				try {
					controller.event(new DeleteEventReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void getRaceHistory(Long eventId, long riderId) {
		controller.event(new LoadingScreenShowIt());
		service.getRaceHistory(eventId, riderId, new AsyncCallback<TRH>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TRH result) {
				try {
					controller.event(new GetRaceHistoryReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void saveEventDesc(TED ed) {
		controller.event(new LoadingScreenShowIt("Saving..."));
		service.saveEventDesc(ed, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new SaveEventDescFailed());
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new SaveEventDescReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void saveEventType(TET et) {
		controller.event(new LoadingScreenShowIt("Saving..."));
		service.saveEventType(et, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
				controller.event(new SaveEventTypeFailed());
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new SaveEventTypeReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void getEditEventDesc(Long eventDescId) {
		controller.event(new LoadingScreenShowIt());
		service.getEventDescById(eventDescId, new AsyncCallback<TED>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TED result) {
				try {
					controller.event(new GetEditEventDescReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void loadAdmin() {
		controller.event(new LoadingScreenShowIt());
		service.loadUsers(new AsyncCallback<List<TU>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(List<TU> result) {
				try {
					controller.event(new LoadAdminReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void getAdmin(String email) {
		controller.event(new LoadingScreenShowIt());
		service.getUser(email, new AsyncCallback<TU>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TU result) {
				try {
					controller.event(new GetAdminReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void saveAdmin(TU a) {
		controller.event(new LoadingScreenShowIt("Saving..."));
		service.saveUser(a, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
				controller.event(new SaveAdminFailed());
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new SaveAdminReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void deleteAdmin(String email) {
		controller.event(new LoadingScreenShowIt("Deleting..."));
		service.deleteAdmin(email, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new DeleteAdminReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void deleteRaceHistory(Long eventId, Long riderId) {
		controller.event(new LoadingScreenShowIt("Deleting..."));
		service.deleteRaceHistory(eventId, riderId, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new DeleteRaceHistoryReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void utilDelete(int clsIdx) {
		controller.event(new LoadingScreenShowIt("Deleting..."));
		service.delete(clsIdx, new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Integer result) {
				try {
					controller.event(new DeleteUtilReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void loadRecentEvents(boolean isSuperUser) {
		controller.event(new LoadingScreenShowIt());
		service.loadRecentEvents(isSuperUser, new AsyncCallback<List<TE>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(List<TE> result) {
				try {
					controller.event(new LoadRecentReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void getEventDescByEventId(long eventDescId) {
		controller.event(new LoadingScreenShowIt());
		service.getEventDescByEventId(eventDescId, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(String result) {
				try {
					controller.event(new GetEventDescByEventIdReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void loadPending() {
		controller.event(new LoadingScreenShowIt());
		service.loadPending(new AsyncCallback<List<TPRH>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(List<TPRH> result) {
				try {
					controller.event(new LoadPendingReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void saveRiderAndCreateRaceHistory(TR r, long prhId) {
		controller.event(new LoadingScreenShowIt("Saving..."));
		service.saveRiderAndCreateRaceHistory(r, prhId,
				new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						controller.event(new GeneralFailHideLoading(caught));
						controller
								.event(new SaveRiderAndCreateRaceHistoryFailed());
					}

					@Override
					public void onSuccess(Boolean result) {
						try {
							controller
									.event(new SaveRiderAndCreateRaceHistoryReturned(
											result));
						} finally {
							controller.event(new LoadingScreenHideIt());
						}
					}
				});
	}

	public void getFutureEvent(Long eventId) {
		controller.event(new LoadingScreenShowIt());
		service.getEventDetails(eventId, new AsyncCallback<TEDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TEDTO result) {
				try {
					controller.event(new GetFutureEventReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void saveRaceHistory(TRH rh) {
		controller.event(new LoadingScreenShowIt("Saving..."));
		service.saveRaceHistory(rh, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
				controller.event(new SaveRaceHistoryFailed());
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new SaveRaceHistoryReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void getRaceEventResults(long eventID) {
		controller.event(new LoadingScreenShowIt());
		service.getRaceEventResults(eventID, new AsyncCallback<TEDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TEDTO result) {
				try {
					controller.event(new GetRaceEventResultsReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void loadEventDatesAndIdsForEdit() {
		controller.event(new LoadingScreenShowIt("Loading Event Data..."));
		service.loadEventDatesAndIds(true, true, new AsyncCallback<List<TE>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(List<TE> result) {
				try {
					controller.event(new LoadEventDatesAndIdsForEditReturned(
							result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void loadRidersForEvent(Long eventId) {
		controller.event(new LoadingScreenShowIt());
		service.loadRidersForEvent(eventId, new AsyncCallback<List<TR>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(List<TR> result) {
				try {
					controller.event(new LoadRidersForAnEventReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void getPendingRaceHistory(long prhId) {
		controller.event(new LoadingScreenShowIt());
		service.getPendingRaceHistory(prhId, new AsyncCallback<TPRH>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TPRH result) {
				try {
					controller.event(new GetPendingRaceHistoryReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void getNextNumber() {
		controller.event(new LoadingScreenShowIt());
		service.getNextNumber(new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Integer result) {
				try {
					controller.event(new GetNextNumberReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void checkGradeChangeDetails(int number, String lastName,
			String firstName, Date dob, String newGrade, int subgrade,
			String criteriumGrade) {
		controller.event(new LoadingScreenShowIt());
		service.checkGradeChangeDetails(number, lastName, firstName, dob,
				newGrade, subgrade, criteriumGrade,
				new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						controller.event(new CheckGradeChangeDetailsFailed(
								caught));
						controller.event(new GeneralFailHideLoading(caught));
					}

					@Override
					public void onSuccess(Boolean result) {
						try {
							controller
									.event(new CheckGradeChangeDetailsReturned(
											result));
						} finally {
							controller.event(new LoadingScreenHideIt());
						}
					}
				});

	}

	public void loadGradeChangeRequests() {
		controller.event(new LoadingScreenShowIt());
		service.loadGradeChangeRequests(new AsyncCallback<List<TGCR>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(List<TGCR> result) {
				try {
					controller.event(new LoadGradeChangeRequests(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void loadLocations() {
		controller.event(new LoadingScreenShowIt());
		service.loadLocations(new AsyncCallback<List<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(List<String> result) {
				try {
					controller.event(new LoadLocationsReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void getEditLocation(Long locationId) {
		controller.event(new LoadingScreenShowIt());
		service.getEditLocation(locationId, new AsyncCallback<TEL>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(TEL result) {
				try {
					controller.event(new GetLocationReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void changeGrade(String newGrade, int newSubgrade,
			String newCriteriumGrade, Long id) {
		controller.event(new LoadingScreenShowIt());
		service.changeGrade(newGrade, newSubgrade, newCriteriumGrade, id,
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						controller.event(new GeneralFailHideLoading(caught));
						controller.event(new ChangeGradeFailed());
					}

					@Override
					public void onSuccess(Void result) {
						try {
							controller.event(new ChangeGradeReturned());
						} finally {
							controller.event(new LoadingScreenHideIt());
						}
					}
				});
	}

	public void deleteLocation(Long locationId) {
		controller.event(new LoadingScreenShowIt("Deleting..."));
		service.deleteLocation(locationId, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new DeleteLocationReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void saveLocation(TEL l) {
		controller.event(new LoadingScreenShowIt("Saving..."));
		service.saveLocation(l, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
				controller.event(new SaveLocationFailed());
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new SaveLocationReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void doTest() {
		service.doTest(new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Test failed");
			}

			@Override
			public void onSuccess(Void result) {
				System.out.println("Test succeded");
			}
		});

	}

	public void doTest2() {
		// service.doTest2(new AsyncCallback<String>() {
		//
		// @Override
		// public void onFailure(Throwable caught) {
		// controller.event(new GeneralFailHideLoading(caught));
		// }
		//
		// @Override
		// public void onSuccess(String result) {
		// controller.event(new DoTest2Returned(result));
		// }
		// });
	}

	public void setMaxIdVal(final int maxIdVal) {
		controller.event(new LoadingScreenShowIt("Working..."));
		service.setMaxIdVal(maxIdVal, MAXITERATIONINTERVAL,
				new AsyncCallback<Long>() {

					@Override
					public void onFailure(Throwable caught) {
						controller.event(new GeneralFailHideLoading(caught));
					}

					@Override
					public void onSuccess(Long result) {
						try {
							if (result >= maxIdVal) {
								Window.alert("Max Id Val = " + result);
							} else {
								setMaxIdVal(maxIdVal);
							}
						} finally {
							controller.event(new LoadingScreenHideIt());
						}
					}
				});

	}

	public void submitTweet(String tweetMsg) {
		service.submitTweet(tweetMsg, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				MyLog.log(caught.toString());
				controller.event(new SubmitTweetFailed());
			}

			@Override
			public void onSuccess(Void result) {
				controller.event(new SubmitTweetReturned());
			}
		});
	}

	public void saveTwitterSettings(TS twitter) {
		controller.event(new LoadingScreenShowIt("Saving..."));
		service.saveTwitterSettings(twitter, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
				controller.event(new SaveTwitterFailed());
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new SaveTwitterSettingsReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void resetTwitter() {
		service.resetTwitter(new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				MyLog.log(caught.getCause().toString());
				controller.event(new ResetTwitterFailed());
			}

			@Override
			public void onSuccess(Void result) {
				controller.event(new ResetTwitterReturned());
			}
		});

	}

	public void saveBitlySettings(TS bitly) {
		controller.event(new LoadingScreenShowIt("Saving..."));
		service.saveBitlySettings(bitly, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
				controller.event(new SaveBitlySettingsFailed());
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new SaveBitlySettingsReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});

	}

	public void resetBitly() {
		service.resetBitly(new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new ResetBitlyFailed(caught));
			}

			@Override
			public void onSuccess(Void result) {
				controller.event(new ResetBitlyReturned());
			}
		});
	}

	public void loadSeasons() {
		controller.event(new LoadingScreenShowIt());
		service.loadSeasons(new AsyncCallback<List<TSe>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(List<TSe> result) {
				try {
					controller.event(new LoadSeasonsReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void saveSeason(TSe season) {
		controller.event(new LoadingScreenShowIt("Saving..."));
		service.saveSeason(season, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
				controller.event(new SaveSeasonFailed());
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new SaveSeasonReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void saveGeneralSystem(String urlHome, String mainEmail,
			String returnUrl, int utcOffset, int checkHour) {
		controller.event(new LoadingScreenShowIt("Saving..."));
		service.saveGeneralSystem(urlHome, mainEmail, returnUrl, utcOffset,
				checkHour, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						controller.event(new GeneralFailHideLoading(caught));
						controller.event(new SaveGeneralSystemFailed());
					}

					@Override
					public void onSuccess(Void result) {
						try {
							controller.event(new SaveGeneralSystemReturned());
						} finally {
							controller.event(new LoadingScreenHideIt());
						}
					}
				});
	}

	public void deletePendingRider(Long id) {
		controller.event(new LoadingScreenShowIt("Deleting..."));
		service.deletePendingRider(id, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new DeletePendingRiderReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void getRiderPoints(Long seasonId) {
		controller.event(new LoadingScreenShowIt("Gathering Data..."));
		service.getRiderPoints(seasonId, new AsyncCallback<Map<String, TRP>>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Map<String, TRP> result) {
				try {
					controller.event(new GetRiderPointsReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void getTS() {
		getTS(new GetTSForGeneralCase());
	}

	public void getTS(Event event) {
		if (event == null) {
			event = new GetTSForGeneralCase();
		}
		String eventName = event.getClass().getName();
		service.getTSDao(eventName, new AsyncCallback<TSDao>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailure(caught));
			}

			@Override
			public void onSuccess(TSDao result) {
				if (result != null) {
					if (result.getDaoEvent().equals(
							GetTSDaoForSystem.class.getName())) {
						controller.event(new GetTSDaoForSystem(result));
					} else if (result.getDaoEvent().equals(
							GetTSForStartup.class.getName())) {
						controller.event(new GetTSForStartup(result.getSystem()));
					} else if (result.getDaoEvent().equals(
							GetTSForUtilities.class.getName())) {
						controller.event(new GetTSForUtilities(result
								.getSystem()));
					}

				}

			}
		});

	}

	public void saveTS(TS s, Event event) {
		if (event == null) {
			return;
		}
		String eventName = event.getClass().getName();
		service.setTS(s, eventName, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailure(caught));
			}

			@Override
			public void onSuccess(String result) {
				if (result.equals(SetTSForEditUtilitiesReturned.class.getName())) {
					controller.event(new SetTSForEditUtilitiesReturned());
				}
			}
		});

	}

	public void deleteGradeChangeRequest(Long lookupId) {
		controller.event(new LoadingScreenShowIt("Deleting..."));
		service.deleteGradeChangeRequest(lookupId, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new DeleteGradeChangeRequestReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}

	public void loadDirectors() {
		controller.event(new LoadingScreenShowIt());
		service.loadDirectors(new AsyncCallback<List<String>>() {
			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));

			}

			@Override
			public void onSuccess(List<String> result) {
				try {
					controller.event(new LoadDirectorsReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}

			}
		});
	}

	public void loadRiders() {
		controller.event(new LoadingScreenShowIt());
		service.loadRiders(new AsyncCallback<List<String>>() {
			@Override
			public void onFailure(Throwable caught) {
				controller.event(new GeneralFailHideLoading(caught));

			}

			@Override
			public void onSuccess(List<String> result) {
				try {
					controller.event(new LoadRidersReturned(result));
				} finally {
					controller.event(new LoadingScreenHideIt());
				}

			}
		});
	}

	public void saveResetFinancialDate(TS rf) {
		controller.event(new LoadingScreenShowIt());
		service.saveResetFinancialDate(rf, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				controller.event(new SaveResetFinancialsFailed());
			}

			@Override
			public void onSuccess(Void result) {
				controller.event(new SaveResetFinancialsReturned());
			}
		});
	}

	public void saveDirectorReminder(TSDao reminderDao) {
		controller.event(new LoadingScreenShowIt());
		service.saveDirectorReminder(reminderDao, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				try {
					controller.event(new SaveDirectorReminderFailed());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}

			@Override
			public void onSuccess(Void result) {
				try {
					controller.event(new SaveDirectorReminderReturned());
				} finally {
					controller.event(new LoadingScreenHideIt());
				}
			}
		});
	}
}
