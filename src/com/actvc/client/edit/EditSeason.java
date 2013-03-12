package com.actvc.client.edit;

import java.util.HashMap;

import com.actvc.client.ContentEventDetail;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.TSe;
import com.actvc.client.event.EditSeasonDoIntialLoad;
import com.actvc.client.event.LoadSeasonsReturned;
import com.actvc.client.event.SaveSeasonFailed;
import com.actvc.client.event.SaveSeasonReturned;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class EditSeason extends LookupEditBase {
	TextBox seasonEd;
	TextBox listOrderEd;
	HashMap<Integer, TSe> seasonMap = new HashMap<Integer, TSe>();

	@Override
	void buildContent() {
		seasonEd = new TextBox();
		listOrderEd = new TextBox();

		contentTbl = new FlexTable();
		contentTbl.setWidget(0, 0, new Label("Season"));
		contentTbl.setWidget(0, 1, seasonEd);
		contentTbl.setWidget(1, 0, new Label("List Order"));
		contentTbl.setWidget(1, 1, listOrderEd);

		contentPanel.add(contentTbl);

		seasonEd.setWidth("290px");

	}

	@Override
	void resetForm() {
		resetLookup();
		listOrderEd.setText("");
		seasonEd.setText("");
		if (editRb.getValue()) {
			enableContentFields(false);
		}
	}

	@Override
	void wireEtc() {
		lookup.addItem("Select a Season");
		listOrderEd.setStylePrimaryName("numbertextbox");
		listOrderEd
				.setTitle("This determines the order that the Seasons will be displayed in drop down selection boxes");

		deleteBtn.setVisible(false);

		controller.addListener(EditSeasonDoIntialLoad.class,
				new ControllerListener<EditSeasonDoIntialLoad>() {

					@Override
					public void event(EditSeasonDoIntialLoad event) {
					}
				});

		controller.addListener(LoadSeasonsReturned.class,
				new ControllerListener<LoadSeasonsReturned>() {

					@Override
					public void event(LoadSeasonsReturned result) {
						lookup.clear();
						lookup.addItem("Select a Season");
						seasonMap.clear();
						int i = 0;
						for (TSe season : result.getSeasonList()) {
							String s = season.getSeason();
							lookup.addItem(s, season.getId().toString());
							seasonMap.put(++i, season);
						}
						setLookupLoaded(true);
					}
				});

		controller.addListener(SaveSeasonReturned.class,
				new ControllerListener<SaveSeasonReturned>() {

					@Override
					public void event(SaveSeasonReturned event) {
						ContentEventDetail.setLastEventID(null);
						resetForm();
						Window.alert("Data Saved");
						saveBtn.setEnabled(true);
						service.loadSeasons();
					}
				});

		controller.addListener(SaveSeasonFailed.class,
				new ControllerListener<SaveSeasonFailed>() {

					@Override
					public void event(SaveSeasonFailed result) {
						saveBtn.setEnabled(true);
					}
				});

	}

	@Override
	protected void resetLookup() {
		if (lookup.getItemCount() > 0)
			lookup.setSelectedIndex(0);
	}

	@Override
	void doDelete() {
		// TODO Auto-generated method stub

	}

	@Override
	void doFocusHandler() {
		if (!isLookupLoaded()) {
			service.loadSeasons();
		}
	}

	@Override
	void doLookupChange(ChangeEvent event) {
		seasonEd.setText(lookup.getItemText(lookup.getSelectedIndex()));
		listOrderEd.setText(Integer.toString(seasonMap.get(
				lookup.getSelectedIndex()).getListOrder()));
	}

	@Override
	void doSave() {

		int order;
		try {
			order = Integer.parseInt(listOrderEd.getText().trim());
		} catch (NumberFormatException e) {
			Window.alert("Enter an integer to indicate the order that this season will appear in a drop down selection box");
			listOrderEd.setFocus(true);
			return;
		}
		if (seasonEd.getText().trim().length() == 0) {
			Window.alert("Enter a Season");
			return;
		}

		TSe season = new TSe();
		if (editRb.getValue()) {
			season.setId(getLookupId());
		}
		season.setListOrder(order);
		season.setSeason(seasonEd.getText());
		saveBtn.setEnabled(false);
		service.saveSeason(season);
	}

}
