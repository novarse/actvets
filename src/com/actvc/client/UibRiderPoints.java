package com.actvc.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.actvc.client.common.MyAnchor;
import com.actvc.client.common.MyLog;
import com.actvc.client.controller.Controller;
import com.actvc.client.entities.TRP;
import com.actvc.client.entities.TSe;
import com.actvc.client.event.GetRiderPoints;
import com.actvc.client.event.LoadSeasons;
import com.actvc.client.event.ShowRiderResults;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UibRiderPoints extends Composite {

	private static UibRiderPointsUiBinder uiBinder = GWT
			.create(UibRiderPointsUiBinder.class);

	interface UibRiderPointsUiBinder extends UiBinder<Widget, UibRiderPoints> {
	}

	public UibRiderPoints() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	protected static Controller controller = ACTVC.getInstance()
			.getController();

	@UiField
	Label titleField;

	@UiField
	ListBox seasonList;

	@UiField
	CheckBox sortBy;

	@UiField
	Button submitBtn;

	Label resultDesc;

	@UiField
	VerticalPanel body;

	private Map<String, TRP> prevPointsMap = null;

	public void init() {
		if (seasonList.getItemCount() == 0) {
			seasonList.addItem("Select a Season");
		}

		seasonList.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (seasonList.getItemCount() <= 1) {
					controller.event(new LoadSeasons());
				}
			}
		});
		seasonList.setStylePrimaryName("fontsize");

		ChangeHandler listChangeHandler = new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				prevPointsMap = null;
			}
		};
		seasonList.addChangeHandler(listChangeHandler);

		submitBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (seasonList.getSelectedIndex() == 0) {
					Window.alert("Select a Season");
					seasonList.setFocus(true);
					return;
				}

				if (prevPointsMap == null) {
					controller.event(new GetRiderPoints(Integer
							.parseInt(seasonList.getValue(seasonList
									.getSelectedIndex()))));
				} else {
					displayPoints(prevPointsMap, false);
				}

			}
		});

		sortBy.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

			}
		});

	}

	void displayPoints(Map<String, TRP> pointsMap) {
		displayPoints(pointsMap, true);
	}

	void displayPoints(Map<String, TRP> pointsMap, boolean saveNewPrevious) {
		if (saveNewPrevious) {
			prevPointsMap = new TreeMap<String, TRP>(pointsMap);
		}
		body.clear();

		resultDesc = new Label("Points Summary for "
				+ seasonList.getItemText(seasonList.getSelectedIndex()));

		FlexTable resultsTable = new FlexTable();
		resultsTable.setStylePrimaryName("raceresultstable");
		if (pointsMap.isEmpty()) {
			resultsTable.setWidget(0, 0, new Label("No event details found"));
		} else {
			addEventRiderHeader(resultsTable);

			if (sortBy.getValue()) {

				int i = 0;
				for (Map.Entry<String, TRP> e : pointsMap.entrySet()) {
					String[] key = e.getKey().split("&");
					TRP rp = e.getValue();

					addDetails(resultsTable, Long.parseLong(key[1]), key[0],
							rp.getNumber(), rp.getPoints(), i++);
				}
			} else {

				int i = 0;
				Collection<TRP> rpCol = pointsMap.values();
				List<TRP> rpList = new ArrayList<TRP>(rpCol);
				Collections.sort(rpList, Collections.reverseOrder());
				for (TRP rp : rpList) {
					addDetails(resultsTable, rp.getRiderId(), rp.getName(),
							rp.getNumber(), rp.getPoints(), i++);
				}

			}

		}

		resultDesc.setStylePrimaryName("racedesc");
		body.add(resultDesc);
		body.add(resultsTable);

	}

	private void addDetails(HTMLTable resultsTable, Long riderId, String name,
			int number, int points, int subRow) {
		int row = resultsTable.getRowCount();
		try {
			Anchor nameLbl = new MyAnchor(name, riderId);
			Label numberLbl = new Label(number == -1 ? ""
					: Integer.toString(number));
			Label pointsLbl = new Label(Integer.toString(points));

			resultsTable.setWidget(row, 0, nameLbl);
			resultsTable.setWidget(row, 1, numberLbl);
			resultsTable.setWidget(row, 2, pointsLbl);

			if (subRow % 2 == 0)
				resultsTable.getRowFormatter().setStylePrimaryName(row,
						"resultstablealtrow");
			else
				resultsTable.getRowFormatter().setStylePrimaryName(row,
						"resultstablerow");

			for (int c = 0; c < 3; c++) {
				resultsTable.getCellFormatter().setStylePrimaryName(row, c,
						"resultstablecell");
			}

			numberLbl.setStylePrimaryName("numbertextbox");
			pointsLbl.setStylePrimaryName("numbertextbox");
			nameLbl.setTitle("Click to show rider details");
			nameLbl.addClickHandler(nameClickHandler());
		} catch (RuntimeException e) {
			MyLog.log(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	private ClickHandler nameClickHandler() {
		return (new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MyAnchor ml = (MyAnchor) event.getSource();
				controller.event(new ShowRiderResults(ml.getTag()));
			}
		});
	}

	private void addEventRiderHeader(HTMLTable resultsTable) {
		resultsTable.setWidget(0, 0, new HTML("Name"));
		resultsTable.setWidget(0, 1, new HTML("Rider No"));
		resultsTable.setWidget(0, 2, new HTML("Points"));
		resultsTable.getRowFormatter().setStylePrimaryName(0,
				"resultstableheader");
		for (int c = 0; c < 3; c++) {
			resultsTable.getCellFormatter().setStylePrimaryName(0, c,
					"resultstablecell");
		}
	}

	public void buildSeason(List<TSe> seasons) {
		seasonList.clear();
		seasonList.addItem("Select a Season");
		for (TSe s : seasons) {
			seasonList.addItem(s.getSeason(), s.getId().toString());
		}
	}

}
