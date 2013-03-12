package com.actvc.client.edit;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class LookupEditBase extends EditBase {
	// WIDGETS
	FlexTable editNewTbl;
	RadioButton editRb;
	RadioButton newRb;
	protected FlexTable lookupTable;
	Label lookupLbl;
	ListBox lookup;
	protected FlexTable saveDeleteTable;
	Button saveBtn;
	Button deleteBtn;
	VerticalPanel footerArea;

	// VARIABLES
	static int widgetNo = 0;
	private boolean lookupLoaded = false;
	private String groupId;

	public String getGroupId() {
		return groupId;
	}

	public LookupEditBase() {
		super("");
	}

	public LookupEditBase(String title) {
		super(title);
	}

	public boolean isLookupLoaded() {
		return lookupLoaded;
	}

	public void setLookupLoaded(boolean lookupLoaded) {
		this.lookupLoaded = lookupLoaded;
	}

	// abstract methods
	abstract void doFocusHandler();

	abstract void doLookupChange(ChangeEvent event);

	abstract void doDelete();

	abstract void doSave();

	@Override
	protected void create(String titleStr) {
		contentTbl = new FlexTable();
		contentPanel = new VerticalPanel();
		fieldScrollable.add(contentPanel);

		lookupLbl = new Label("Lookup");
		lookup = new ListBox();
		saveBtn = new Button("Save");
		deleteBtn = new Button("Delete");
		lookupTable = new FlexTable();
		editNewTbl = new FlexTable();
		saveDeleteTable = new FlexTable();
		footerArea = new VerticalPanel();

		this.add(editNewTbl);
		this.add(lookupTable);
		this.add(fieldScrollable);
		this.add(footerArea);
		this.add(saveDeleteTable);

		buildEditNewTbl();
		buildLookupTbl();
		buildContent();
		buildSaveDeleteTbl();

		if (!titleStr.equals("")) {
			this.add(new HTML(titleStr));
		}

		wireEtcBase();

		wireEtc();
	}

	private void buildLookupTbl() {
		lookupTable.setWidget(0, 0, lookupLbl);
		lookupTable.setWidget(0, 1, lookup);
	}

	public void setLookupLbl(String lookupLbl) {
		this.lookupLbl.setText(lookupLbl);
	}

	private void doEditLookup() {
		lookup.setEnabled(true);
		saveBtn.setEnabled(true);
		deleteBtn.setEnabled(true);
		enableContentFields(false);
		clearContentFields();

		doEditClick();
	}

	private void buildEditNewTbl() {
		groupId = String.valueOf(++widgetNo);
		editRb = new RadioButton(groupId, "Edit", true);
		newRb = new RadioButton(groupId, "New", true);
		editNewTbl.setWidget(0, 0, new HTML("Select&nbsp;"));
		editNewTbl.setWidget(0, 1, editRb);
		editNewTbl.setWidget(0, 2, new HTML("&nbsp;"));
		editNewTbl.setWidget(0, 3, newRb);

		editRb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					doEditLookup();
				}
			}

		});

		newRb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					lookup.setEnabled(false);
					saveBtn.setEnabled(true);
					deleteBtn.setEnabled(false);
					enableContentFields(true);
					clearContentFields();
					resetLookup();
					resetForm();

					doNewClick();
				}

			}
		});

		lookup.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				doFocusHandler();
			}
		});

		lookup.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					doEditLookup();
				}
			}
		});

	}

	protected void doNewClick() {
	}

	protected void doEditClick() {
	}

	protected void enableContentFields(boolean setIt) {
		for (int i = 0; i < contentTbl.getRowCount(); i++) {
			Widget w = contentTbl.getWidget(i, 1);
			if (w instanceof FocusWidget) {
				((FocusWidget) w).setEnabled(setIt);
			}
		}

		enableOtherFields(setIt);
	}

	protected void enableOtherFields(boolean setIt) {

	}

	protected void resetLookup() {
		if (lookup.getItemCount() > 0)
			lookup.setSelectedIndex(0);
	}

	protected void wireEtcBase() {
		this.setStylePrimaryName("editbase");
		editRb.setValue(true, true);

		saveBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (Window
						.confirm("Are you sure you want to save these details?"))
					doSave();
			}
		});

		deleteBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm("Are you sure you want to delete this?;"))
					doDelete();
			}
		});

		lookup.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (lookup.getSelectedIndex() == 0) {
					enableContentFields(false);
					clearContentFields();
				} else {
					enableContentFields(true);
					doLookupChange(event);
				}

			}
		});

	}

	private void buildSaveDeleteTbl() {
		saveDeleteTable.setWidget(0, 0, saveBtn);
		saveDeleteTable.setWidget(0, 1, deleteBtn);
	}

	public Long getLookupId() {
		try {
			return Long.parseLong(lookup.getValue(lookup.getSelectedIndex()));
		} catch (Exception e) {
			return Long.parseLong("0");
		}

	}

	protected void clearContentFields() {
		for (int i = 0; i < contentTbl.getRowCount(); i++) {
			Widget w = contentTbl.getWidget(i, 1);
			if (w instanceof TextBox) {
				((TextBox) w).setText("");
			}
		}

		clearOtherFields();
	}

	protected void clearOtherFields() {

	}

}
