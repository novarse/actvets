package com.actvc.client.edit;

import com.actvc.client.ACTVC;
import com.actvc.client.AppServiceDelegate;
import com.actvc.client.common.LoadingScreen;
import com.actvc.client.controller.Controller;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class EditBase extends VerticalPanel {
	// WIdGETS
	LoadingScreen loadingScreen = ACTVC.getLoadingScreen();
	public VerticalPanel contentPanel;
	FlexTable contentTbl;

	// VARIABLES
	protected static Controller controller = ACTVC.getInstance()
			.getController();
	protected AppServiceDelegate service;

	// PANELS
	ScrollPanel fieldScrollable = new ScrollPanel();

	abstract void buildContent();

	abstract void wireEtc();

	abstract void resetForm();

	public EditBase() {
		create("");
	}

	public EditBase(String titleStr) {
		create(titleStr);
	}

	protected void create(String titleStr) {
		contentTbl = new FlexTable();
		contentPanel = new VerticalPanel();
		contentPanel.setStylePrimaryName("width100percent");

		fieldScrollable.add(contentPanel);

		this.setStylePrimaryName("editbase");

		if (!titleStr.equals("")) {
			this.add(new HTML(titleStr));
		}
		this.add(fieldScrollable);

		buildContent();

		wireEtc();
	}

	public void setService(AppServiceDelegate service) {
		this.service = service;
	}

}
