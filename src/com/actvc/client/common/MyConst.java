package com.actvc.client.common;

public class MyConst extends Object {
	private final static String delim = ":::";
	private final static String HANDICAPPEREMAIL = "handicapper@actvets.cc";
	private static final String[] jdoDesc = { "Events", "Event Descriptions",
			"Event Types", "Event Locations", "Riders", "Race Results",
			"Pending Race Results", "Seasons", "Utilities" };

	public final static int EVENTIDX = 0;
	public final static int EVENTDESCIDX = 1;
	public static final int EVENTTYPEIDX = 2;
	public static final int EVENTLOCATIONIDX = 3;
	public final static int RIDERIDX = 4;
	public final static int RACEHISTIDX = 5;
	public static final int PENDINGRACEHISTORYIDX = 6;
	public static final int SEASONIDX = 7;
	public static final int SYSTEMIDX = 8;

	public static final int REGISTEREDUSERIDX = 200;
	public static final int MAINTENANCEIDX = 300;

	private static final int HANDICAPTYPE = 5;
	private static final String HANDICAPSTR = "Handicap";
	private static final int PLACENOTSET = 10000;
	private static final int POINTSNOTSET = -1;
	public static final int MAXEVENTLARGE = 10;
	public static final int MAXEVENTSMALL = 3;

	public static final int MARSHALLIDX = 6;
	public static final long SINGLETONID = 1L;
	public static final String TAB = "\t";
	public static final int MINYEAR = 1993;
	public static final int MAX_SUBGRADE = 3;

	public static String getDelim() {
		return delim;
	}

	public static int getHandicapId() {
		return HANDICAPTYPE;
	}

	public static String getHandicapStr() {
		return HANDICAPSTR;
	}

	public static int getPlacenotset() {
		return PLACENOTSET;
	}

	public static int getPointsnotset() {
		return POINTSNOTSET;
	}

	public static String[] getJdodesc() {
		return jdoDesc;
	}

	public static String getHandicapperemail() {
		return HANDICAPPEREMAIL;
	}
}
