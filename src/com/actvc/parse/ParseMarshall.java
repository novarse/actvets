package com.actvc.parse;

import com.actvc.client.entities.TE;
import com.actvc.client.entities.TEntity;
import com.actvc.server.Util;
import com.google.code.twig.ObjectDatastore;

public class ParseMarshall implements ParseBase {
	private final ObjectDatastore datastore = Util.getDatastore();

	@Override
	public TEntity getEntity(String[] items) {
		TE event = datastore.load(TE.class, Long.parseLong(items[1]));
		event.setDirectorId(Long.parseLong(items[0]));
		return event;
	}

}
