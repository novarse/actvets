package com.actvc.parse;

import com.actvc.client.common.MyConst;
import com.actvc.client.entities.TS;

public class ParseSystem implements ParseBase {

	@Override
	public TS getEntity(String[] items) {
		TS system = new TS(MyConst.SINGLETONID);
		system.setReturnBitlyUrl(items[0]);
		return system;
	}

}
