package com.actvc.client;

import com.actvc.client.controller.ControllerListener;
import com.actvc.client.event.GetRiderPoints;
import com.actvc.client.event.GetRiderPointsReturned;
import com.actvc.client.event.LoadSeasons;
import com.actvc.client.event.LoadSeasonsReturned;
import com.actvc.client.event.LoadingScreenHideIt;
import com.actvc.client.event.LoadingScreenShowIt;
import com.actvc.client.event.ShowOnly;
import com.actvc.client.event.ShowRiderPoints;
import com.google.gwt.event.dom.client.ClickEvent;

public class ContentRiderPoints extends ContentWidget {

	private boolean isLoaded = false;

	private final UibRiderPoints uibRiderPoints;

	public ContentRiderPoints(String headerTitle) {
		super(headerTitle);
		uibRiderPoints = new UibRiderPoints();
		getContent().add(uibRiderPoints);

		wire();
	}

	private void wire() {
		setShowTag(UibAreas.RIDERPOINTS);

		controller.addListener(LoadSeasons.class,
				new ControllerListener<LoadSeasons>() {

					@Override
					public void event(LoadSeasons result) {
						service.loadSeasons();
					}
				});

		controller.addListener(ShowRiderPoints.class,
				new ControllerListener<ShowRiderPoints>() {

					@Override
					public void event(ShowRiderPoints result) {
						if (!isLoaded) {
							uibRiderPoints.init();
							isLoaded = true;
						}
						show();
					}

				});

		controller.addListener(GetRiderPointsReturned.class,
				new ControllerListener<GetRiderPointsReturned>() {

					@Override
					public void event(GetRiderPointsReturned result) {
						if (result != null) {
							controller.event(new LoadingScreenShowIt(
									"Building report..."));
							uibRiderPoints.displayPoints(result.getPointsMap());
							controller.event(new LoadingScreenHideIt());
						}
					}
				});

		controller.addListener(LoadSeasonsReturned.class,
				new ControllerListener<LoadSeasonsReturned>() {

					@Override
					public void event(LoadSeasonsReturned result) {
						uibRiderPoints.buildSeason(result.getSeasonList());
					}
				});

		controller.addListener(GetRiderPoints.class,
				new ControllerListener<GetRiderPoints>() {

					@Override
					public void event(GetRiderPoints result) {
						if (result != null) {
							service.getRiderPoints(result.getSeasonId());
						}
					}
				});
	}

	@Override
	void hbClickEvent(ClickEvent event) {
		controller.event(new ShowOnly(UibAreas.DUAL));
	}

}
