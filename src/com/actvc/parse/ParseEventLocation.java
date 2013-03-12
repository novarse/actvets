package com.actvc.parse;

import com.actvc.client.entities.TEL;
import com.actvc.client.entities.TEntity;

public class ParseEventLocation implements ParseBase {

	@Override
	public TEntity getEntity(String[] items) {
		Float latitude = null, longitude = null;
		if (items[3].length() > 0) {
			String[] mapParts = items[3].split("(&ll=)|(&spn)");
			String[] xy = mapParts[1].split(",");
			latitude = Float.parseFloat(xy[0]);
			longitude = Float.parseFloat(xy[1]);
		}
		return new TEL(Long.parseLong(items[0]), items[1], items[4], items[2],
				latitude, longitude);
	}

}
