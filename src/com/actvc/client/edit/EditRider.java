package com.actvc.client.edit;

import java.util.Date;
import java.util.List;

import com.actvc.client.ACTVC;
import com.actvc.client.ContentEventDetail;
import com.actvc.client.ContentRaceResults;
import com.actvc.client.Gender;
import com.actvc.client.common.MyConst;
import com.actvc.client.common.MyDate;
import com.actvc.client.common.MyUtils;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.Person;
import com.actvc.client.entities.TPRH;
import com.actvc.client.entities.TR;
import com.actvc.client.event.DeletePendingRiderReturned;
import com.actvc.client.event.DeleteRiderReturned;
import com.actvc.client.event.EditGetRidersByNumberReturned;
import com.actvc.client.event.GetEditRiderReturned;
import com.actvc.client.event.GetNextNumberReturned;
import com.actvc.client.event.GetPendingRaceHistoryReturned;
import com.actvc.client.event.InitRider;
import com.actvc.client.event.LoadPendingReturned;
import com.actvc.client.event.LoadRiderNamesAndIdsReturned;
import com.actvc.client.event.SaveRiderAndCreateRaceHistoryReturned;
import com.actvc.client.event.SaveRiderReturned;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;

public class EditRider extends LookupEditBase {

	private static final int TMP_RIDER_NUMBER_MIN = 900;

	private enum State {
		ACT, NSW, NT, QLD, SA, TAS, VIC, WA;
	}

	private static final int NOTSET = -1;
	// .println("Number\tName\tStreet"
	// "\tSuburb\tState\tPostcode\tHome Phone\tWork or Mobile\tDOB\tEmail\tFirst Aid\tEmergency Contact\tEmergency Contact No");

	private CheckBox showOnlyActivesChk;
	private CheckBox isActiveChk;
	private TextBox lastName;
	private TextBox firstName;
	private TextBox number;
	private TextBox AVCCNumber;
	private TextBox grade;
	private ListBox subGrade;
	private TextBox criteriumGrade;
	private TextBox tempNumber;
	private PushButton newNumberBtn;
	private TextBox street;
	private TextBox suburb;
	private ListBox state;
	private TextBox postcode;
	private TextBox phoneHome;
	private TextBox phoneWorkOrMobile;
	private MyDate dob;
	private ListBox gender;
	private TextBox email;
	private CheckBox firstAidChk;
	private TextBox emergencyContact;
	private TextBox phoneEmergencyContact;
	private TextBox phoneEmergencyContact2;
	private CheckBox isDirectorChk;
	private int checkedRiderNumber = NOTSET;
	private boolean nameHasChanged = false;
	boolean pendingLoaded = false;
	private ListBox pendingList;
	protected long prhId = 0;
	private TextBox dobText;
	private TextBox raceGrade;
	private Button deletePendingBtn;

	@Override
	void buildContent() {
		showOnlyActivesChk = new CheckBox(
				"Show only financially active members");
		pendingList = new ListBox();

		isActiveChk = new CheckBox();
		lastName = new TextBox();
		firstName = new TextBox();
		number = new TextBox();
		AVCCNumber = new TextBox();
		grade = new TextBox();
		raceGrade = new TextBox();
		subGrade = new ListBox();
		criteriumGrade = new TextBox();
		tempNumber = new TextBox();
		newNumberBtn = new PushButton("Get New Number");
		street = new TextBox();
		suburb = new TextBox();
		state = new ListBox();
		postcode = new TextBox();
		phoneHome = new TextBox();
		phoneWorkOrMobile = new TextBox();
		dob = new MyDate();
		gender = new ListBox();
		dobText = new TextBox();
		email = new TextBox();
		firstAidChk = new CheckBox();
		emergencyContact = new TextBox();
		phoneEmergencyContact = new TextBox();
		phoneEmergencyContact2 = new TextBox();
		isDirectorChk = new CheckBox();
		deletePendingBtn = new Button("Delete Pending Rider");

		lookupTable.setWidget(0, 2, showOnlyActivesChk);

		contentTbl = new FlexTable();
		contentTbl.setWidget(0, 0, new Label("Is Financial"));
		contentTbl.setWidget(0, 1, isActiveChk);
		contentTbl.setWidget(1, 0, new Label("First Name"));
		contentTbl.setWidget(1, 1, firstName);
		contentTbl.setWidget(2, 0, new Label("Last Name"));
		contentTbl.setWidget(2, 1, lastName);
		contentTbl.setWidget(3, 0, new Label("Number"));
		contentTbl.setWidget(3, 1, number);
		contentTbl.setWidget(3, 2, tempNumber);
		contentTbl.setWidget(3, 3, newNumberBtn);
		contentTbl.setWidget(4, 0, new Label("AVCC Number"));
		contentTbl.setWidget(4, 1, AVCCNumber);
		contentTbl.setWidget(5, 0, new Label("Grade"));
		contentTbl.setWidget(5, 1, grade);
		contentTbl.setWidget(5, 2, raceGrade);
		contentTbl.setWidget(6, 0, new Label("Sub Grade"));
		contentTbl.setWidget(6, 1, subGrade);
		contentTbl.setWidget(7, 0, new Label("Criterium Grade"));
		contentTbl.setWidget(7, 1, criteriumGrade);
		contentTbl.setWidget(8, 0, new Label("Street"));
		contentTbl.setWidget(8, 1, street);
		contentTbl.setWidget(9, 0, new Label("Suburb"));
		contentTbl.setWidget(9, 1, suburb);
		contentTbl.setWidget(10, 0, new Label("State"));
		contentTbl.setWidget(10, 1, state);
		contentTbl.setWidget(11, 0, new Label("Postcode"));
		contentTbl.setWidget(11, 1, postcode);
		contentTbl.setWidget(12, 0, new Label("Home phone"));
		contentTbl.setWidget(12, 1, phoneHome);
		contentTbl.setWidget(13, 0, new Label("Work or Mobile"));
		contentTbl.setWidget(13, 1, phoneWorkOrMobile);
		contentTbl.setWidget(14, 0, new Label("Date of Birth"));
		contentTbl.setWidget(14, 1, dob);
		contentTbl.setWidget(14, 2, dobText);
		contentTbl.setWidget(15, 0, new Label("Gender"));
		contentTbl.setWidget(15, 1, gender);
		contentTbl.setWidget(16, 0, new Label("Email"));
		contentTbl.setWidget(16, 1, email);
		contentTbl.setWidget(17, 0, new Label("First Aid"));
		contentTbl.setWidget(17, 1, firstAidChk);
		contentTbl.setWidget(18, 0, new Label("Emergency Cont"));
		contentTbl.setWidget(18, 1, emergencyContact);
		contentTbl.setWidget(19, 0, new Label("Emer Cont Ph 1"));
		contentTbl.setWidget(19, 1, phoneEmergencyContact);
		contentTbl.setWidget(20, 0, new Label("Emer Cont Ph 2"));
		contentTbl.setWidget(20, 1, phoneEmergencyContact2);
		contentTbl.setWidget(21, 0, new Label("Is Director"));
		contentTbl.setWidget(21, 1, isDirectorChk);

		contentPanel.add(contentTbl);

		saveDeleteTable.setWidget(0, 2, deletePendingBtn);
	}

	@Override
	void doDelete() {
		if (lookup.getSelectedIndex() == 0) {
			Window.alert("Select a Rider to delete");
			return;
		}
		service.deleteRider(getLookupId());
	}

	@Override
	void doSave() {
		checkFieldValues();
	}

	private void checkFieldValues() {
		if (editRb.getValue() && lookup.getSelectedIndex() == 0) {
			Window.alert("A Rider needs to be selected when saving an edit");
			lookup.setFocus(true);
			return;
		}
		if (lastName.getText().length() == 0) {
			Window.alert("A Last Name is required");
			lastName.setFocus(true);
			return;
		}
		if (firstName.getText().length() == 0) {
			Window.alert("A First Name is required");
			firstName.setFocus(true);
			return;
		}
		if (grade.getText().length() == 0) {
			Window.alert("A Grade is required");
			grade.setFocus(true);
			return;
		}
		if (subGrade.getSelectedIndex() == 0) {
			Window.alert("Select a Sub Grade");
			subGrade.setFocus(true);
			return;
		}
		if (criteriumGrade.getText().isEmpty()) {
			Window.alert("A Criterium Grade is required");
			criteriumGrade.setFocus(true);
			return;
		}
		number.setText(number.getText().trim());
		if (number.getText().length() == 0) {
			Window.alert("A Race Number is required");
			number.setFocus(true);
			return;
		}
		if (state.getSelectedIndex() == 0) {
			Window.alert("Select a State");
			state.setFocus(true);
			return;
		}
		if (MyUtils.getDateStr(dob.getDate()).equals(
				MyUtils.getDateStr(new Date()))) {
			Window.alert("Set the rider's date of birth");
			return;
		}
		grade.setText(grade.getText().toUpperCase());

		if (gender.getSelectedIndex() == 0) {
			Window.alert("Select a Gender");
			gender.setFocus(true);
			return;
		}
		try {
			setCheckedRiderNumber(Integer.parseInt(number.getText()));
			if (getCheckedRiderNumber() >= TMP_RIDER_NUMBER_MIN) {
				throw new Exception("A rider requires a number < "
						+ TMP_RIDER_NUMBER_MIN);
			}
		} catch (Exception e) {
			Window.alert("Enter a valid Number");
			number.setFocus(true);
			setCheckedRiderNumber(NOTSET);
			return;
		}

		if (Integer.parseInt(number.getText()) == 0) {
			saveNow();
		} else {
			checkNumberUnique();
		}
	}

	private void saveNow() {
		TR r = new TR();
		if (editRb.getValue()) {
			r.setId(getLookupId());
		}
		r.setActive(isActiveChk.getValue());
		r.setFirstName(firstName.getText());
		r.setLastName(lastName.getText());
		r.setNumber(checkedRiderNumber);
		r.setAVCCNumber(AVCCNumber.getText().trim());
		r.setGrade(grade.getText().trim());
		r.setSubGrade(subGrade.getSelectedIndex());
		r.setCriteriumGrade(criteriumGrade.getText().trim());
		nameHasChanged = !lookup.getItemText(lookup.getSelectedIndex()).equals(
				lastName.getText() + ", " + firstName.getText().charAt(0));
		r.setStreet(street.getText());
		r.setSuburb(suburb.getText());
		r.setState(state.getItemText(state.getSelectedIndex()));
		r.setPostcode(postcode.getText());
		r.setPhoneHome(phoneHome.getText());
		r.setPhoneWorkOrMobile(phoneWorkOrMobile.getText());
		r.setDob(dob.getDate());
		r.setGender(gender.getSelectedIndex() == 1 ? Gender.F : gender
				.getSelectedIndex() == 2 ? Gender.M : Gender.U);
		r.setEmail(email.getText());
		r.setFirstAid(firstAidChk.getValue());
		r.setEmergencyContact(emergencyContact.getText());
		r.setPhoneEmergencyContact(phoneEmergencyContact.getText());
		r.setPhoneEmergencyContact2(phoneEmergencyContact2.getText());
		r.setDirector(isDirectorChk.getValue());

		saveBtn.setEnabled(false);
		if (prhId == 0) {
			service.saveRider(r);
		} else {
			service.saveRiderAndCreateRaceHistory(r, prhId);
		}
	}

	@Override
	void resetForm() {
		prhId = 0;
		lookup.setSelectedIndex(0);

		isActiveChk.setValue(true);
		firstName.setText("");
		lastName.setText("");
		number.setText("");
		AVCCNumber.setText("");
		grade.setText("");
		subGrade.setSelectedIndex(0);
		criteriumGrade.setText("");
		street.setText("");
		suburb.setText("");
		postcode.setText("");
		state.setSelectedIndex(0);
		phoneHome.setText("");
		phoneWorkOrMobile.setText("");
		dob.reset();
		email.setText("");
		firstAidChk.setValue(false);
		emergencyContact.setText("");
		phoneEmergencyContact.setText("");
		phoneEmergencyContact2.setText("");
		isDirectorChk.setValue(false);
		raceGrade.setVisible(false);
		dobText.setVisible(false);
		gender.setSelectedIndex(0);
		tempNumber.setVisible(false);
		if (editRb.getValue()) {
			enableContentFields(false);
		}
		showDeletePending(false);

	}

	@Override
	void wireEtc() {
		showOnlyActivesChk.setValue(true);
		lookup.addItem("Select a Rider");
		state.addItem("Select a State");
		for (State s : State.values()) {
			state.addItem(s.toString());
		}
		setupSubGrade();
		lastName.setStylePrimaryName("capitalize");
		firstName.setStylePrimaryName("capitalize");
		grade.setStylePrimaryName("uppercase");
		criteriumGrade.setStylePrimaryName("uppercase");
		number.setStylePrimaryName("numbertextbox");
		AVCCNumber.setStylePrimaryName("uppercase");
		raceGrade.setStylePrimaryName("width100");
		postcode.setStylePrimaryName("numbertextbox");
		tempNumber.setStylePrimaryName("width100numbertextbox");
		dobText.setStylePrimaryName("width100numbertextbox");
		initGender();
		raceGrade.setReadOnly(true);
		dobText.setReadOnly(true);
		dobText.setVisible(false);
		raceGrade.setVisible(false);
		tempNumber.setReadOnly(true);
		tempNumber.setVisible(false);
		raceGrade.setTitle("This is the grade received for the race");
		tempNumber.setTitle("This is the temporary number given for the race");
		dobText.setTitle("This is the date of birth recorded on race day. The date of birth needs to be set for this rider");
		deletePendingBtn.setVisible(false);

		newRb.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				prhId = 0;
			}
		});

		editRb.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				initEdit();
			}
		});

		pendingList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {

				if (pendingList.getSelectedIndex() != 0) {
					newRb.setValue(true, true);
					prhId = Long.parseLong(pendingList.getValue(pendingList
							.getSelectedIndex()));
					service.getPendingRaceHistory(prhId);
				} else {
					resetForm();
				}
			}
		});

		newNumberBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				service.getNextNumber();
			}
		});

		deletePendingBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (prhId > 0) {
					if (Window
							.confirm("Are you sure you want to delete this pending rinder?")) {
						deletePendingBtn.setEnabled(false);
						service.deletePendingRider(prhId);
					}
				}
			}
		});

		showOnlyActivesChk.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				loadRiders();
			}
		});

		controller.addListener(InitRider.class,
				new ControllerListener<InitRider>() {

					@Override
					public void event(InitRider result) {
						if (isOnlyHandicapper()) {
							newRb.setEnabled(false);
						}
					}
				});

		controller.addListener(LoadRiderNamesAndIdsReturned.class,
				new ControllerListener<LoadRiderNamesAndIdsReturned>() {

					@Override
					public void event(LoadRiderNamesAndIdsReturned results) {
						lookup.clear();
						lookup.addItem("Select a Rider");
						for (String s : results.getRidersList()) {
							String[] items = s.split(";");
							lookup.addItem(items[0], items[1]);
						}
						if (pendingLoaded) {
							service.loadPending();
						}
					}
				});

		controller.addListener(GetEditRiderReturned.class,
				new ControllerListener<GetEditRiderReturned>() {

					@Override
					public void event(GetEditRiderReturned event) {
						if (event.getRider() != null) {
							service.loadPending();

							populateFields(event.getRider());

							if (isOnlyHandicapper()) {
								enableHandicapperFields();
							}
						}
					}

					private void enableHandicapperFields() {
						enableContentFields(false);

						grade.setEnabled(true);
						subGrade.setEnabled(true);
						criteriumGrade.setEnabled(true);
					}

				});

		controller.addListener(EditGetRidersByNumberReturned.class,
				new ControllerListener<EditGetRidersByNumberReturned>() {

					@Override
					public void event(EditGetRidersByNumberReturned result) {
						List<TR> riders = result.getRiders();
						if (riders == null
								|| riders.size() == 0
								|| (riders.size() == 1 && lookup.getValue(
										lookup.getSelectedIndex()).equals(
										riders.get(0).getId().toString()))) {
							saveNow();
						} else {
							number.setFocus(true);
							number.selectAll();
							String confirmStr = "";
							if (riders.size() == 1) {
								confirmStr = "Another rider with number "
										+ riders.get(0).getNumber()
										+ " already exists: "
										+ riders.get(0).getFirstName() + " "
										+ riders.get(0).getLastName()
										+ ". Do you want to save?";
							} else {
								confirmStr = "Other riders with number "
										+ riders.get(0).getNumber()
										+ " already exists: ";
								for (TR rider : riders) {
									confirmStr += rider.getFirstName() + " "
											+ rider.getLastName() + "; ";

								}
								confirmStr += " Do you want to save?";
							}
							if (Window.confirm(confirmStr)) {
								saveNow();
							}
						}
					}
				});

		controller.addListener(SaveRiderReturned.class,
				new ControllerListener<SaveRiderReturned>() {

					@Override
					public void event(SaveRiderReturned result) {
						saveBtn.setEnabled(true);
						ContentEventDetail.setLastEventID(null);
						resetForm();
						if (!nameHasChanged) {
							Window.alert("Data Saved");
						} else {
							if (Window
									.confirm("Data Saved. Reload Riders now?")) {
								nameHasChanged = false;
								loadRiders();

								service.loadDirectors();
							} else {
								service.loadPending();
							}
						}

					}
				});

		controller.addListener(DeleteRiderReturned.class,
				new ControllerListener<DeleteRiderReturned>() {

					@Override
					public void event(DeleteRiderReturned result) {
						resetForm();
						ContentRaceResults.setLastEventID(null);
						if (Window
								.confirm("Delete was successful. Do you want to refresh the Riders list now?")) {
							loadRiders();
							service.loadDirectors();
						}
					}
				});

		controller.addListener(LoadPendingReturned.class,
				new ControllerListener<LoadPendingReturned>() {

					@Override
					public void event(LoadPendingReturned result) {
						pendingList.clear();
						if (result.getPendingList() == null
								|| result.getPendingList().isEmpty()) {
							pendingList.addItem("There are no pending riders");
						} else {
							pendingList.addItem("Select a pending Rider");
							for (TPRH prh : result.getPendingList()) {
								pendingList.addItem(prh.getLastName() + ", "
										+ prh.getFirstName(), prh.getpKeyID()
										.toString());
							}
						}
						pendingLoaded = true;
						prhId = 0;
					}
				});

		controller.addListener(GetPendingRaceHistoryReturned.class,
				new ControllerListener<GetPendingRaceHistoryReturned>() {

					@Override
					public void event(GetPendingRaceHistoryReturned result) {
						TPRH prh = result.getPrh();
						if (prh != null) {
							populateFields(prh);
							raceGrade.setText(prh.getRaceGrade());
							dobText.setText(prh.getDob());
							tempNumber.setText(Integer.toString(prh.getNumber()));
							raceGrade.setVisible(true);
							dobText.setVisible(true);
							tempNumber.setVisible(true);

							showDeletePending(true);
						}
					}
				});

		controller
				.addListener(
						SaveRiderAndCreateRaceHistoryReturned.class,
						new ControllerListener<SaveRiderAndCreateRaceHistoryReturned>() {

							@Override
							public void event(
									SaveRiderAndCreateRaceHistoryReturned event) {
								saveBtn.setEnabled(true);
								resetForm();
								if (event.savedOk()) {
									ContentRaceResults.setLastEventID(null);
									if (nameHasChanged) {
										if (Window
												.confirm("Data Saved. Reload Riders now?")) {
											nameHasChanged = false;
											loadRiders();
											service.loadDirectors();
										} else {
											service.loadPending();
										}
									} else {
										Window.alert("Data Saved");
										if (pendingLoaded) {
											service.loadPending();
										}
									}
								} else {
									Window.alert("Failed to create history for that rider");
								}
							}

						});

		controller.addListener(GetNextNumberReturned.class,
				new ControllerListener<GetNextNumberReturned>() {

					@Override
					public void event(GetNextNumberReturned result) {
						if (result.getNextNumber() == null) {
							Window.alert("Could not obtain the next rider Number");
						} else {
							number.setText(Integer.toString(result
									.getNextNumber()));
						}
					}
				});

		controller.addListener(DeletePendingRiderReturned.class,
				new ControllerListener<DeletePendingRiderReturned>() {

					@Override
					public void event(DeletePendingRiderReturned event) {
						deletePendingBtn.setEnabled(true);
						resetForm();
						service.loadPending();
					}
				});

	}

	protected boolean isOnlyHandicapper() {
		return !ACTVC.getLoginInfo().isRoleSuperUser()
				&& !ACTVC.getLoginInfo().isRoleMembershipTreasurer()
				&& ACTVC.getLoginInfo().isRoleHandicapper();
	}

	protected void loadRiders() {
		resetForm();
		service.loadRiderNamesAndIds(showOnlyActivesChk.getValue());
		service.loadRiderNumbersAndIds();

	}

	private void setupSubGrade() {
		subGrade.clear();
		subGrade.addItem("Select a Sub Grade");
		for (int i = 1; i <= MyConst.MAX_SUBGRADE; i++) {
			subGrade.addItem(Integer.toString(i));
		}
	}

	protected void showDeletePending(boolean showDeletePending) {
		deletePendingBtn.setVisible(showDeletePending);
		deleteBtn.setVisible(!showDeletePending);
	}

	private void initGender() {
		gender.clear();
		gender.addItem("Select a gender");
		gender.addItem(Gender.F.toString());
		gender.addItem(Gender.M.toString());
	}

	private void populateFields(Person p) {
		isActiveChk.setValue(p.isActive());
		lastName.setText(p.getLastName());
		firstName.setText(p.getFirstName());
		if (p.getNumber() != -1 && p.getNumber() < TMP_RIDER_NUMBER_MIN) {
			newNumberBtn.setEnabled(false);
			number.setText(String.valueOf(p.getNumber()));
		} else {
			newNumberBtn.setEnabled(true);
			number.setText("");
		}
		AVCCNumber.setText(p.getAVCCNumber());
		grade.setText(p.getGrade());
		subGrade.setSelectedIndex(p.getSubGrade());
		criteriumGrade.setText(p.getCriteriumGrade());
		street.setText(p.getStreet());
		suburb.setText(p.getSuburb());
		state.setSelectedIndex(getStateFromList(p.getState()));
		postcode.setText(p.getPostcode());
		phoneHome.setText(p.getPhoneHome());
		phoneWorkOrMobile.setText(p.getPhoneWorkOrMobile());
		if (p instanceof TR && p.getDob() != null) {
			dob.setDate((Date) p.getDob());
		}
		gender.setSelectedIndex(p.getGender().equals(Gender.F) ? 1 : p
				.getGender().equals(Gender.M) ? 2 : 0);
		email.setText(p.getEmail());
		firstAidChk.setValue(p.isFirstAid());
		emergencyContact.setText(p.getEmergencyContact());
		phoneEmergencyContact.setText(p.getPhoneEmergencyContact());
		phoneEmergencyContact2.setText(p.getPhoneEmergencyContact2());
		isDirectorChk.setValue(p.isDirector());

		enableContentFields(true);
		newNumberBtn.setEnabled(true);
	}

	private int getStateFromList(String state) {
		if (state == null || state.isEmpty()) {
			return 0;
		}
		try {
			State s = State.valueOf(state);
			Integer index = null;
			for (int i = 0; i < State.values().length; i++)
				if (State.values()[i].equals(s))
					index = i;
			return index + 1;
		} catch (Exception e) {
			return 0;
		}
	}

	private void initEdit() {
		lookup.setSelectedIndex(0);
		lookupTable.setWidget(0, 1, lookup);
		resetForm();
	}

	private void initPending() {
		pendingList.setSelectedIndex(0);
		lookupTable.setWidget(0, 1, pendingList);
		resetForm();
	}

	protected void checkNumberUnique() {
		service.getRidersByNumber(checkedRiderNumber);
	}

	@Override
	void doLookupChange(ChangeEvent event) {
		service.getEditRider(getLookupId());
	}

	@Override
	protected void doNewClick() {
		showOnlyActivesChk.setVisible(false);
		initPending();
		if (!pendingLoaded) {
			service.loadPending();
		}

	}

	@Override
	protected void doEditClick() {
		showOnlyActivesChk.setVisible(true);
	}

	@Override
	void doFocusHandler() {
		if (!ACTVC.isRiderSearchLoaded()) {
			loadRiders();
		}
	}

	public int getCheckedRiderNumber() {
		return checkedRiderNumber;
	}

	public void setCheckedRiderNumber(int checkedRiderNumber) {
		this.checkedRiderNumber = checkedRiderNumber;
	}

	@Override
	protected void enableOtherFields(boolean setIt) {
		newNumberBtn.setEnabled(setIt);
		dob.setEnabled(setIt);
	}

}
