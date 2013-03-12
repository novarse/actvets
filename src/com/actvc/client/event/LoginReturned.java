package com.actvc.client.event;

import com.actvc.client.controller.Event;
import com.actvc.client.entities.LoginInfo;

public class LoginReturned implements Event {

	private final LoginInfo loginInfo;

	public LoginReturned(LoginInfo result) {
		this.loginInfo = result;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

}
