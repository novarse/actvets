package com.actvc.client.edit;

import com.actvc.client.ACTVC;
import com.actvc.client.ContentEventDetail;
import com.actvc.client.ContentRaceResults;
import com.actvc.client.common.MyConst;
import com.actvc.client.common.MyLog;
import com.actvc.client.common.MyUtils;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.Person;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TRH;
import com.actvc.client.event.DeleteRaceHistoryReturned;
import com.actvc.client.event.GetRaceHistoryReturned;
import com.actvc.client.event.LoadRecentReturned;
import com.actvc.client.event.LoadRiderNamesAndIdsReturned;
import com.actvc.client.event.LoadRidersForAnEventReturned;
import com.actvc.client.event.SaveRaceHistoryFailed;
import com.actvc.client.event.SaveRaceHistoryReturned;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class EditRaceHistory extends LookupEditBase {
	ListBox riders;
	ListBox ridersForAnEvent;
	TextBox grade;
	TextBox place;
	TextBox overTheLine;
	TextBox time;
	TextBox points;
	TextArea comment;
	private int checkedPlace;
	private int checkedOverTheLine;
	private int checkedPoints;

	@Override
	void buildContent() {
		riders = new ListBox();
		ridersForAnEvent = new ListBox();
		grade = new TextBox();
		place = new TextBox();
		overTheLine = new TextBox();
		time = new TextBox();
		points = new TextBox();
		comment = new TextArea();
		lookupTable.setWidget(1, 0, new Label("Rider"));
		lookupTable.setWidget(1, 1, ridersForAnEvent);

		contentTbl = new FlexTable();
		contentTbl.setWidget(0, 0, new Label("Race Grade"));
		contentTbl.setWidget(0, 1, grade);
		contentTbl.setWidget(1, 0, new Label("Over the Line"));
		contentTbl.setWidget(1, 1, overTheLine);
		contentTbl.setWidget(2, 0, new Label("Place"));
		contentTbl.setWidget(2, 1, place);
		contentTbl.setWidget(3, 0, new Label("Time"));
		contentTbl.setWidget(3, 1, time);
		contentTbl.setWidget(4, 0, new Label("Points"));
		contentTbl.setWidget(4, 1, points);
		contentTbl.setWidget(5, 0, new Label("Comment"));
		contentTbl.setWidget(5, 1, comment);

		contentPanel.add(contentTbl);

	}

	@Override
	void resetForm() {
		// resetLookup();
		riders.setSelectedIndex(0);
		ridersForAnEvent.setSelectedIndex(0);
		blankFields();
		if (editRb.getValue() && riders.getSelectedIndex() == 0) {
			enableContentFields(false);
		}
	}

	private void blankFields() {
		grade.setText("");
		place.setText("");
		overTheLine.setText("");
		time.setText("");
		points.setText("");
	}

	@Override
	void doDelete() {
		service.deleteRaceHistory(getLookupId(), Long
				.parseLong(ridersForAnEvent.getValue(ridersForAnEvent
						.getSelectedIndex())));
	}

	@Override
	void doLookupChange(ChangeEvent event) {
		if (editRb.getValue()) {
			riders.setSelectedIndex(0);
			ridersForAnEvent.clear();
			blankFields();
			if (lookup.getSelectedIndex() != 0) {
				service.loadRidersForEvent(getLookupId());
			}
		}
	}

	@Override
	void doSave() {
		checkValues();
	}

	private void checkValues() {
		if (lookup.getSelectedIndex() == 0) {
			Window.alert("An Event needs to be selected");
			lookup.setFocus(true);
			return;
		}
		if (editRb.getValue() && ridersForAnEvent.getSelectedIndex() == 0) {
			Window.alert("A Rider needs to be selected");
			ridersForAnEvent.setFocus(true);
			return;
		}
		if (newRb.getValue() && riders.getSelectedIndex() == 0) {
			Window.alert("A Rider needs to be selected");
			riders.setFocus(true);
			return;
		}
		if (grade.getText().length() == 0) {
			Window.alert("A Grade is required");
			grade.setFocus(true);
			return;
		}
		grade.setText(grade.getText().toUpperCase());

		try {
			if (place.getText().trim().length() == 0) {
				checkedPlace = MyConst.getPlacenotset();
			} else {
				checkedPlace = Integer.parseInt(place.getText().trim());
			}
		} catch (Exception e) {
			Window.alert("Enter a valid Place or leave blank");
			place.setFocus(true);
			checkedPlace = MyConst.getPlacenotset();
			return;
		}
		try {
			if (overTheLine.getText().trim().length() == 0) {
				checkedOverTheLine = MyConst.getPlacenotset();
			} else {
				checkedOverTheLine = Integer.parseInt(overTheLine.getText()
						.trim());
			}
		} catch (Exception e) {
			Window.alert("Enter a valid Over the Line value or leave blank");
			overTheLine.setFocus(true);
			checkedOverTheLine = MyConst.getPlacenotset();
			return;
		}
		try {
			if (points.getText().trim().length() == 0) {
				checkedPoints = MyConst.getPointsnotset();
			} else {
				checkedPoints = Integer.parseInt(points.getText().trim());
			}
		} catch (Exception e) {
			Window.alert("Enter a valid Points or leave blank");
			points.setFocus(true);
			checkedPoints = MyConst.getPointsnotset();
			return;
		}

		saveNow();
	}

	private void saveNow() {
		try {
			Long riderId = editRb.getValue() ? Long.parseLong(ridersForAnEvent
					.getValue(ridersForAnEvent.getSelectedIndex())) : Long
					.parseLong(riders.getValue(riders.getSelectedIndex()));
			TRH rh = new TRH();
			rh.setId(getLookupId(), riderId);
			rh.setOverTheLine(checkedOverTheLine);
			rh.setPlace(checkedPlace);
			rh.setPoints(checkedPoints);
			rh.setRaceGrade(grade.getText());
			rh.setTime(time.getText());
			rh.setComment(comment.getText());

			saveBtn.setEnabled(false);
			service.saveRaceHistory(rh);
		} catch (RuntimeException e) {
			MyLog.log(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	void wireEtc() {
		lookup.addItem("Select an Event Date");
		ridersForAnEvent.addItem("");
		riders.addItem("Select a Rider");
		grade.setStylePrimaryName("uppercase");
		place.setStylePrimaryName("numbertextbox");
		overTheLine.setStylePrimaryName("numbertextbox");
		time.setStylePrimaryName("numbertextbox");
		points.setStylePrimaryName("numbertextbox");
		comment.setStylePrimaryName("rhComment");
		ridersForAnEvent.setWidth("150px");

		setLookupLbl("Event");

		ridersForAnEvent.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if (lookup.getSelectedIndex() > 0
						&& ridersForAnEvent.getSelectedIndex() > 0) {
					service.getRaceHistory(getLookupId(), Long
							.parseLong(ridersForAnEvent
									.getValue(ridersForAnEvent
											.getSelectedIndex())));
				}
			}
		});

		riders.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (!ACTVC.isRiderSearchLoaded()) {
					service.loadRiderNamesAndIds();
				}
			}
		});

		controller.addListener(LoadRecentReturned.class,
				new ControllerListener<LoadRecentReturned>() {

					@Override
					public void event(LoadRecentReturned results) {
						lookup.clear();
						lookup.addItem("Select an Event Date");
						for (TE e : results.getEvents()) {
							lookup.addItem(MyUtils.getDateStr(e.getDate()), e
									.getId().toString());
						}
						setLookupLoaded(true);
						if (editRb.getValue() && getLookupId() > 0) {
							service.loadRidersForEvent(getLookupId());
						}
					}
				});

		controller.addListener(LoadRiderNamesAndIdsReturned.class,
				new ControllerListener<LoadRiderNamesAndIdsReturned>() {

					@Override
					public void event(LoadRiderNamesAndIdsReturned results) {
						riders.clear();
						riders.addItem("Select a Rider");
						for (String s : results.getRidersList()) {
							String[] items = s.split(";");
							riders.addItem(items[0], items[1]);
						}
					}
				});

		controller.addListener(LoadRidersForAnEventReturned.class,
				new ControllerListener<LoadRidersForAnEventReturned>() {

					@Override
					public void event(LoadRidersForAnEventReturned result) {
						try {
							ridersForAnEvent.clear();
							if (result.getRiderList().isEmpty()) {
								ridersForAnEvent.addItem("There are no riders");
							} else {
								ridersForAnEvent.addItem("Select a Rider");
								for (Person r : result.getRiderList()) {
									ridersForAnEvent.addItem(r.getLastName()
											+ ", " + r.getFirstName(), r
											.getId().toString());
								}
							}
						} finally {
							if (editRb.getValue()
									&& riders.getSelectedIndex() == 0) {
								enableContentFields(false);
							}
						}
					}

				});

		controller.addListener(GetRaceHistoryReturned.class,
				new ControllerListener<GetRaceHistoryReturned>() {

					@Override
					public void event(GetRaceHistoryReturned result) {
						if (result.getRaceHistory() != null) {
							TRH rh = result.getRaceHistory();
							grade.setText(rh.getRaceGrade());
							place.setText(Integer.toString(rh.getPlace()));
							overTheLine.setText(Integer.toString(rh
									.getOverTheLine()));
							time.setText(rh.getTime());
							points.setText(Integer.toString(rh.getPoints()));
							comment.setText(rh.getComment());
							if (newRb.getValue()) {
								editRb.setValue(true);
								deleteBtn.setEnabled(true);
							}
							enableContentFields(true);
						} else if (newRb.getValue()) {
							blankFields();
						} else {
							Window.alert("No race history for that Event and Rider");
							resetForm();
						}
					}
				});

		controller.addListener(SaveRaceHistoryReturned.class,
				new ControllerListener<SaveRaceHistoryReturned>() {

					@Override
					public void event(SaveRaceHistoryReturned event) {
						resetForm();
						ContentRaceResults.setLastEventID(null);
						ContentEventDetail.setLastEventID(null);
						Window.alert("Data Saved");
						saveBtn.setEnabled(true);
						service.loadRidersForEvent(getLookupId());
					}
				});

		controller.addListener(SaveRaceHistoryFailed.class,
				new ControllerListener<SaveRaceHistoryFailed>() {

					@Override
					public void event(SaveRaceHistoryFailed result) {
						saveBtn.setEnabled(true);
					}
				});

		controller.addListener(DeleteRaceHistoryReturned.class,
				new ControllerListener<DeleteRaceHistoryReturned>() {

					@Override
					public void event(DeleteRaceHistoryReturned result) {
						resetForm();
						ContentRaceResults.setLastEventID(null);
						ContentEventDetail.setLastEventID(null);
						Window.confirm("Delete was successful");
						service.loadRidersForEvent(getLookupId());
					}
				});

	}

	@Override
	protected void doNewClick() {
		lookup.setEnabled(true);
		lookupTable.setWidget(1, 1, riders);
	}

	@Override
	protected void doEditClick() {
		lookupTable.setWidget(1, 1, ridersForAnEvent);
	}

	@Override
	void doFocusHandler() {
		if (!isLookupLoaded()) {
			service.loadRecentEvents(ACTVC.getLoginInfo().isRoleSuperUser());
		}
	}

}
