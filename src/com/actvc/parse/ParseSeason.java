package com.actvc.parse;

import com.actvc.client.entities.TSe;

public class ParseSeason implements ParseBase {

	@Override
	public TSe getEntity(String[] items) {
		for (String item : items) {
			if (item.trim().isEmpty()) {
				throw new RuntimeException("Blank field");
			}
		}
		return new TSe(Long.parseLong(items[0]), items[1],
				Integer.parseInt(items[2]));
	}

}
