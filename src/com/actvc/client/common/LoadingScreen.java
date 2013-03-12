package com.actvc.client.common;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Alejandro D. Garin
 */
public class LoadingScreen extends DialogBox {
	private static int counter = 0;
	private final Image image;
	VerticalPanel content = new VerticalPanel();

	public LoadingScreen() {
		this.setAnimationEnabled(true);
		this.setAutoHideEnabled(false);

		image = new Image();
		image.setUrl("images/loading.gif");
		image.setSize("100px", "100px");
		this.add(image);
		setModal(true);
		setText("Loading...");
	}

	public void showIt() {
		if (counter++ == 0) {
			center("Loading...");
		}
	}

	public void showIt(String msg) {
		if (counter++ == 0) {
			center(msg);
		}
	}

	public void hideIt() {
		if (--counter <= 0) {
			counter = 0;
			hide();
		}
	}

	@Override
	public void hide() {
		super.hide();
	}

	public void center(String text) {
		setText(text);

		int left = Math.round((Window.getClientWidth() - 260) / 2);
		int top = Math.round((Window.getClientHeight() - 300) / 2);
		setPopupPosition(left, top);
		show();
	}

}
