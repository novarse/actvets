package com.actvc.client;

import com.actvc.client.common.MyConst;
import com.actvc.client.common.MyDate;
import com.actvc.client.common.MyUtils;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.event.CheckGradeChangeDetailsFailed;
import com.actvc.client.event.CheckGradeChangeDetailsReturned;
import com.actvc.client.event.ShowOnly;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class ContentGradeChange extends ContentWidget {

	private final FlexTable contentTbl = new FlexTable();
	private final TextBox number = new TextBox();
	private final TextBox lastName = new TextBox();
	private final TextBox firstName = new TextBox();
	private final MyDate dob = new MyDate();
	private final TextBox newGrade = new TextBox();
	private final ListBox newSubGrade = new ListBox();
	private final TextBox newCriteriumGrade = new TextBox();

	Button submit = new Button("Submit");

	public ContentGradeChange(String headerTitle) {
		super(headerTitle);

		buildContents();

		getContent()
				.add(new HTML(
						"Here you can request for your grade to be changed<br>Fill out the details below and click the Submit button"));
		getContent().add(contentTbl);
		getContent().add(submit);

		wire();
	}

	private void buildContents() {
		setupSubGrade();

		contentTbl.setWidget(0, 0, new Label("Number"));
		contentTbl.setWidget(0, 1, number);
		contentTbl.setWidget(1, 0, new Label("First Name"));
		contentTbl.setWidget(1, 1, firstName);
		contentTbl.setWidget(2, 0, new Label("Last Name"));
		contentTbl.setWidget(2, 1, lastName);
		contentTbl.setWidget(3, 0, new Label("Date of Birth"));
		contentTbl.setWidget(3, 1, dob);
		contentTbl.setWidget(4, 0, new Label("New Grade"));
		contentTbl.setWidget(4, 1, newGrade);
		contentTbl.setWidget(5, 0, new Label("New Sub-grade"));
		contentTbl.setWidget(5, 1, newSubGrade);
		contentTbl.setWidget(6, 0, new Label("New Criterium Grade"));
		contentTbl.setWidget(6, 1, newCriteriumGrade);
	}

	private void setupSubGrade() {
		newSubGrade.clear();
		newSubGrade.addItem("");
		for (int i = 1; i <= MyConst.MAX_SUBGRADE; i++) {
			newSubGrade.addItem(Integer.toString(i));
		}
	}

	@Override
	void hbClickEvent(ClickEvent event) {
		controller.event(new ShowOnly(UibAreas.DUAL));
	}

	public void wire() {
		setShowTag(UibAreas.CHANGEGRADE);
		number.setStylePrimaryName("numbertextbox");
		lastName.setStylePrimaryName("capitalize");
		firstName.setStylePrimaryName("capitalize");
		newGrade.setStylePrimaryName("uppercase");
		newSubGrade.setStylePrimaryName("numbertextbox");
		newCriteriumGrade.setStylePrimaryName("uppercase");

		submit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				int num = 0;

				number.getText().trim();
				lastName.getText().trim();
				firstName.getText().trim();
				newGrade.getText().trim();
				newCriteriumGrade.getText().trim();

				try {
					num = Integer.parseInt(number.getText());
				} catch (Exception e) {
					Window.alert("A Rider Number is required");
					number.setFocus(true);
				}

				if (number.getText().isEmpty()) {
					Window.alert("A Rider Number is required");
					number.setFocus(true);
				} else if (lastName.getText().isEmpty()) {
					Window.alert("A Last Name is required");
					lastName.setFocus(true);
				} else if (firstName.getText().isEmpty()) {
					Window.alert("A First Name is required");
					firstName.setFocus(true);
				} else if (newGrade.getText().isEmpty()
						&& newSubGrade.getSelectedIndex() == 0
						&& newCriteriumGrade.getText().isEmpty()) {
					Window.alert("A Grade, Sub-grade or Criterium Grade is required");
					newGrade.setFocus(true);
				} else {
					submit.setEnabled(false);
					service.checkGradeChangeDetails(num,
							MyUtils.capitalizeFirst(lastName.getText()),
							MyUtils.capitalizeFirst(firstName.getText()),
							dob.getDate(), newGrade.getText().toUpperCase(),
							newSubGrade.getSelectedIndex(),
							newCriteriumGrade.getText());
				}

			}
		});

		controller.addListener(CheckGradeChangeDetailsFailed.class,
				new ControllerListener<CheckGradeChangeDetailsFailed>() {

					@Override
					public void event(CheckGradeChangeDetailsFailed result) {
						submit.setEnabled(true);
					}
				});

		controller.addListener(CheckGradeChangeDetailsReturned.class,
				new ControllerListener<CheckGradeChangeDetailsReturned>() {

					@Override
					public void event(CheckGradeChangeDetailsReturned result) {
						submit.setEnabled(true);
						ClearFields();
						if (result.getDetailsOk()) {
							Window.alert("Your request has been sent to the administrator");
						} else {
							Window.alert("Incorrect details entered or Rider is inactive. Please try again");
						}
					}

				});
	}

	private void ClearFields() {
		number.setText("");
		lastName.setText("");
		firstName.setText("");
		dob.reset();
		newGrade.setText("");
		newSubGrade.setSelectedIndex(0);
		newCriteriumGrade.setText("");
	}
}
