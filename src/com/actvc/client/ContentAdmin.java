package com.actvc.client;

import java.util.HashMap;
import java.util.Map;

import com.actvc.client.controller.ControllerListener;
import com.actvc.client.edit.EditBase;
import com.actvc.client.edit.EditEvent;
import com.actvc.client.edit.EditEventDesc;
import com.actvc.client.edit.EditEventLocation;
import com.actvc.client.edit.EditEventType;
import com.actvc.client.edit.EditGradeChangeRequest;
import com.actvc.client.edit.EditRaceHistory;
import com.actvc.client.edit.EditResultsUpload;
import com.actvc.client.edit.EditRider;
import com.actvc.client.edit.EditSeason;
import com.actvc.client.edit.EditSystem;
import com.actvc.client.edit.EditUser;
import com.actvc.client.edit.EditUtilities;
import com.actvc.client.event.InitRider;
import com.actvc.client.event.InitSystem;
import com.actvc.client.event.InitUtilities;
import com.actvc.client.event.LoginReturned;
import com.actvc.client.event.ShowOnly;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ContentAdmin extends ContentWidget {
	private static final int EVENTTAB = 0;
	private static final int RIDERTAB = 1;
	private static final int RACEHISTORYTAB = 2;
	private static final int EVENTDESCTAB = 3;
	private static final int EVENTTYPETAB = 4;
	private static final int EVENTLOCATIONTAB = 5;
	private static final int SEASONTAB = 6;
	private static final int USERTAB = 7;
	private static final int UPLOADTAB = 8;
	private static final int GRADECHANGE = 9;
	private static final int SYSTEMTAB = 10;
	private static final int UTILITIESTAB = 11;

	private final Map<Integer, String> tabMap = new HashMap<Integer, String>();

	private final String[] tabs = { "Events", "Riders", "Race Details",
			"Descriptions", "Types", "Locations", "Seasons", "Users",
			"Add Results", "Grade Change", "System", "Utilities" };

	private final VerticalPanel loginPanel = new VerticalPanel();
	private final HTML loginMessage = new HTML();
	private final HTML separator = new HTML("<hr>");
	FlexTable loginTable = new FlexTable();
	TabPanel editTabs = new TabPanel();
	Button loginBtn = new Button("Login");
	Button logoutBtn = new Button("Logout");

	EditEvent editEvent = new EditEvent();
	EditRider editRider = new EditRider();
	EditRaceHistory editRaceHistory = new EditRaceHistory();
	EditEventDesc editEventDesc = new EditEventDesc();
	EditEventType editEventType = new EditEventType();
	EditEventLocation editEventLocation = new EditEventLocation();
	EditSeason editSeason = new EditSeason();
	EditUser editUsers = new EditUser();
	EditResultsUpload editResultsUpload = new EditResultsUpload();
	EditGradeChangeRequest editGradeChangeRequest = new EditGradeChangeRequest();
	EditUtilities editUtilities = new EditUtilities();
	EditSystem editSystem = new EditSystem();

	public ContentAdmin(String headerTitle) {
		super(headerTitle);
		buildLoginPanel();

		getContent().add(loginPanel);
		getContent().add(separator);
		getContent().add(editTabs);

		wire();
	}

	private void wire() {
		setShowTag(UibAreas.ADMIN);
		showEditing(false);

		editRider.setService(this.service);
		editRaceHistory.setService(this.service);
		editEventDesc.setService(this.service);
		editEventType.setService(this.service);
		editEventLocation.setService(this.service);
		editSeason.setService(this.service);
		editUtilities.setService(this.service);
		editEvent.setService(this.service);
		editUsers.setService(this.service);
		editResultsUpload.setService(this.service);
		editGradeChangeRequest.setService(this.service);
		editSystem.setService(this.service);

		editTabs.setStylePrimaryName("edittabs");
		editTabs.addSelectionHandler(new SelectionHandler<Integer>() {

			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				if ((EditBase) editTabs.getWidget(event.getSelectedItem()) == editSystem) {
					controller.event(new InitSystem());
				} else if ((EditBase) editTabs.getWidget(event
						.getSelectedItem()) == editRider) {
					controller.event(new InitRider());
				} else if ((EditBase) editTabs.getWidget(event
						.getSelectedItem()) == editUtilities) {
					controller.event(new InitUtilities());
				}

			}
		});

		loginBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.replace(ACTVC.getLoginInfo().getLoginUrl());
			}
		});

		logoutBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.replace(ACTVC.getLoginInfo().getLogoutUrl());
			}
		});

		controller.addListener(LoginReturned.class,
				new ControllerListener<LoginReturned>() {

					@Override
					public void event(LoginReturned result) {
						ACTVC.setLoginInfo(result.getLoginInfo());

						int tabIdx = 0;
						tabMap.clear();
						if (ACTVC.getLoginInfo().isLoggedIn()) {
							loginTable.setWidget(0, 0, logoutBtn);
							loginMessage.setHTML("<h3>Hello "
									+ ACTVC.getLoginInfo().getName() + "</h3>");
							VerticalPanel vp = new VerticalPanel();

							if (ACTVC.getLoginInfo().isRoleSuperUser()) {
								addEdit(tabIdx++, editEvent, tabs[EVENTTAB]);
								addEdit(tabIdx++, editEventDesc,
										tabs[EVENTDESCTAB]);
								addEdit(tabIdx++, editEventType,
										tabs[EVENTTYPETAB]);
								addEdit(tabIdx++, editEventLocation,
										tabs[EVENTLOCATIONTAB]);
								addEdit(tabIdx++, editSeason, tabs[SEASONTAB]);
								addEdit(tabIdx++, editRider, tabs[RIDERTAB]);
								addEdit(tabIdx++, editRaceHistory,
										tabs[RACEHISTORYTAB]);
								addEdit(tabIdx++, editUsers, tabs[USERTAB]);
								addEdit(tabIdx++, editResultsUpload,
										tabs[UPLOADTAB]);
								addEdit(tabIdx++, editGradeChangeRequest,
										tabs[GRADECHANGE]);
								addEdit(tabIdx++, editSystem, tabs[SYSTEMTAB]);
								addEdit(tabIdx++, editUtilities,
										tabs[UTILITIESTAB]);

								if (ACTVC.getLoginInfo().getEmailAddress()
										.equals("test@example.com")
										|| ACTVC.getLoginInfo()
												.getEmailAddress()
												.equals("stephenmwills@gmail.com")
										|| ACTVC.getLoginInfo()
												.getEmailAddress()
												.equals("stephenmwills@actvets.cc")) {
									UibACTVC.testField.setVisible(true);
									UibACTVC.test2Field.setVisible(true);
								}
							} else {

								if (ACTVC.getLoginInfo().isRoleCommittee()
										|| ACTVC.getLoginInfo()
												.isRoleRaceDirector()) {
									addEdit(tabIdx++, editEvent, tabs[EVENTTAB]);
								}
								if (ACTVC.getLoginInfo().isRoleCommittee()) {
									addEdit(tabIdx++, editEventDesc,
											tabs[EVENTDESCTAB]);
									addEdit(tabIdx++, editEventType,
											tabs[EVENTTYPETAB]);
									addEdit(tabIdx++, editEventLocation,
											tabs[EVENTLOCATIONTAB]);
									addEdit(tabIdx++, editSeason,
											tabs[SEASONTAB]);
								}

								if (ACTVC.getLoginInfo()
										.isRoleMembershipTreasurer()
										|| ACTVC.getLoginInfo()
												.isRoleHandicapper()) {
									addEdit(tabIdx++, editRider, tabs[RIDERTAB]);
								}

								if (ACTVC.getLoginInfo().isRoleResults()) {
									addEdit(tabIdx++, editRaceHistory,
											tabs[RACEHISTORYTAB]);
									addEdit(tabIdx++, editResultsUpload,
											tabs[UPLOADTAB]);
								}

								if (ACTVC.getLoginInfo().isRoleHandicapper()) {
									addEdit(tabIdx++, editGradeChangeRequest,
											tabs[GRADECHANGE]);
								}

								if (ACTVC.getLoginInfo().isRoleSystem()) {
									addEdit(tabIdx++, editSystem,
											tabs[SYSTEMTAB]);
								}

							}

							showEditing(true);

						} else {
							loginMessage
									.setText("You need to log in with a Google account registered for this website");
							loginTable.setWidget(0, 0, loginBtn);
							showEditing(false);
						}
					}

					private void addEdit(int tabIdx, EditBase editWidget,
							String tabDesc) {
						editTabs.add(editWidget, tabDesc);
						if (tabIdx == 0) {
							editTabs.selectTab(0);
						}
						tabMap.put(tabIdx, tabDesc);
					}

				});

		init();
	}

	private void init() {
		// controller.event(new ShowFutureEvent(-147L));

	}

	@Override
	void hbClickEvent(ClickEvent event) {
		controller.event(new ShowOnly(UibAreas.DUAL));
	}

	private void buildLoginPanel() {
		loginPanel.add(loginMessage);
		loginPanel.add(loginTable);
	}

	private void showEditing(boolean show) {
		separator.setVisible(show);
		editTabs.setVisible(show);
	}
}
