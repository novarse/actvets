package com.actvc.parse;

import com.actvc.client.entities.TET;
import com.actvc.client.entities.TEntity;

public class ParseEventType implements ParseBase {

	@Override
	public TEntity getEntity(String[] items) {
		return new TET(Long.parseLong(items[0]), items[1]);
	}

}
