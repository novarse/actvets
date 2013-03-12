package com.actvc.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.actvc.client.common.MyAnchor;
import com.actvc.client.common.MyConst;
import com.actvc.client.common.MyDate;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.Person;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TED;
import com.actvc.client.entities.TEDTO;
import com.actvc.client.event.ExpandContentForHistoric;
import com.actvc.client.event.LoadHistoricEventsReturned;
import com.actvc.client.event.ShowHistoricContent;
import com.actvc.client.event.ShowRaceResults;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class ContentHistoric extends ContentWidget {

	private enum loadStates {
		NEW, PARTLOADED, FULLYLOADED
	};

	private loadStates loadState = loadStates.NEW;
	private int row = 0;
	List<EventDetails> eventDetailsList = new ArrayList<EventDetails>();

	private final FlexTable eventsTbl = new FlexTable();

	public ContentHistoric(String headerTitle) {
		super(headerTitle);

		getContent().add(eventsTbl);

		wire();
	}

	@Override
	public void expandIt() {
		controller.event(new ExpandContentForHistoric());
	}

	void wire() {
		setShowTag(UibAreas.DUAL);
		this.setHeaderImage(PLUS);
		show(false);

		controller.addListener(ShowHistoricContent.class,
				new ControllerListener<ShowHistoricContent>() {

					@Override
					public void event(ShowHistoricContent event) {
						if (loadState.equals(loadStates.NEW)) {
							service.loadHistoricEvents(!isExpanded());
						} else {
							displayDetails();
						}

					}
				});

		controller.addListener(ExpandContentForHistoric.class,
				new ControllerListener<ExpandContentForHistoric>() {

					@Override
					public void event(ExpandContentForHistoric event) {

						if (loadState.equals(loadStates.NEW)
								|| loadState.equals(loadStates.PARTLOADED)) {
							service.loadHistoricEvents(!isExpanded());
						} else {
							displayDetails();
						}

					}
				});

		controller.addListener(LoadHistoricEventsReturned.class,
				new ControllerListener<LoadHistoricEventsReturned>() {

					@Override
					public void event(LoadHistoricEventsReturned result) {
						if (result.getEventList() != null) {

							if (loadState.equals(loadStates.NEW)
									|| loadState.equals(loadStates.PARTLOADED)) {
								buildAndStoreDetails(result.getEventList());
							}

							displayDetails();
						}
					}

				});

		init();
	}

	private void init() {
		if (ACTVC.getEventId() == 0) {
			show(true);
		}
	}

	@Override
	void hbClickEvent(ClickEvent event) {
		expand(!isExpanded());
	}

	protected void buildAndStoreDetails(TEDTO tedto) {
		Iterator<?> iterator = tedto.getEventMap().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Long, TE> eventPair = (Entry<Long, TE>) iterator.next();

			EventDetails ed = new EventDetails();
			ed.setEventID(eventPair.getKey());
			ed.setDate(eventPair.getValue().getDate());

			ed.setEventType(tedto.getTypeMap()
					.get(eventPair.getValue().getEventTypeId())
					.getDescription());

			ed.setLocation(tedto.getLocationMap()
					.get(eventPair.getValue().getLocationId()).getLocation());

			TED eventDesc = tedto.getDescriptionMap().get(
					eventPair.getValue().getEventDescriptionId());
			ed.setDistLong(eventDesc.getDistLong());
			ed.setDistShort(eventDesc.getDistShort());

			Person director = tedto.getDirectorMap().get(
					eventPair.getValue().getDirectorId());
			if (director != null) {
				ed.setDirector(director.getFirstName() + " "
						+ director.getLastName());
			}

			eventDetailsList.add(ed);
		}

		loadState = loadState.equals(loadStates.NEW) ? loadStates.PARTLOADED
				: loadStates.FULLYLOADED;

	}

	private void displayDetails() {
		row = 0;
		eventsTbl.removeAllRows();
		Iterator<EventDetails> iterator = eventDetailsList.iterator();
		for (int i = 1; i <= (!isExpanded() ? MyConst.MAXEVENTSMALL
				: MyConst.MAXEVENTLARGE); i++) {
			if (iterator.hasNext()) {
				addToEventTbl(iterator.next());

			}
		}
	}

	private void addToEventTbl(EventDetails e) {

		eventsTbl.setWidget(row, 0, new HTML(MyDate.getDateStr(e.getDate())));
		eventsTbl.getRowFormatter().setStylePrimaryName(row++, "eventdaterow");

		MyAnchor eventDesc = new MyAnchor(
				e.getLocation()
						+ " "
						+ e.getEventType()
						+ ((e.getDirector() != null && e.getDirector().length() != 0) ? " ("
								+ e.getDirector() + ") Dist("
								: " Dist(")
						+ e.getDistLong()
						+ "km"
						+ (e.getDistShort().equals(e.getDistLong()) ? ")" : "/"
								+ e.getDistShort() + "km)"), e.getEventID());

		eventDesc.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				controller.event(new ShowRaceResults(((MyAnchor) event
						.getSource()).getTag()));
			}
		});
		eventsTbl.setWidget(row, 0, eventDesc);
		eventsTbl.getRowFormatter().setStylePrimaryName(row++, "eventlinkrow");

	}

	@Override
	protected void doExtraIfVisible() {
		controller.event(new ShowHistoricContent());
	}
}
