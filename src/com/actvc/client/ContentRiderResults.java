package com.actvc.client;

import java.util.Date;
import java.util.List;

import com.actvc.client.common.MyAnchor;
import com.actvc.client.common.MyLog;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.Person;
import com.actvc.client.entities.TRH;
import com.actvc.client.event.GetRiderHistoryByIdReturned;
import com.actvc.client.event.ShowOnly;
import com.actvc.client.event.ShowRaceResults;
import com.actvc.client.event.ShowRiderResults;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;

public class ContentRiderResults extends ContentWidget {

	protected Long riderID = null;
	protected HTML riderDetails;

	public ContentRiderResults(String headerTitle) {
		super(headerTitle);

		wire();
	}

	private void wire() {
		setShowTag(UibAreas.RIDERRESULTS);
		setHeaderImage(CLOSE);

		controller.addListener(ShowRiderResults.class,
				new ControllerListener<ShowRiderResults>() {

					@Override
					public void event(ShowRiderResults event) {
						if (!event.getRiderID().equals(riderID)) {
							service.getRiderHistoryById(event.getRiderID());
						} else {
							show();
						}
					}
				});

		controller.addListener(GetRiderHistoryByIdReturned.class,
				new ControllerListener<GetRiderHistoryByIdReturned>() {

					@Override
					public void event(GetRiderHistoryByIdReturned result) {
						if (result.getRiderDetails() != null) {
							Person r = result.getRiderDetails().getRider();
							int eventCount = result.getRiderDetails()
									.getEventCount();
							riderDetails = new HTML(
									r.getFirstName()
											+ " "
											+ r.getLastName()
											+ (r.getNumber() != -1 ? ", Race Number: "
													+ r.getNumber()
													: "")
											+ (r.getGrade().isEmpty() ? ""
													: ", Grade: "
															+ r.getGrade())
											+ (r.getSubGrade() > 0 ? ", Sub Grade: "
													+ Integer.toString(r
															.getSubGrade())
													: "")
											+ (r.getCriteriumGrade() == null
													|| r.getCriteriumGrade()
															.isEmpty() ? ""
													: ", Criterium Grade: "
															+ r.getCriteriumGrade())
											+ (eventCount >= 0 ? ", Number of Events for Club: "
													+ Integer
															.toString(eventCount)
													: ""));
							build(result.getRiderDetails().getRaceHistoryList());
						} else {
							Window.alert("No rider found");
						}
					}
				});

	}

	protected void addHistoryRiderDetails(TRH r, int subRow,
			HTMLTable resultsTable) {
		try {
			Date d = r.getDate();
			String newDate = d.getDate() + "/" + (d.getMonth() + 1) + "/"
					+ (d.getYear() + 1900);
			int row = resultsTable.getRowCount();
			MyAnchor dateLabel = new MyAnchor(newDate, r.getEventId());
			Label placeLbl;
			Label pointsLbl;

			resultsTable.setWidget(row, 0, dateLabel);
			resultsTable.setWidget(row, 1, new Label(r.getRaceGrade()));
			if (r.getPlace() == 10000) {
				placeLbl = new Label("");
			} else {
				placeLbl = new Label(Integer.toString(r.getPlace()));
			}
			resultsTable.setWidget(row, 2, placeLbl);

			if (r.getTime() == null || r.getTime().length() == 0) {
				resultsTable.setWidget(row, 3, new Label(""));
			} else {
				resultsTable.setWidget(row, 3, new Label(r.getTime()));
			}

			if (r.getPoints() == -1) {
				pointsLbl = new Label("");
			} else {
				pointsLbl = new Label(Integer.toString(r.getPoints()));
			}
			resultsTable.setWidget(row, 4, pointsLbl);

			if (subRow % 2 == 0)
				resultsTable.getRowFormatter().setStylePrimaryName(row,
						"resultstablealtrow");
			else
				resultsTable.getRowFormatter().setStylePrimaryName(row,
						"resultstablerow");

			for (int c = 0; c < 5; c++) {
				resultsTable.getCellFormatter().setStylePrimaryName(row, c,
						"resultstablecell");
			}

			dateLabel.setTitle("Click to show event details");

			pointsLbl.setStylePrimaryName("numbertextbox");
			placeLbl.setStylePrimaryName("numbertextbox");
			dateLabel.setStylePrimaryName("cursorpointer");
			dateLabel.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					MyAnchor ml = (MyAnchor) event.getSource();
					controller.event(new ShowRaceResults(ml.getTag()));
				}
			});
		} catch (RuntimeException e) {

			MyLog.log(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	@Override
	void hbClickEvent(ClickEvent event) {
		controller.event(new ShowOnly(UibAreas.DUAL));
	}

	public void build(List<TRH> rhList) {
		FlexTable resultsTable = new FlexTable();
		resultsTable.setStylePrimaryName("riderresultstable");
		getContent().clear();
		int i = 0;
		if (rhList.isEmpty()) {
			resultsTable.setWidget(0, 0, new Label(
					"There is no data for that rider"));
		} else {
			addRiderHeader(resultsTable);
			for (TRH r : rhList) {
				addHistoryRiderDetails(r, ++i, resultsTable);
			}
		}
		getContent().add(riderDetails);
		riderDetails.setStylePrimaryName("riderdetails");
		getContent().add(resultsTable);

		show();

	}

	private void addRiderHeader(FlexTable resultsTable) {
		resultsTable.setWidget(0, 0, new Label("Race Date"));
		resultsTable.setWidget(0, 1, new Label("Grade"));
		resultsTable.setWidget(0, 2, new Label("Place"));
		resultsTable.setWidget(0, 3, new Label("Time"));
		resultsTable.setWidget(0, 4, new Label("Points"));
		resultsTable.getRowFormatter().setStylePrimaryName(0,
				"resultstableheader");
		for (int c = 0; c < 5; c++) {
			resultsTable.getCellFormatter().setStylePrimaryName(0, c,
					"resultstablecell");
		}
	}

}
