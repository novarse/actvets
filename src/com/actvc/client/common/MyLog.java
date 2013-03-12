package com.actvc.client.common;

import com.actvc.client.AppService;
import com.actvc.client.AppServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class MyLog {
	private final static AppServiceAsync service = GWT.create(AppService.class);

	public static void log(String msg) {

		service.logClientMsg(msg, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.getStackTrace();
			}

			@Override
			public void onSuccess(Void result) {

			}
		});
	}

}
