package com.actvc.client;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the Gender enum.
 * 
 * @author stephen
 * 
 */
public class GenderTest {

	/**
	 * Tests the getGender method.
	 */
	@Test
	public void testGetGender() {
		assertEquals(Gender.U, Gender.getGender("t"));
		assertEquals(Gender.U, Gender.getGender("u"));
		assertEquals(Gender.F, Gender.getGender("f"));
		assertEquals(Gender.F, Gender.getGender("F"));
		assertEquals(Gender.M, Gender.getGender("m"));
		assertEquals(Gender.M, Gender.getGender("M"));
		assertEquals(Gender.F, Gender.getGender("f"));

	}
}
