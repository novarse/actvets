package com.actvc.parser;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import com.actvc.client.entities.TU;
import com.actvc.parse.ParseUser;

/**
 * Tests parsing of Users file.
 * 
 * @author stephen
 * 
 */
public class ParseUsersTest {

	private static final String TYPICAL_LINE = "test@example.com	test	1";

	/**
	 * Tests a typical user line.
	 */
	@Test
	public void testTypicalLine() {
		TU user = new ParseUser().getEntity(TYPICAL_LINE.split("\t"));

		assertEquals("test@example.com", user.getEmail());
		assertEquals("test", user.getName());
		assertEquals(true, user.isRoleSuperUser());

	}

}
