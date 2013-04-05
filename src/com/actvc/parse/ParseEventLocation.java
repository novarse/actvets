package com.actvc.parse;

import com.actvc.client.entities.TEL;
import com.actvc.client.entities.TEntity;

public class ParseEventLocation implements ParseBase {

	private static final String NULL = "null";

	@Override
	public TEntity getEntity(String[] items) {
		Float latitude = null, longitude = null;
		if (items[3].length() > 0 && !NULL.equalsIgnoreCase(items[3])) {
			try {
				latitude = Float.parseFloat(items[3]);
			} catch (NumberFormatException e) {
				System.out.println("Invalid latitude: " + items[3]);
			}
		}
		if (items[4].length() > 0 && !NULL.equalsIgnoreCase(items[4])) {
			try {
				longitude = Float.parseFloat(items[4]);
			} catch (NumberFormatException e) {
				System.out.println("Invalid longitude: >" + items[4] + "<");
			}
		}
		return new TEL(Long.parseLong(items[0]), items[1], items[5], items[2],
				latitude, longitude);
	}

}
