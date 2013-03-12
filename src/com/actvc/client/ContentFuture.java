package com.actvc.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.actvc.client.common.MyAnchor;
import com.actvc.client.common.MyConst;
import com.actvc.client.common.MyDate;
import com.actvc.client.common.MyLog;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.entities.Person;
import com.actvc.client.entities.TE;
import com.actvc.client.entities.TED;
import com.actvc.client.entities.TEDTO;
import com.actvc.client.event.ExpandContentForFuture;
import com.actvc.client.event.GetFutureEventReturned;
import com.actvc.client.event.LoadFutureEventsReturned;
import com.actvc.client.event.ShowContentForFuture;
import com.actvc.client.event.ShowFutureEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class ContentFuture extends ContentWidget {

	private enum loadStates {
		NEW, PARTLOADED, FULLYLOADED
	};

	private int row = 0;
	List<EventDetails> eventDetailsList = new ArrayList<EventDetails>();

	private loadStates loadState = loadStates.NEW;

	FlexTable eventsTbl = new FlexTable();

	private final Label ffMessage = new Label(
			"This webpage is best viewed in FireFox");

	public ContentFuture(String headerTitle) {
		super(headerTitle);

		if (getUserAgent().contains("msie")) {
			getContent().add(ffMessage);
		}
		getContent().add(eventsTbl);

		wire();
	}

	public static native String getUserAgent() /*-{
		return navigator.userAgent.toLowerCase();
	}-*/;

	@Override
	void hbClickEvent(ClickEvent event) {
		expand(!isExpanded());
	}

	@Override
	public void expandIt() {
		controller.event(new ExpandContentForFuture());
	}

	void wire() {

		setShowTag(UibAreas.DUAL);
		this.setHeaderImage(PLUS);
		show(false);

		controller.addListener(ShowContentForFuture.class,
				new ControllerListener<ShowContentForFuture>() {

					@Override
					public void event(ShowContentForFuture event) {
						if (loadState.equals(loadStates.NEW)) {
							service.loadFutureEvents(!isExpanded());
						} else {
							displayDetails();
						}

					}
				});

		controller.addListener(ExpandContentForFuture.class,
				new ControllerListener<ExpandContentForFuture>() {

					@Override
					public void event(ExpandContentForFuture event) {
						if (loadState.equals(loadStates.NEW)
								|| loadState.equals(loadStates.PARTLOADED)) {
							service.loadFutureEvents(!isExpanded());
						} else {
							displayDetails();
						}

					}
				});

		controller.addListener(LoadFutureEventsReturned.class,
				new ControllerListener<LoadFutureEventsReturned>() {

					@Override
					public void event(LoadFutureEventsReturned result) {
						if (result.getEventList() != null) {

							if (loadState.equals(loadStates.NEW)
									|| loadState.equals(loadStates.PARTLOADED)) {
								buildAndStoreDetails(result.getEventList());
							}
							// hh
							displayDetails();
						}
					}
				});

		controller.addListener(GetFutureEventReturned.class,
				new ControllerListener<GetFutureEventReturned>() {

					@Override
					public void event(GetFutureEventReturned event) {
						if (event.getDetails() == null) {
							Window.alert("Failed to load future event");
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

	protected void buildAndStoreDetails(TEDTO tedto) {
		try {
			Iterator<?> iterator = tedto.getEventMap().entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Long, TE> eventPair = (Entry<Long, TE>) iterator
						.next();

				EventDetails ed = new EventDetails();
				ed.setEventID(eventPair.getKey());
				ed.setDate(eventPair.getValue().getDate());

				ed.setEventType(tedto.getTypeMap()
						.get(eventPair.getValue().getEventTypeId())
						.getDescription());

				ed.setLocation(tedto.getLocationMap()
						.get(eventPair.getValue().getLocationId())
						.getLocation());

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
		} catch (RuntimeException e) {
			MyLog.log(e.getMessage());
			throw new RuntimeException(e);
		}
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
		try {
			eventsTbl.setWidget(row, 0,
					new HTML(MyDate.getDateStr(e.getDate())));
			eventsTbl.getRowFormatter().setStylePrimaryName(row++,
					"eventdaterow");

			MyAnchor eventDesc = new MyAnchor(
					e.getLocation()
							+ " "
							+ e.getEventType()
							+ ((e.getDirector() != null && e.getDirector()
									.length() != 0) ? " (" + e.getDirector()
									+ ") Dist(" : " Dist(")
							+ e.getDistLong()
							+ "km"
							+ (e.getDistShort().equals(e.getDistLong()) ? ")"
									: "/" + e.getDistShort() + "km)"),
					e.getEventID());

			eventDesc.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					controller.event(new ShowFutureEvent(((MyAnchor) event
							.getSource()).getTag()));
				}
			});
			eventsTbl.setWidget(row, 0, eventDesc);
			eventsTbl.getRowFormatter().setStylePrimaryName(row++,
					"eventlinkrow");
		} catch (RuntimeException re) {
			MyLog.log(re.getMessage());
			throw new RuntimeException(re);
		}

	}

	@Override
	protected void doExtraIfVisible() {
		controller.event(new ShowContentForFuture());
	}
}