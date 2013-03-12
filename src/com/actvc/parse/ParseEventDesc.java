package com.actvc.parse;

import com.actvc.client.entities.TED;
import com.actvc.client.entities.TEntity;

public class ParseEventDesc implements ParseBase {

	@Override
	public TEntity getEntity(String[] items) {
		return new TED(Long.parseLong(items[0]), items[3], items[1], items[2]);
	}
}
