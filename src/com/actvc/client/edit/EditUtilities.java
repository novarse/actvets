package com.actvc.client.edit;

import com.actvc.client.ACTVC;
import com.actvc.client.common.MyConst;
import com.actvc.client.common.MyLog;
import com.actvc.client.controller.Controller;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.TS;
import com.actvc.client.event.DeleteUtilReturned;
import com.actvc.client.event.GetTSForUtilities;
import com.actvc.client.event.InitUtilities;
import com.actvc.client.event.LoadingScreenHideIt;
import com.actvc.client.event.SetTSForEditUtilitiesReturned;
import com.actvc.client.event.UtilDeleteFailed;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EditUtilities extends EditBase {
	private static Controller controller = ACTVC.getInstance().getController();
	private VerticalPanel delRbPanel;
	private String choice = null;
	private Button extractBtn;
	private Button deleteBtn;
	private int dataDeleted;
	private Button initMaxIdBtn;
	private TextBox maxId;
	private VerticalPanel extractRbPanel;
	private VerticalPanel initIdPanel;
	private String extractChoice;
	private VerticalPanel getRiderIdsPanel;
	private FileUpload fileUpload;
	private Button uploadBtn;
	private FormPanel getRiderIdForm;
	private VerticalPanel miscPanel;
	private CheckBox raceDayFileUploadUpdatesRidersChk;
	private TS system;

	@Override
	void buildContent() {
		extractBtn = new Button("Extract");
		deleteBtn = new Button("Delete");
		delRbPanel = new VerticalPanel();
		initMaxIdBtn = new Button("Initialise maximum Key ID value");
		maxId = new TextBox();
		extractRbPanel = new VerticalPanel();
		initIdPanel = new VerticalPanel();
		getRiderIdsPanel = new VerticalPanel();
		uploadBtn = new Button("Get Ids");
		getRiderIdForm = new FormPanel();
		miscPanel = new VerticalPanel();
		raceDayFileUploadUpdatesRidersChk = new CheckBox(
				"Race Day File Upload updates Rider details");

		buildExtractRbPanel();
		buildDelRbPanel();
		buildInitIdPanel();
		buildGetRiderIdsPanel();
		buildMiscPanel();

		this.add(extractRbPanel);
		this.add(new HTML("<hr>"));
		this.add(delRbPanel);
		this.add(new HTML("<hr>"));
		this.add(initIdPanel);
		this.add(new HTML("<hr>"));
		this.add(getRiderIdsPanel);
		this.add(new HTML("<hr>"));
		this.add(miscPanel);
	}

	private void buildMiscPanel() {
		miscPanel.add(new HTML("<h3>Miscellaneous</h3>"));
		miscPanel.add(raceDayFileUploadUpdatesRidersChk);
	}

	private void buildGetRiderIdsPanel() {
		fileUpload = new FileUpload();
		fileUpload.getElement().setAttribute("size", "40");

		FlexTable getRiderIdsTbl = new FlexTable();
		getRiderIdsTbl.setWidget(0, 0, fileUpload);
		getRiderIdsTbl.setWidget(0, 1, uploadBtn);

		getRiderIdsPanel
				.add(new HTML(
						"<h3>Get Rider Id's</h3>Select the input file containing a comma separated file with rider details in order of rider number, surname and last name."));
		getRiderIdForm.add(getRiderIdsTbl);
		getRiderIdsPanel.add(getRiderIdForm);
	}

	private void buildInitIdPanel() {
		FlexTable initMaxIdTbl = new FlexTable();
		initMaxIdTbl.setWidget(0, 0, initMaxIdBtn);
		initMaxIdTbl.setWidget(0, 1, new Label("Set Id up to"));
		initMaxIdTbl.setWidget(0, 2, maxId);

		initIdPanel
				.add(new HTML(
						"<h3>Initialise base Id</h3>"
								+ "This will increment the starting Id. Only need to do this "
								+ "once after a new database, populated with imported data, has been established.<br>"
								+ "Used for auto-generated Ids."));
		initIdPanel.add(initMaxIdTbl);
	}

	@Override
	void wireEtc() {
		maxId.setText("2000");
		fieldScrollable.setVisible(false);
		getRiderIdForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		getRiderIdForm.setMethod(FormPanel.METHOD_POST);
		fileUpload.setName("getriderids");

		extractBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (extractChoice == null) {
					Window.alert("Make a Selection");
				} else {
					if (Window
							.confirm("Are you quite sure you want to extract data for "
									+ MyConst.getJdodesc()[Integer
											.parseInt(extractChoice)] + "?")) {
						String link = GWT.getHostPageBaseURL()
								+ "getdata?entitytype=" + extractChoice;
						Window.open(link, "_blank", "");
					}
				}
			}
		});

		deleteBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (choice == null) {
					Window.alert("Make a Selection");
				} else {
					if (Window
							.confirm("Are you quite sure you want to delete data for "
									+ MyConst.getJdodesc()[Integer
											.parseInt(choice)] + "?")) {
						if (Window.confirm("You REALLY sure?")) {
							dataDeleted = 0;
							service.utilDelete(Integer.parseInt(choice));
						}
					}
				}
			}
		});

		initMaxIdBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				try {
					if (maxId.getText().trim().length() == 0) {
						Window.alert("Enter a numeric value");
						maxId.setFocus(true);
						return;
					}
					if (Window.confirm("Are you sure you want to do this?")) {
						if (Window.confirm("You REALLY sure?")) {
							service.setMaxIdVal(Integer.parseInt(maxId
									.getText().trim()));
						}
					}
				} catch (RuntimeException e) {
					MyLog.log(e.toString());
					Window.alert("Enter a numeric value");
					maxId.selectAll();
					maxId.setFocus(true);
				}
			}
		});

		uploadBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (fileUpload.getFilename().isEmpty()) {
					Window.alert("Select an input file");
				} else {
					getRiderIdForm.setAction(GWT.getHostPageBaseURL()
							+ "getriderids");

					getRiderIdForm.submit();
				}
			}
		});

		raceDayFileUploadUpdatesRidersChk.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				system.setRaceDayFileUploadUpdatesRiders(raceDayFileUploadUpdatesRidersChk
						.getValue());
				service.saveTS(system, new SetTSForEditUtilitiesReturned());
			}
		});

		controller.addListener(SetTSForEditUtilitiesReturned.class,
				new ControllerListener<SetTSForEditUtilitiesReturned>() {

					@Override
					public void event(SetTSForEditUtilitiesReturned event) {
						String returnMessage = raceDayFileUploadUpdatesRidersChk
								.getValue() ? "Now uploaded 'race day' files will update Rider details."
								: "Now uploaded 'race day' files will create pending riders and will NOT update rider details.";
						Window.alert(returnMessage);
					}
				});

		controller.addListener(DeleteUtilReturned.class,
				new ControllerListener<DeleteUtilReturned>() {

					@Override
					public void event(DeleteUtilReturned result) {
						if (result.getDataDeleted() > 0) {
							loadingScreen.setText("Deleting "
									+ MyConst.getJdodesc()[Integer
											.parseInt(choice)]
									+ ", "
									+ Integer.toString(dataDeleted += result
											.getDataDeleted()) + " deleted");
							service.utilDelete(Integer.parseInt(choice));
						} else {
							controller.event(new LoadingScreenHideIt());
							Window.alert(dataDeleted + " deleted");
						}
					}
				});

		controller.addListener(UtilDeleteFailed.class,
				new ControllerListener<UtilDeleteFailed>() {

					@Override
					public void event(UtilDeleteFailed result) {
						deleteBtn.setEnabled(true);
						controller.event(new LoadingScreenHideIt());
						Window.alert("Delete failed: "
								+ result.getThrowable().getMessage());
					}
				});

		controller.addListener(InitUtilities.class,
				new ControllerListener<InitUtilities>() {

					@Override
					public void event(InitUtilities event) {
						service.getTS(new GetTSForUtilities());
					}
				});

		controller.addListener(GetTSForUtilities.class,
				new ControllerListener<GetTSForUtilities>() {

					@Override
					public void event(GetTSForUtilities event) {
						system = event.getSystem();
						raceDayFileUploadUpdatesRidersChk.setValue(system
								.isRaceDayFileUploadUpdatesRiders());
					}
				});

	}

	private void buildDelRbPanel() {
		delRbPanel
				.add(new HTML(
						"<h3>Delete data</h3><b>Select a table to delete from then click the Delete button</b>"));
		for (int i = 0; i < MyConst.getJdodesc().length; i++) {
			RadioButton rb = new RadioButton("jdo", MyConst.getJdodesc()[i]);
			rb.setText(MyConst.getJdodesc()[i]);
			rb.setFormValue(Integer.toString(i));

			delRbPanel.add(rb);

			rb.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					RadioButton r = (RadioButton) event.getSource();
					choice = r.getFormValue();
				}
			});
		}
		delRbPanel.add(deleteBtn);
	}

	private void buildExtractRbPanel() {
		extractRbPanel
				.add(new HTML(
						"<h3>Extract data</h3><b>Select a table and then click the Extract button</b>"));
		for (int i = 0; i < MyConst.getJdodesc().length; i++) {
			RadioButton rb = new RadioButton("extract", MyConst.getJdodesc()[i]);
			rb.setText(MyConst.getJdodesc()[i]);
			rb.setFormValue(Integer.toString(i));

			extractRbPanel.add(rb);

			rb.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					RadioButton r = (RadioButton) event.getSource();
					extractChoice = r.getFormValue();
				}
			});
		}
		extractRbPanel.add(extractBtn);
	}

	@Override
	void resetForm() {
		// TODO Auto-generated method stub

	}

}
