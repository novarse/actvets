package com.actvc.server;

import com.google.code.twig.ObjectDatastore;
import com.google.code.twig.annotation.AnnotationObjectDatastore;

public class Util {

	private static ObjectDatastore datastore;

	public synchronized static ObjectDatastore getDatastore() {
		if (datastore == null)
			datastore = new AnnotationObjectDatastore();
		return datastore;
	}

}
