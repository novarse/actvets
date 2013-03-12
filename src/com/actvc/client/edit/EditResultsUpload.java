package com.actvc.client.edit;

import com.actvc.client.ACTVC;
import com.actvc.client.ContentEventDetail;
import com.actvc.client.ContentRaceResults;
import com.actvc.client.common.MyUtils;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.TE;
import com.actvc.client.event.GetEventDescByEventIdReturned;
import com.actvc.client.event.LoadRecentReturned;
import com.actvc.client.event.LoadingScreenHideIt;
import com.actvc.client.event.LoadingScreenShowIt;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class EditResultsUpload extends EditBase {
	private boolean lookupLoaded = false;
	private Button uploadBtn;
	private FileUpload fileUpload;
	private FormPanel form;
	private Label lookupLbl;
	private ListBox lookup;
	private FlexTable lookupTable;
	private TextBox description;

	@Override
	void buildContent() {
		description = new TextBox();
		description.setWidth("370px");
		description.setReadOnly(true);
		lookupLbl = new Label("Select Event");
		lookup = new ListBox();
		form = new FormPanel();
		uploadBtn = new Button("Upload Results");
		fileUpload = new FileUpload();
		fileUpload.getElement().setAttribute("size", "40");

		lookupTable = new FlexTable();
		lookupTable.setWidget(0, 0, lookupLbl);
		lookupTable.setWidget(0, 1, lookup);
		lookupTable.setWidget(0, 2, description);
		lookupTable.getColumnFormatter().setWidth(0, "80px");

		contentTbl.setWidget(0, 0, new Label(
				"Select the results file to upload"));
		contentTbl.getFlexCellFormatter().setColSpan(0, 0, 2);
		contentTbl.setWidget(1, 0, fileUpload);
		contentTbl.setWidget(1, 1, uploadBtn);

		form.add(contentTbl);

		contentPanel.add(lookupTable);
		contentPanel.add(form);
	}

	@Override
	void resetForm() {
		lookup.setSelectedIndex(0);
		description.setText("");
	}

	@Override
	void wireEtc() {
		lookup.addItem("Select an Event");
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		fileUpload.setName("fileupload");

		lookup.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (!isLookupLoaded()) {
					service.loadRecentEvents(ACTVC.getLoginInfo()
							.isRoleSuperUser());
				}
			}
		});

		uploadBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (fileUpload.getFilename().isEmpty()) {
					Window.alert("Select a race result file");
				} else if (lookup.getSelectedIndex() == 0) {
					Window.alert("Select an Event");
					lookup.setFocus(true);
				} else {
					controller.event(new LoadingScreenShowIt());
					form.setAction(GWT.getHostPageBaseURL()
							+ "fileupload?eventid="
							+ lookup.getValue(lookup.getSelectedIndex()));
					form.submit();
				}
			}
		});

		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				if (event.getResults() != null) {
					controller.event(new LoadingScreenHideIt());
					Window.alert(event.getResults());
					resetForm();
					service.loadPending();
				}
			}
		});

		controller.addListener(LoadRecentReturned.class,
				new ControllerListener<LoadRecentReturned>() {

					@Override
					public void event(LoadRecentReturned results) {
						lookup.clear();
						setLookupLoaded(true);
						if (results.getEvents() != null) {
							lookup.addItem("Select an Event");
							for (TE e : results.getEvents()) {
								lookup.addItem(MyUtils.getDateStr(e.getDate()),
										e.getId().toString());
							}
						}

					}

				});

		lookup.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if (lookup.getSelectedIndex() == 0) {
					description.setText("");
				} else {
					service.getEventDescByEventId(Long.parseLong(lookup
							.getValue(lookup.getSelectedIndex())));
				}
			}
		});

		controller.addListener(GetEventDescByEventIdReturned.class,
				new ControllerListener<GetEventDescByEventIdReturned>() {

					@Override
					public void event(GetEventDescByEventIdReturned result) {
						ContentRaceResults.setLastEventID(null);
						ContentEventDetail.setLastEventID(null);
						if (result != null) {
							String descStr = result.getEventDesc();
							if (descStr.length() > 100) {
								descStr = descStr.substring(0, 100);
							}
							description.setText(descStr);
						}
					}
				});
	}

	public void setLookupLoaded(boolean lookupLoaded) {
		this.lookupLoaded = lookupLoaded;
	}

	public boolean isLookupLoaded() {
		return lookupLoaded;
	}

}