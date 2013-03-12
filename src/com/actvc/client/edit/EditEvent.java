package com.actvc.client.edit;

import java.util.Date;

import com.actvc.client.ACTVC;
import com.actvc.client.ContentEventDetail;
import com.actvc.client.UibEventContent;
import com.actvc.client.common.EventDate;
import com.actvc.client.common.MyLog;
import com.actvc.client.common.MyUtils;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TED;
import com.actvc.client.entities.TET;
import com.actvc.client.entities.TSe;
import com.actvc.client.event.DeleteEventReturned;
import com.actvc.client.event.GetEditEventReturned;
import com.actvc.client.event.LoadActiveEventDescReturned;
import com.actvc.client.event.LoadDirectorsReturned;
import com.actvc.client.event.LoadEventDatesAndIdsForEditReturned;
import com.actvc.client.event.LoadEventDescReturned;
import com.actvc.client.event.LoadEventTypesReturned;
import com.actvc.client.event.LoadLocationsReturned;
import com.actvc.client.event.LoadRidersReturned;
import com.actvc.client.event.LoadSeasonsReturned;
import com.actvc.client.event.SaveEventFailed;
import com.actvc.client.event.SaveEventReturned;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

public class EditEvent extends LookupEditBase {
	EventDate date;
	ListBox desc;
	ListBox activeDescEd;
	ListBox type;
	ListBox location;
	ListBox season;
	ListBox ridersEd;
	ListBox directorsEd;
	Button downloadBtn;
	private static HTML separator = new HTML("<hr/>");

	private boolean isDateChanged = false;;
	private boolean isEventDescLoaded = false;
	private boolean isActiveEventDescLoaded = false;
	private boolean isEventTypesLoaded = false;
	private boolean isEventLocationsLoaded = false;
	private boolean isSeasonsLoaded = false;
	private boolean isRidersLoaded = false;
	private boolean isDirectorsLoaded = false;
	private boolean isReloadEventSearch = false;
	protected boolean isCallbackFromEditEvents = false;

	@Override
	void buildContent() {
		desc = new ListBox();
		activeDescEd = new ListBox();
		date = new EventDate();
		type = new ListBox();
		location = new ListBox();
		season = new ListBox();
		ridersEd = new ListBox();
		directorsEd = new ListBox();
		downloadBtn = new Button("Download Race Day Spreadsheet");

		contentTbl.setWidget(0, 0, new Label("Date"));
		contentTbl.setWidget(0, 1, date);
		contentTbl.setWidget(1, 0, new Label("Desc"));
		contentTbl.setWidget(1, 1, desc);
		contentTbl.setWidget(2, 0, new Label("Type"));
		contentTbl.setWidget(2, 1, type);
		contentTbl.setWidget(3, 0, new Label("Location"));
		contentTbl.setWidget(3, 1, location);
		contentTbl.setWidget(4, 0, new Label("Season"));
		contentTbl.setWidget(4, 1, season);
		contentTbl.setWidget(5, 0, new Label("Director"));
		contentTbl.setWidget(5, 1, ridersEd);

		contentPanel.add(contentTbl);
		contentPanel.add(separator);
		contentPanel.add(downloadBtn);
		contentPanel.add(new HTML("&nbsp;"));

	}

	@Override
	void doDelete() {
		if (lookup.getSelectedIndex() == 0) {
			Window.alert("Select an Event to delete");
			return;
		}
		service.deleteEvent(getLookupId());
	}

	@Override
	void doSave() {
		if (editRb.getValue() && lookup.getSelectedIndex() == 0) {
			Window.alert("Select an Event");
			lookup.setFocus(true);
			return;
		}
		if (editRb.getValue()) {
			if (desc.getSelectedIndex() == 0) {
				Window.alert("Select a Description");
				desc.setFocus(true);
				return;
			}
		} else {
			if (activeDescEd.getSelectedIndex() == 0) {
				Window.alert("Select a Description");
				activeDescEd.setFocus(true);
				return;
			}
		}

		if (type.getSelectedIndex() == 0) {
			Window.alert("Select an Event Type");
			type.setFocus(true);
			return;
		}
		if (location.getSelectedIndex() == 0) {
			Window.alert("Select an Event Location");
			location.setFocus(true);
			return;
		}
		if (season.getSelectedIndex() == 0) {
			Window.alert("Select a Season");
			season.setFocus(true);
			return;
		}

		isDateChanged = (!lookup.getItemText(lookup.getSelectedIndex()).equals(
				MyUtils.getDateStr(date.getDate())));

		TE e = new TE();
		if (getLookupId() != 0) {
			e.setId(getLookupId());
		}
		e.setDate(date.getDate());
		e.setEventTypeId(Long.parseLong(type.getValue(type.getSelectedIndex())));
		e.setLocationId(Long.parseLong(location.getValue(location
				.getSelectedIndex())));
		e.setSeasonId(Long.parseLong(season.getValue(season.getSelectedIndex())));
		if (editRb.getValue()) {
			e.setEventDescriptionId(Long.parseLong(desc.getValue(desc
					.getSelectedIndex())));
			if (ridersEd.getSelectedIndex() > 0) {
				e.setDirectorId(Long.parseLong(ridersEd.getValue(ridersEd
						.getSelectedIndex())));
			}
		} else {
			e.setEventDescriptionId(Long.parseLong(activeDescEd
					.getValue(activeDescEd.getSelectedIndex())));
			if (directorsEd.getSelectedIndex() > 0) {
				e.setDirectorId(Long.parseLong(directorsEd.getValue(directorsEd
						.getSelectedIndex())));
			}
		}
		saveBtn.setEnabled(false);
		service.saveEvent(e);
	}

	@Override
	void resetForm() {
		date.reset();

		lookup.setSelectedIndex(0);
		desc.setSelectedIndex(0);
		activeDescEd.setSelectedIndex(0);
		type.setSelectedIndex(0);
		location.setSelectedIndex(0);
		season.setSelectedIndex(0);
		ridersEd.setSelectedIndex(0);
		directorsEd.setSelectedIndex(0);
		isDateChanged = false;
		deleteBtn.setEnabled(true);

		if (editRb.getValue()) {
			enableContentFields(false);
		}

		toggleNewEditEds(newRb.getValue());

	}

	@Override
	void wireEtc() {
		lookup.addItem("Select an Event");
		desc.setWidth("300px");
		activeDescEd.setWidth("300px");
		type.setWidth("300px");
		location.setWidth("300px");
		season.setWidth("300px");
		ridersEd.setWidth("300px");
		directorsEd.setWidth("300px");
		downloadBtn.setStylePrimaryName("shortbtn");

		contentPanel.setStylePrimaryName("width100percent");

		downloadBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (lookup.getSelectedIndex() == 0) {
					Window.alert("Select an Event for which to generate a race day spreadsheet");
				} else {
					String link = GWT.getHostPageBaseURL() + "getspreadsheet?"
							+ getLookupId() + "&"
							+ lookup.getItemText(lookup.getSelectedIndex());
					Window.open(link, "_blank", "");
				}
			}
		});

		desc.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (!isEventDescLoaded) {
					service.loadEventDesc();
				}
			}
		});

		activeDescEd.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (!isActiveEventDescLoaded) {
					service.loadActiveEventDesc();
				}
			}
		});

		type.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (!isEventTypesLoaded) {
					service.loadEventTypes();
				}
			}
		});

		location.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (!isEventLocationsLoaded) {
					service.loadLocations();
				}
			}
		});

		season.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (!isSeasonsLoaded) {
					service.loadSeasons();
				}
			}
		});

		ridersEd.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (!isRidersLoaded) {
					service.loadRiders();
				} else {
					if (!isRidersLoaded) {
						service.loadRiders();
					}
				}
			}
		});

		directorsEd.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (!isDirectorsLoaded) {
					service.loadDirectors();
				} else {
					if (!isDirectorsLoaded) {
						service.loadDirectors();
					}
				}
			}
		});

		controller.addListener(LoadEventDatesAndIdsForEditReturned.class,
				new ControllerListener<LoadEventDatesAndIdsForEditReturned>() {

					@Override
					public void event(
							LoadEventDatesAndIdsForEditReturned results) {
						lookup.clear();
						setLookupLoaded(true);
						if (results.getEventList() != null) {
							lookup.addItem("Select an Event");
							for (TE e : results.getEventList()) {
								lookup.addItem(MyUtils.getDateStr(e.getDate()),
										e.getId().toString());
							}
							if (isCallbackFromEditEvents) {
								if (!isEventDescLoaded) {
									service.loadEventDesc();
								} else if (!isEventTypesLoaded) {
									service.loadEventTypes();
								} else if (!isEventLocationsLoaded) {
									service.loadLocations();
								} else if (!isSeasonsLoaded) {
									service.loadSeasons();
								} else if (!isRidersLoaded) {
									service.loadRiders();
								} else if (isReloadEventSearch) {
									isReloadEventSearch = false;
									service.loadEventDatesAndIds(
											ACTVC.isShowHistoricData(),
											ACTVC.isShowAllFutureEvents());
								}
							}
						}
					}
				});

		controller.addListener(LoadEventDescReturned.class,
				new ControllerListener<LoadEventDescReturned>() {

					@Override
					public void event(LoadEventDescReturned result) {
						desc.clear();
						desc.addItem("");
						isEventDescLoaded = true;
						for (TED ed : result.getEventDescList()) {
							String s = ed.getDescription();
							if (s.length() > 45)
								s = s.substring(0, 45);
							desc.addItem(s, ed.getId().toString());
						}
						if (isCallbackFromEditEvents) {
							if (!isEventTypesLoaded) {
								service.loadEventTypes();
							} else if (!isEventLocationsLoaded) {
								service.loadLocations();
							} else if (!isSeasonsLoaded) {
								service.loadSeasons();
							} else if (!isRidersLoaded) {
								service.loadRiders();
							}
						}
					}
				});

		controller.addListener(LoadActiveEventDescReturned.class,
				new ControllerListener<LoadActiveEventDescReturned>() {

					@Override
					public void event(LoadActiveEventDescReturned result) {
						activeDescEd.clear();
						activeDescEd.addItem("");
						isActiveEventDescLoaded = true;
						for (TED ed : result.getActiveDescList()) {
							String s = ed.getDescription();
							if (s.length() > 45)
								s = s.substring(0, 45);
							activeDescEd.addItem(s, ed.getId().toString());
						}
					}
				});

		controller.addListener(LoadEventTypesReturned.class,
				new ControllerListener<LoadEventTypesReturned>() {

					@Override
					public void event(LoadEventTypesReturned result) {
						type.clear();
						isEventTypesLoaded = true;
						type.addItem("");
						for (TET et : result.getEventTypeList()) {
							String s = et.getDescription();
							if (s.length() > 45)
								s = s.substring(0, 45);
							type.addItem(s, et.getId().toString());
						}
						if (isCallbackFromEditEvents) {
							if (!isEventDescLoaded) {
								service.loadEventDesc();
							} else if (!isEventLocationsLoaded) {
								service.loadLocations();
							} else if (!isSeasonsLoaded) {
								service.loadSeasons();
							} else if (!isRidersLoaded) {
								service.loadRiders();
							}
						}
					}
				});

		controller.addListener(LoadLocationsReturned.class,
				new ControllerListener<LoadLocationsReturned>() {

					@Override
					public void event(LoadLocationsReturned result) {
						location.clear();
						location.addItem("");
						isEventLocationsLoaded = true;
						if (result.getLocationList() != null) {
							isEventLocationsLoaded = true;
							for (String s : result.getLocationList()) {
								String[] items = s.split(";");
								location.addItem(items[0], items[1]);
							}
						}
						if (isCallbackFromEditEvents) {
							if (!isEventDescLoaded) {
								service.loadEventDesc();
							} else if (!isEventTypesLoaded) {
								service.loadEventTypes();
							} else if (!isSeasonsLoaded) {
								service.loadSeasons();
							} else if (!isRidersLoaded) {
								service.loadRiders();
							}
						}
					}
				});

		controller.addListener(LoadSeasonsReturned.class,
				new ControllerListener<LoadSeasonsReturned>() {

					@Override
					public void event(LoadSeasonsReturned result) {
						season.clear();
						season.addItem("");
						isSeasonsLoaded = true;
						if (result.getSeasonList() != null) {
							isSeasonsLoaded = true;
							for (TSe s : result.getSeasonList()) {
								season.addItem(s.getSeason(), s.getId()
										.toString());
							}
						}
						if (isCallbackFromEditEvents) {
							if (!isEventDescLoaded) {
								service.loadEventDesc();
							} else if (!isEventTypesLoaded) {
								service.loadEventTypes();
							} else if (!isEventLocationsLoaded) {
								service.loadLocations();
							} else if (!isRidersLoaded) {
								service.loadRiders();
							}
						}
					}
				});

		controller.addListener(GetEditEventReturned.class,
				new ControllerListener<GetEditEventReturned>() {

					@Override
					public void event(GetEditEventReturned result) {
						if (result.getEvent() != null) {
							TE e = result.getEvent();

							MyLog.log("client " + e.getDate().toString());// smw
							date.setDate(e.getDate());

							desc.setSelectedIndex(getListIndex(desc, e
									.getEventDescriptionId().toString()));

							type.setSelectedIndex(getListIndex(type, e
									.getEventTypeId().toString()));

							location.setSelectedIndex(getListIndex(location, e
									.getLocationId().toString()));

							if (e.getSeasonId() != null) {
								season.setSelectedIndex(getListIndex(season, e
										.getSeasonId().toString()));
							} else {
								season.setSelectedIndex(0);
							}

							if (result.getEvent().getDirectorId() != null) {
								ridersEd.setSelectedIndex(getListIndex(
										ridersEd, e.getDirectorId().toString()));
							} else {
								ridersEd.setSelectedIndex(0);
							}
						}
					}

					private int getListIndex(ListBox listBox, String id) {
						int result = -1;
						for (int i = 1; i < listBox.getItemCount(); i++)
							if (listBox.getValue(i).equals(id)) {
								result = i;
								break;
							}
						return result;
					}
				});

		controller.addListener(SaveEventReturned.class,
				new ControllerListener<SaveEventReturned>() {

					@Override
					public void event(SaveEventReturned event) {
						saveBtn.setEnabled(true);

						ContentEventDetail.setLastEventID(null);
						if (isDateChanged) {
							UibEventContent.setLastEventID(null);
							resetForm();
							Window.alert("Event Saved. Need to reload Events");
							isDateChanged = false;
							if (ACTVC.isEventsSearchLoaded()) {
								isReloadEventSearch = true;
							}
							service.loadEventDatesAndIdsForEdit();
						} else {
							resetForm();
							Window.alert("Event Saved");
						}
					}
				});

		controller.addListener(DeleteEventReturned.class,
				new ControllerListener<DeleteEventReturned>() {

					@Override
					public void event(DeleteEventReturned result) {
						resetForm();
						if (result.getDeleteSuccessful()) {
							Window.alert("Delete was successful. Need to refresh the Events list");
							isReloadEventSearch = true;
							service.loadEventDatesAndIdsForEdit();
						} else {
							Window.alert("Delete failed");
						}

					}
				});

		controller.addListener(LoadRidersReturned.class,
				new ControllerListener<LoadRidersReturned>() {

					@Override
					public void event(LoadRidersReturned results) {
						ridersEd.clear();
						ridersEd.addItem("");
						isRidersLoaded = true;
						for (String s : results.getRiderList()) {
							String[] items = s.split(";");
							ridersEd.addItem(items[0], items[1]);
						}
					}
				});

		controller.addListener(LoadDirectorsReturned.class,
				new ControllerListener<LoadDirectorsReturned>() {

					@Override
					public void event(LoadDirectorsReturned results) {
						directorsEd.clear();
						directorsEd.addItem("");
						isDirectorsLoaded = true;
						for (String s : results.getDirectorList()) {
							String[] items = s.split(";");
							directorsEd.addItem(items[0], items[1]);
						}
					}
				});

		controller.addListener(SaveEventFailed.class,
				new ControllerListener<SaveEventFailed>() {

					@Override
					public void event(SaveEventFailed event) {
						saveBtn.setEnabled(true);
					}
				});

	}

	@Override
	void doLookupChange(ChangeEvent event) {
		service.getEditEvent(getLookupId());
	}

	private void toggleNewEditEds(boolean setForNew) {
		ridersEd.setEnabled(false);
		directorsEd.setEnabled(setForNew);
		desc.setEnabled(false);
		activeDescEd.setEnabled(setForNew);
		if (setForNew) {
			contentTbl.setWidget(1, 1, activeDescEd);
			contentTbl.setWidget(5, 1, directorsEd);
		} else {
			contentTbl.setWidget(1, 1, desc);
			contentTbl.setWidget(5, 1, ridersEd);
		}
	}

	@Override
	protected void clearOtherFields() {
		date.setDate(new Date());
		date.setHour(11);
	}

	@Override
	protected void doEditClick() {
		toggleNewEditEds(false);
	}

	@Override
	protected void doNewClick() {
		// toggleNewEditEds(true);
		// ridersEd.setEnabled(false);
		// directorsEd.setEnabled(true);
	}

	@Override
	void doFocusHandler() {
		if (!isLookupLoaded()) {
			isCallbackFromEditEvents = true;
			service.loadEventDatesAndIdsForEdit();
		} else {
			isCallbackFromEditEvents = false;
		}
	}

}
