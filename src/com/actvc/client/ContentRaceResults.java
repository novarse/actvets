package com.actvc.client;

import java.util.HashMap;
import java.util.Iterator;

import com.actvc.client.common.MyAnchor;
import com.actvc.client.common.MyConst;
import com.actvc.client.common.MyDate;
import com.actvc.client.common.MyLog;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.Person;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TED;
import com.actvc.client.entities.TEDTO;
import com.actvc.client.entities.TET;
import com.actvc.client.entities.TR;
import com.actvc.client.entities.TRH;
import com.actvc.client.event.GetRaceEventResultsReturned;
import com.actvc.client.event.ShowOnly;
import com.actvc.client.event.ShowRaceResults;
import com.actvc.client.event.ShowRiderResults;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;

public class ContentRaceResults extends ContentWidget {

	private static Long lastEventID = null;
	boolean isHandicap;
	protected Long newEventID = null;
	protected TE eventDetails;

	public ContentRaceResults(String headerTitle) {
		super(headerTitle);

		wire();
	}

	private void wire() {
		setShowTag(UibAreas.RACERESULTS);

		controller.addListener(ShowRaceResults.class,
				new ControllerListener<ShowRaceResults>() {

					@Override
					public void event(ShowRaceResults event) {
						if (!event.getEventID().equals(lastEventID)) {
							newEventID = event.getEventID();
							service.getRaceEventResults(event.getEventID());
						} else {
							show();
						}
					}
				});

		controller.addListener(GetRaceEventResultsReturned.class,
				new ControllerListener<GetRaceEventResultsReturned>() {

					@Override
					public void event(GetRaceEventResultsReturned event) {
						if (event.getEventDetails() != null) {
							buildResults(event.getEventDetails());
						}
					}
				});

		init();
	}

	private void init() {

	}

	@Override
	void hbClickEvent(ClickEvent event) {
		controller.event(new ShowOnly(UibAreas.DUAL));
	}

	private void addEventRaceHistoryDetails(TRH rh, Person rider, int subRow,
			HTMLTable resultsTable) {
		int row = resultsTable.getRowCount();
		try {
			MyAnchor lastNameLbl = new MyAnchor(rider.getLastName(),
					rh.getRiderId());
			MyAnchor firstNameLbl = new MyAnchor(rider.getFirstName(),
					rh.getRiderId());
			Label gradeLbl = new Label(rider.getGrade());
			Label criteriumGradeLbl = new Label(rider.getCriteriumGrade());
			Label placeLbl;
			Label pointsLbl;
			resultsTable.setWidget(row, 0, lastNameLbl);
			resultsTable.setWidget(row, 1, firstNameLbl);
			resultsTable.setWidget(row, 2, gradeLbl);
			resultsTable.setWidget(row, 3, criteriumGradeLbl);
			if (rider.getNumber() == -1) {
				resultsTable.setWidget(row, 4, new Label(""));
			} else {
				resultsTable.setWidget(row, 4,
						new Label(Integer.toString(rider.getNumber())));
			}
			if (isHandicap) {
				if (rh.getOverTheLine() == 10000) {
					placeLbl = new Label("");
				} else {
					placeLbl = new Label(Integer.toString(rh.getOverTheLine()));
				}
			} else {
				if (rh.getPlace() == 10000) {
					placeLbl = new Label("");
				} else {
					placeLbl = new Label(Integer.toString(rh.getPlace()));
				}
			}
			resultsTable.setWidget(row, 5, placeLbl);
			if (rh.getTime() == null || rh.getTime().length() == 0) {
				resultsTable.setWidget(row, 6, new Label(""));
			} else {
				resultsTable.setWidget(row, 6, new Label(rh.getTime()));
			}

			if (rh.getPoints() == -1) {
				pointsLbl = new Label("");
			} else {
				pointsLbl = new Label(Integer.toString(rh.getPoints()));
			}
			resultsTable.setWidget(row, 7, pointsLbl);

			if (rh.getComment() != null) {
				resultsTable.setWidget(row, 8, new Label(rh.getComment()));
			}
			if (subRow % 2 == 0) {
				resultsTable.getRowFormatter().setStylePrimaryName(row,
						"resultstablealtrow");
			} else {
				resultsTable.getRowFormatter().setStylePrimaryName(row,
						"resultstablerow");
			}

			for (int c = 0; c < 9; c++) {
				resultsTable.getCellFormatter().setStylePrimaryName(row, c,
						"resultstablecell");
			}

			placeLbl.setStylePrimaryName("numbertextbox");
			pointsLbl.setStylePrimaryName("numbertextbox");
			// firstNameLbl.setStylePrimaryName("ridername");
			// lastNameLbl.setStylePrimaryName("ridername");
			gradeLbl.setStylePrimaryName("cursorhelp");
			firstNameLbl.setTitle("Click to show rider details");
			lastNameLbl.setTitle("Click to show rider details");
			gradeLbl.setTitle("Riders normal grade");
			criteriumGradeLbl.setTitle("Riders normal criterium grade");

			lastNameLbl.addClickHandler(nameClickHandler());
			firstNameLbl.addClickHandler(nameClickHandler());
		} catch (RuntimeException e) {
			MyLog.log(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	private ClickHandler nameClickHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MyAnchor ml = (MyAnchor) event.getSource();
				controller.event(new ShowRiderResults(ml.getTag()));
			}
		};
	}

	private void buildResults(TEDTO tedto) {
		getContent().clear();
		TE event = (TE) tedto.getEventMap().values().toArray()[0];
		TET et = tedto.getTypeMap().get(event.getEventTypeId());
		TED ed = tedto.getDescriptionMap().get(event.getEventDescriptionId());
		HashMap<Long, TR> riderMap = tedto.getRiderMap();
		HTML raceDesc = new HTML(MyDate.days[event.getDate().getDay()]
				+ ", "
				+ event.getDate().getDate()
				+ " "
				+ MyDate.months[event.getDate().getMonth()]
				+ " "
				+ (event.getDate().getYear() + 1900)
				+ " - "
				+ tedto.getLocationMap().get(event.getLocationId())
						.getLocation()
				+ " "
				+ et.getDescription()
				+ " Dist("
				+ ed.getDistLong()
				+ "km"
				+ (ed.getDistShort().equals(ed.getDistLong()) ? ")" : "/"
						+ ed.getDistShort() + "km)"));

		isHandicap = et.getId() == MyConst.getHandicapId();
		int i = 0;
		String lastGrade = "  ";
		FlexTable resultsTable = new FlexTable();
		resultsTable.setStylePrimaryName("raceresultstable");
		if (tedto.getRiderMap().isEmpty()
				|| tedto.getRaceHistoryMap().isEmpty()) {
			resultsTable.setWidget(0, 0, new Label("No event details found"));
		} else {
			addEventRiderHeader(resultsTable);

			Iterator<TRH> iterator = tedto.getRaceHistoryMap().values()
					.iterator();
			while (iterator.hasNext()) {
				TRH rh = iterator.next();
				System.out.println(rh);
				if (isHandicap) {

				} else {
					String grade = rh.getRaceGrade();
					if (!grade.equals(lastGrade)) {
						int row = resultsTable.getRowCount();
						resultsTable.setWidget(row, 0, new Label(grade));
						resultsTable.getFlexCellFormatter().setColSpan(row, 0,
								9);
						resultsTable.getRowFormatter().addStyleName(row,
								"gradeline");
						resultsTable.setTitle("Race Grading");
						lastGrade = grade;
						i = 0;
					}
				}
				addEventRaceHistoryDetails(rh, riderMap.get(rh.getRiderId()),
						++i, resultsTable);
			}
		}

		getContent().add(raceDesc);
		raceDesc.setStylePrimaryName("racedesc");
		getContent().add(resultsTable);

		lastEventID = newEventID;
		show();

	}

	private void addEventRiderHeader(HTMLTable resultsTable) {
		resultsTable.setWidget(0, 0, new HTML("Last Name"));
		resultsTable.setWidget(0, 1, new HTML("First Name"));
		resultsTable.setWidget(0, 2, new HTML("Rd Grd"));
		resultsTable.setWidget(0, 3, new HTML("Crit Grd"));
		resultsTable.setWidget(0, 4, new HTML("Rider No"));
		resultsTable.setWidget(0, 5, new HTML("Place"));
		resultsTable.setWidget(0, 6, new HTML("Time"));
		resultsTable.setWidget(0, 7, new HTML("Points"));
		resultsTable.setWidget(0, 8, new HTML("Comments"));
		resultsTable.getRowFormatter().setStylePrimaryName(0,
				"resultstableheader");
		for (int c = 0; c < 9; c++) {
			resultsTable.getCellFormatter().setStylePrimaryName(0, c,
					"resultstablecell");
		}
	}

	public Long getLastEventID() {
		return lastEventID;
	}

	public static void setLastEventID(Long pLastEventID) {
		lastEventID = pLastEventID;
	}

}
