package com.actvc.client.entity;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.actvc.client.entities.TRH;

/**
 * Tests for TRH.
 * 
 * @author stephen
 * 
 */
public class TRHTest {
	// eventid,rider, date, grade, place, time, over, points, rider
	/**
	 * Tests toExportForm.
	 */
	@Test
	public void testToExportForm() {
		TRH rh = new TRH();
		rh.setId(1, 8);
		Date d = new Date(110, 0, 1);
		rh.setDate(d);
		rh.setRaceGrade("3");
		rh.setPlace(4);
		rh.setTime("05:05");
		rh.setOverTheLine(6);
		rh.setPoints(7);
		System.out.print(rh.toExportForm());
		Assert.assertEquals("1	Fri Jan 01 00:00:00 EST 2010	3	4	05:05	6	7	8",
				rh.toExportForm());
	}

}
