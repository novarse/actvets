package com.actvc.parse;

import com.actvc.client.entities.TED;
import com.actvc.client.entities.TEntity;

public class ParseEventDesc implements ParseBase {

	private static final String FALSE = "false";

	@Override
	public TEntity getEntity(String[] items) {
		boolean isActive = FALSE.equalsIgnoreCase(items[4]) ? false : true;
		return new TED(Long.parseLong(items[0]), items[1], items[2], items[3],
				isActive);
	}
}
