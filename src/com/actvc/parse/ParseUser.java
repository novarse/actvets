package com.actvc.parse;

import com.actvc.client.entities.TU;

public class ParseUser implements ParseBase {

	@Override
	public TU getEntity(String[] items) {
		final TU ru = new TU();

		ru.setEmail(items[0]);
		ru.setName(items[1].replaceAll("\"", ""));
		ru.setRoleSuperUser(items[2].trim().equals("1"));
		return ru;
	}

}
