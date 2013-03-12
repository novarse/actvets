package com.actvc.client;

import com.actvc.client.controller.Controller;
import com.actvc.client.controller.ControllerListener;
import com.actvc.client.event.ShowOnly;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class ContentWidget extends VerticalPanel {
	protected static Controller controller = ACTVC.getInstance()
			.getController();

	public static enum UibAreas {
		DUAL, FUTURE, HISTORIC, EVENT, RACERESULTS, RIDERRESULTS, ADMIN, CHANGEGRADE, RIDERPOINTS, CALENDAR
	};

	AppServiceDelegate service;

	public static final String MINUS = "/images/minus.png";
	public static final String PLUS = "/images/plus.png";
	public static final String CLOSE = "/images/close.png";

	private final HorizontalPanel headerPanel;
	private final VerticalPanel body;

	private final PushButton headerBtn;
	private final Image headerBtnImg;
	private final HTML headerLabel;

	private boolean expanded = false;
	private UibAreas showTag = null;

	abstract void hbClickEvent(ClickEvent event);

	@UiConstructor
	public ContentWidget(String headerTitle) {
		service = new AppServiceDelegate();
		this.show(false);
		headerPanel = new HorizontalPanel();
		body = new VerticalPanel();
		headerBtnImg = new Image(CLOSE);
		headerLabel = new HTML(headerTitle);
		headerBtn = new PushButton(headerBtnImg, hbClick());

		headerPanel.add(headerBtn);
		headerPanel.add(headerLabel);

		this.add(headerPanel);
		this.add(body);

		wireIt();
	}

	private void wireIt() {
		this.setStylePrimaryName("content");
		body.setStylePrimaryName("body");
		headerBtn.setWidth("8px");
		headerBtn.setHeight("8px");
		headerPanel.setStylePrimaryName("contentheader");
		headerLabel.setStylePrimaryName("headerlabel");
		headerBtnImg.setStylePrimaryName("btnimage");

		controller.addListener(ShowOnly.class,
				new ControllerListener<ShowOnly>() {

					@Override
					public void event(ShowOnly result) {
						show(result.getArea().equals(getShowTag()),
								result.getArg());
					}
				});

	}

	private ClickHandler hbClick() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hbClickEvent(event);
			}
		};
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	protected void expand(boolean expand) {
		this.setExpanded(expand);
		setHeaderImage(this.isExpanded() ? MINUS : PLUS);
		expandIt();
	}

	public void expandIt() {

	}

	public void show() {
		controller.event(new ShowOnly(showTag));
	}

	public void show(boolean sv) {
		show(sv, null);
	}

	protected void show(boolean sv, String arg) {
		this.setVisible(sv);
		if (sv) {
			if (arg == null) {
				doExtraIfVisible();
			} else {
				doExtraIfVisible(arg);
			}
		}
	}

	/**
	 * Override this to add behaviour.
	 * 
	 * @param arg
	 */
	protected void doExtraIfVisible() {
		doExtraIfVisible(null);
	}

	protected void doExtraIfVisible(String arg) {
	}

	public VerticalPanel getContent() {
		return body;
	}

	public void setHeaderImage(String url) {
		headerBtnImg.setUrl(url);
	}

	public void setShowTag(UibAreas showTag) {
		this.showTag = showTag;
	}

	public UibAreas getShowTag() {
		return showTag;
	}

}
