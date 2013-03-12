package com.actvc.client.edit;

import java.util.HashMap;
import java.util.Map;

import com.actvc.client.common.MyConst;
import com.actvc.client.common.MyUtils;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.TGCR;
import com.actvc.client.event.ChangeGradeFailed;
import com.actvc.client.event.ChangeGradeReturned;
import com.actvc.client.event.DeleteGradeChangeRequestReturned;
import com.actvc.client.event.LoadGradeChangeRequests;
import com.actvc.client.event.LoadingScreenHideIt;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class EditGradeChangeRequest extends LookupEditBase {
	Map<Long, TGCR> gcrMap = new HashMap<Long, TGCR>();
	TextBox numberEd;
	TextBox dobEd;

	TextBox currentGradeEd;
	TextBox currentSubgradeEd;
	TextBox currentCriteriumGradeEd;

	TextBox newGradeEd;
	ListBox newSubgradeEd;
	TextBox newCriteriumGradeEd;
	TextBox dateSubmittedEd;

	@Override
	void doDelete() {
		saveBtn.setEnabled(false);
		service.deleteGradeChangeRequest(gcrMap.get(getLookupId()).getId());
	}

	@Override
	void doFocusHandler() {
		if (!isLookupLoaded()) {
			service.loadGradeChangeRequests();
		}
	}

	@Override
	void doLookupChange(ChangeEvent event) {
		if (lookup.getSelectedIndex() == 0) {
			resetForm();
		} else {
			TGCR gcr = gcrMap.get(getLookupId());
			numberEd.setText(Integer.toString(gcr.getNumber()));
			dobEd.setText(MyUtils.getDateStr(gcr.getDob()));
			currentGradeEd.setText(gcr.getCurrentGrade());
			currentSubgradeEd
					.setText(Integer.toString(gcr.getCurrentSubgrade()));
			currentCriteriumGradeEd.setText(gcr.getCurrentCriteriumGrade());
			newGradeEd.setText(gcr.getNewGrade());
			newSubgradeEd
					.setSelectedIndex(gcr.getNewSubgrade() > MyConst.MAX_SUBGRADE ? 0
							: gcr.getNewSubgrade());
			newCriteriumGradeEd.setText(gcr.getNewCriteriumGrade());
			dateSubmittedEd.setText(MyUtils.getDateStr(gcr.getSubmitDate()));
		}
	}

	@Override
	void doSave() {
		newGradeEd.setText(newGradeEd.getText().trim().toUpperCase());
		newCriteriumGradeEd.setText(newCriteriumGradeEd.getText().trim()
				.toUpperCase());
		saveBtn.setEnabled(false);
		service.changeGrade(newGradeEd.getText(), newSubgradeEd
				.getSelectedIndex(), newCriteriumGradeEd.getText(),
				gcrMap.get(getLookupId()).getId());
	}

	@Override
	void buildContent() {
		numberEd = new TextBox();
		dobEd = new TextBox();
		currentGradeEd = new TextBox();
		currentSubgradeEd = new TextBox();
		currentCriteriumGradeEd = new TextBox();
		newGradeEd = new TextBox();
		newSubgradeEd = new ListBox();
		newCriteriumGradeEd = new TextBox();
		dateSubmittedEd = new TextBox();
		setupSubGrade();

		contentTbl.setWidget(0, 0, new Label("Number"));
		contentTbl.setWidget(0, 1, numberEd);
		contentTbl.setWidget(1, 0, new Label("Date of Birth"));
		contentTbl.setWidget(1, 1, dobEd);
		contentTbl.setWidget(2, 0, new Label("Current Grade"));
		contentTbl.setWidget(2, 1, currentGradeEd);
		contentTbl.setWidget(3, 0, new Label("Current Sub-grade"));
		contentTbl.setWidget(3, 1, currentSubgradeEd);
		contentTbl.setWidget(4, 0, new Label("Current Criterium Grade"));
		contentTbl.setWidget(4, 1, currentCriteriumGradeEd);
		contentTbl.setWidget(5, 0, new Label("New Grade"));
		contentTbl.setWidget(5, 1, newGradeEd);
		contentTbl.setWidget(6, 0, new Label("New Sub-grade"));
		contentTbl.setWidget(6, 1, newSubgradeEd);
		contentTbl.setWidget(7, 0, new Label("New Criterium Grade"));
		contentTbl.setWidget(7, 1, newCriteriumGradeEd);
		contentTbl.setWidget(8, 0, new Label("Date Submitted"));
		contentTbl.setWidget(8, 1, dateSubmittedEd);

		contentPanel.add(contentTbl);

	}

	private void setupSubGrade() {
		newSubgradeEd.clear();
		newSubgradeEd.addItem("Select a Sub Grade");
		for (int i = 1; i <= MyConst.MAX_SUBGRADE; i++) {
			newSubgradeEd.addItem(Integer.toString(i));
		}
	}

	@Override
	void resetForm() {
		numberEd.setText("");
		dobEd.setText("");
		currentGradeEd.setText("");
		currentSubgradeEd.setText("");
		currentCriteriumGradeEd.setText("");
		newGradeEd.setText("");
		newSubgradeEd.setSelectedIndex(0);
		newCriteriumGradeEd.setText("");
		dateSubmittedEd.setText("");
		lookup.setSelectedIndex(0);
		if (editRb.getValue()) {
			enableContentFields(false);
		}
	}

	@Override
	void wireEtc() {
		numberEd.setReadOnly(true);
		editNewTbl.setVisible(false);
		dobEd.setReadOnly(true);
		currentGradeEd.setReadOnly(true);
		currentSubgradeEd.setReadOnly(true);
		currentCriteriumGradeEd.setReadOnly(true);
		dateSubmittedEd.setReadOnly(true);
		newGradeEd.setStylePrimaryName("uppercase");
		newCriteriumGradeEd.setStylePrimaryName("uppercase");

		lookup.addItem("Select a Rider");

		controller.addListener(LoadGradeChangeRequests.class,
				new ControllerListener<LoadGradeChangeRequests>() {

					@Override
					public void event(LoadGradeChangeRequests event) {
						try {
							if (event.getList() == null
									|| event.getList().isEmpty()) {
								lookup.clear();
								lookup.addItem("There are no grade change requests");
							} else {
								lookup.clear();
								lookup.addItem("Select a Rider");
								gcrMap.clear();
								Long i = 1L;
								for (TGCR gcr : event.getList()) {
									lookup.addItem(gcr.getLastName() + ", "
											+ gcr.getFirstName(), i.toString());
									gcrMap.put(i++, gcr);
								}

								setLookupLoaded(true);
							}
						} finally {
							controller.event(new LoadingScreenHideIt());
						}
					}
				});

		controller.addListener(ChangeGradeReturned.class,
				new ControllerListener<ChangeGradeReturned>() {

					@Override
					public void event(ChangeGradeReturned event) {
						Window.alert("Grade updated");
						saveBtn.setEnabled(true);
						resetForm();
						service.loadGradeChangeRequests();
					}
				});

		controller.addListener(ChangeGradeFailed.class,
				new ControllerListener<ChangeGradeFailed>() {

					@Override
					public void event(ChangeGradeFailed result) {
						saveBtn.setEnabled(true);
					}
				});

		controller.addListener(DeleteGradeChangeRequestReturned.class,
				new ControllerListener<DeleteGradeChangeRequestReturned>() {

					@Override
					public void event(DeleteGradeChangeRequestReturned result) {
						Window.alert("Request deleted");
						resetForm();
						service.loadGradeChangeRequests();
					}
				});
	}
}
