package com.actvc.client.edit;

import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.TU;
import com.actvc.client.event.DeleteAdminReturned;
import com.actvc.client.event.GetAdminReturned;
import com.actvc.client.event.LoadAdminReturned;
import com.actvc.client.event.LoadingScreenHideIt;
import com.actvc.client.event.SaveAdminFailed;
import com.actvc.client.event.SaveAdminReturned;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class EditUser extends LookupEditBase {
	TextBox email;
	private TextBox name;
	private CheckBox roleSuperUserChk;
	private CheckBox roleMembershipTreasurerChk;
	private CheckBox roleResultsChk;
	private CheckBox roleHandicapperChk;
	private CheckBox roleRaceDirectorChk;
	private CheckBox roleCommitteeChk;
	private CheckBox roleSystemChk;

	@Override
	void buildContent() {
		email = new TextBox();
		name = new TextBox();
		roleSuperUserChk = new CheckBox();
		roleCommitteeChk = new CheckBox();
		roleHandicapperChk = new CheckBox();
		roleMembershipTreasurerChk = new CheckBox();
		roleRaceDirectorChk = new CheckBox();
		roleResultsChk = new CheckBox();
		roleSystemChk = new CheckBox();
		contentTbl = new FlexTable();
		contentTbl.setWidget(0, 0, new Label("Email"));
		contentTbl.setWidget(0, 1, email);
		contentTbl.setWidget(1, 0, new Label("Name"));
		contentTbl.setWidget(1, 1, name);
		contentTbl.setWidget(2, 0, new Label("Super User"));
		contentTbl.setWidget(2, 1, roleSuperUserChk);
		contentTbl.setWidget(3, 0, new Label("Membership/Treasurer"));
		contentTbl.setWidget(3, 1, roleMembershipTreasurerChk);
		contentTbl.setWidget(4, 0, new Label("Results Handler"));
		contentTbl.setWidget(4, 1, roleResultsChk);
		contentTbl.setWidget(5, 0, new Label("Handicapper"));
		contentTbl.setWidget(5, 1, roleHandicapperChk);
		contentTbl.setWidget(6, 0, new Label("Race Director"));
		contentTbl.setWidget(6, 1, roleRaceDirectorChk);
		contentTbl.setWidget(7, 0, new Label("Committee Member"));
		contentTbl.setWidget(7, 1, roleCommitteeChk);
		contentTbl.setWidget(8, 0, new Label("System Administrator"));
		contentTbl.setWidget(8, 1, roleSystemChk);

		contentPanel.add(contentTbl);

		email.setEnabled(false);
		email.setWidth("280px");

	}

	@Override
	void doDelete() {
		if (lookup.getSelectedIndex() == 0) {
			Window.alert("Select an Administrator to delete");
			return;
		}
		service.deleteAdmin(lookup.getValue(lookup.getSelectedIndex()));

	}

	@Override
	void doLookupChange(ChangeEvent event) {
		if (lookup.getSelectedIndex() != 0)
			service.getAdmin(lookup.getValue(lookup.getSelectedIndex()));
	}

	@Override
	void doSave() {
		email.setText(email.getText().trim());
		name.setText(name.getText().trim());
		if (newRb.getValue()) {
			if (email.getText().length() == 0) {
				Window.alert("Enter an Email address");
				email.setFocus(true);
				return;
			} else if (checkEmailUsed(email.getText())) {
				return;
			}
		}
		if (name.getText().length() == 0) {
			Window.alert("Enter a name");
			name.setFocus(true);
		} else {
			TU a = new TU();
			a.setEmail(email.getText());
			a.setName(name.getText());
			a.setRoleSuperUser(roleSuperUserChk.getValue());
			a.setRoleCommittee(roleCommitteeChk.getValue());
			a.setRoleHandicapper(roleHandicapperChk.getValue());
			a.setRoleMembershipTreasurer(roleMembershipTreasurerChk.getValue());
			a.setRoleRaceDirector(roleRaceDirectorChk.getValue());
			a.setRoleResults(roleResultsChk.getValue());
			a.setRoleSystem(roleSystemChk.getValue());

			saveBtn.setEnabled(false);
			service.saveAdmin(a);
		}

	}

	private boolean checkEmailUsed(String email) {
		for (int i = 1; i < lookup.getItemCount(); i++) {
			if (lookup.getValue(i).equals(email)) {
				editRb.setValue(true);
				deleteBtn.setEnabled(true);
				lookup.setSelectedIndex(i);
				Window.alert("An Administrator with that Email already exists");
				service.getAdmin(lookup.getValue(lookup.getSelectedIndex()));
				return true;
			}
		}
		return false;
	}

	@Override
	void wireEtc() {
		lookup.addItem("Select a Administrator");

		controller.addListener(LoadAdminReturned.class,
				new ControllerListener<LoadAdminReturned>() {

					@Override
					public void event(LoadAdminReturned result) {
						if (!result.getAdminList().isEmpty()) {

							lookup.clear();
							lookup.addItem("Select an Administrator");
							for (TU a : result.getAdminList()) {
								lookup.addItem(a.getName(), a.getEmail());
							}
						}
						setLookupLoaded(true);
					}
				});

		controller.addListener(GetAdminReturned.class,
				new ControllerListener<GetAdminReturned>() {

					@Override
					public void event(GetAdminReturned result) {
						try {
							TU u = result.getUser();
							if (u != null) {
								email.setText(u.getEmail());
								name.setText(u.getName());
								roleSuperUserChk.setValue(u.isRoleSuperUser());
								roleCommitteeChk.setValue(u.isRoleCommittee());
								roleHandicapperChk.setValue(u
										.isRoleHandicapper());
								roleMembershipTreasurerChk.setValue(u
										.isRoleMembershipTreasurer());
								roleRaceDirectorChk.setValue(u
										.isRoleRaceDirector());
								roleResultsChk.setValue(u.isRoleResults());
								roleSystemChk.setValue(u.isRoleSystem());

								enableContentFields(true);
							}
						} finally {
							controller.event(new LoadingScreenHideIt());
						}
					}
				});

		controller.addListener(SaveAdminReturned.class,
				new ControllerListener<SaveAdminReturned>() {

					@Override
					public void event(SaveAdminReturned event) {
						resetForm();
						Window.alert("Data Saved");
						saveBtn.setEnabled(true);
						service.loadAdmin();

					}
				});

		controller.addListener(SaveAdminFailed.class,
				new ControllerListener<SaveAdminFailed>() {

					@Override
					public void event(SaveAdminFailed result) {
						saveBtn.setEnabled(true);
					}
				});

		controller.addListener(DeleteAdminReturned.class,
				new ControllerListener<DeleteAdminReturned>() {

					@Override
					public void event(DeleteAdminReturned result) {
						resetForm();
						Window.confirm("Delete was successful");
						service.loadAdmin();
					}
				});
	}

	@Override
	void resetForm() {
		lookup.setSelectedIndex(0);
		email.setText("");
		name.setText("");
		roleSuperUserChk.setValue(false);
		roleHandicapperChk.setValue(false);
		roleMembershipTreasurerChk.setValue(false);
		roleCommitteeChk.setValue(false);
		roleRaceDirectorChk.setValue(false);
		roleResultsChk.setValue(false);
		roleSystemChk.setValue(false);
		if (editRb.getValue()) {
			enableContentFields(false);
		}

	}

	@Override
	void doFocusHandler() {
		if (!isLookupLoaded()) {
			service.loadAdmin();
		}
	}

}
