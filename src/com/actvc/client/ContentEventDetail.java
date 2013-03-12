package com.actvc.client;

import com.actvc.client.controller.ControllerListener;
import com.actvc.client.event.GetFutureEventReturned;
import com.actvc.client.event.ShowFutureEvent;
import com.actvc.client.event.ShowOnly;
import com.google.gwt.event.dom.client.ClickEvent;

public class ContentEventDetail extends ContentWidget {

	private final UibEventContent uiEventContent;
	private static Long lastEventID = null;

	public ContentEventDetail(String headerTitle) {
		super(headerTitle);
		uiEventContent = new UibEventContent();
		getContent().add(uiEventContent);

		wire();
	}

	private void wire() {
		setShowTag(UibAreas.EVENT);

		controller.addListener(ShowFutureEvent.class,
				new ControllerListener<ShowFutureEvent>() {

					@Override
					public void event(ShowFutureEvent event) {
						if (!event.getEventId().equals(lastEventID)) {
							lastEventID = event.getEventId();
							service.getFutureEvent(event.getEventId());
						} else {
							show();
						}
					}
				});

		controller.addListener(GetFutureEventReturned.class,
				new ControllerListener<GetFutureEventReturned>() {

					@Override
					public void event(GetFutureEventReturned result) {
						if (result.getDetails() != null) {
							Long id = (Long) result.getDetails().getEventMap()
									.keySet().toArray()[0];
							if (!uiEventContent.isLoaded(id)) {
								uiEventContent.loadDetails(result.getDetails());
							}
							show();
						}
					}
				});

		init();

	}

	private void init() {
		if (ACTVC.getEventId() != 0L) {
			controller.event(new ShowFutureEvent(ACTVC.getEventId()));
		}
	}

	@Override
	void hbClickEvent(ClickEvent event) {
		controller.event(new ShowOnly(UibAreas.DUAL));
	}

	public static Long getLastEventID() {
		return lastEventID;
	}

	public static void setLastEventID(Long plastEventID) {
		lastEventID = plastEventID;
	}

}
