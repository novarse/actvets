package com.actvc.client.entity;

import junit.framework.Assert;

import org.junit.Test;

import com.actvc.client.entities.TED;

/**
 * Tests TED class.
 * 
 * @author stephen
 * 
 */
public class TEDTest {

	private static final String EXPORTSTR = "1	Description	5	6	true";

	/**
	 * Tests export function works
	 */
	@Test
	public void testToExportForm() {
		TED ed = new TED(1L, "Description", "5", "6");
		Assert.assertEquals(EXPORTSTR, ed.toExportForm());
	}
}
